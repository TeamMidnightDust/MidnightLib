package eu.midnightdust.lib.config;

import eu.midnightdust.lib.event.MidnightEvent;
import eu.midnightdust.lib.event.MidnightEventType;
import eu.midnightdust.lib.util.PlatformFunctions;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class EntryInfo {
    public MidnightConfig.Entry entry;
    public MidnightConfig.Comment comment;
    public MidnightConfig.Condition[] conditions;
    public final Field field;
    public final Class<?> dataType;
    public final String modid, fieldName, translationKey;
    int listIndex;
    Object defaultValue, value, function;
    String tempValue;   // The value visible in the config screen
    boolean inLimits = true;
    Component error;
    AbstractWidget actionButton; // color picker button / explorer button
    Tab tab;
    boolean conditionsMet = true;

    public EntryInfo(Field field, String modid) {
        this.field = field;
        this.modid = modid;
        if (field != null) {
            this.fieldName = field.getName();
            this.dataType = MidnightConfig.getUnderlyingType(field);
            this.entry = field.getAnnotation(MidnightConfig.Entry.class);
            this.comment = field.getAnnotation(MidnightConfig.Comment.class);
            this.conditions = field.getAnnotationsByType(MidnightConfig.Condition.class);
        } else {
            this.fieldName = "";
            this.dataType = null;
        }

        if (entry != null && !entry.name().isEmpty())
            this.translationKey = entry.name();
        else if (comment != null && !comment.name().isEmpty())
            this.translationKey = comment.name();
        else this.translationKey = modid + ".midnightconfig." + fieldName;
    }

    public void setValue(Object value) {
        Object oldValue;
        if (this.field.getType() != List.class) {
            oldValue = this.value;
            this.value = value;
            this.tempValue = value.toString();
        } else {
            oldValue = this.listIndex >= ((List<?>) this.value).size() ? null : ((List<?>) this.value).get(this.listIndex);
            writeList(this.listIndex, value);
            this.tempValue = toTemporaryValue();
        }
        MidnightEvent.trigger(MidnightEventType.SET_VALUE, this.modid, this.fieldName, oldValue, value);
    }

    public String toTemporaryValue() {
        if (this.field.getType() != List.class) return this.value.toString();
        else try {
            return ((List<?>) this.value).get(this.listIndex).toString();
        } catch (Exception ignored) {
            return "";
        }
    }

    public void updateFieldValue() {
        try {
            if (this.field.get(null) != value) MidnightConfig.entries.values().forEach(EntryInfo::updateConditions);
            if (this.entry != null) this.field.set(null, this.value);
        } catch (IllegalAccessException ignored) {
        }
    }

    public void updateConditions() {
        boolean prevConditionState = this.conditionsMet;
        if (this.conditions.length > 0) this.conditionsMet = true;    // reset conditions
        for (MidnightConfig.Condition condition : this.conditions) {
            //noinspection ConstantValue
            if (!condition.requiredModId().isEmpty() && !PlatformFunctions.isModLoaded(condition.requiredModId()))
                this.conditionsMet = false;
            String requiredOption = condition.requiredOption().contains(":") ? condition.requiredOption() : (this.modid + ":" + condition.requiredOption());
            Optional.ofNullable(MidnightConfig.entries.get(requiredOption)).ifPresent(info -> this.conditionsMet &= List.of(condition.requiredValue()).contains(info.tempValue));

            if (!this.conditionsMet) break;
        }
        if (prevConditionState != this.conditionsMet) MidnightConfig.configInstances.get(modid).reloadScreen = true;
    }

    public <T> void writeList(int index, T value) {
        //noinspection unchecked
        var list = (List<T>) this.value;
        if (index >= list.size())
            list.add(value);
        else list.set(index, value);
    }

    public Tooltip getTooltip(boolean isButton) {
        String key = translationKey + (!isButton ? ".label" : "") + ".tooltip";
        return Tooltip.create(isButton && this.error != null ? this.error : I18n.exists(key) ? Component.translatable(key) : Component.empty());
    }
}
