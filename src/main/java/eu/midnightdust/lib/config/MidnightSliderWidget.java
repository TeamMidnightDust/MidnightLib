package eu.midnightdust.lib.config;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Function;

public class MidnightSliderWidget extends AbstractSliderButton {
    private final Consumer<Double> applyValue;
    private final Function<Double, Component> updateMessage;

    public MidnightSliderWidget(int x, int y, int width, int height, Component text, double value, EntryInfo info) {
        this(x, y, width, height, value, getApplyValue(info),v -> Component.nullToEmpty(info.tempValue));
    }

    private static Consumer<Double> getApplyValue(EntryInfo info) {
        final MidnightConfig.Entry e = info.entry;
        if (info.dataType == int.class) return d -> info.setValue(((Number) (e.min() + d * (e.max() - e.min()))).intValue());
        else if (info.dataType == double.class)
            return d -> info.setValue(Math.round((e.min() + d * (e.max() - e.min())) * (double) e.precision()) / (double) e.precision());
        else if (info.dataType == float.class)
            return d -> info.setValue(Math.round((e.min() + d * (e.max() - e.min())) * (float) e.precision()) / (float) e.precision());
        return d -> {};
    }

    public MidnightSliderWidget(int x, int y, int width, int height, double value, Consumer<Double> applyValue, Function<Double, Component> updateMessage) {
        super(x, y, width, height, updateMessage.apply(value), value);
        this.applyValue = applyValue;
        this.updateMessage = updateMessage;
    }

    @Override
    public void updateMessage() {
        this.setMessage(this.updateMessage.apply(value));
    }

    @Override
    public void applyValue() {
        this.applyValue.accept(value);
    }
}
