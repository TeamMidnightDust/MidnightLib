package eu.midnightdust.lib.config;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import eu.midnightdust.lib.config.MidnightConfig.Entry;
import eu.midnightdust.lib.util.PlatformFunctions;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

//? fabric
import net.fabricmc.api.DedicatedServerModInitializer;

//? if >= 1.21.11
import net.minecraft.server.permissions.*;

public class AutoCommand /*? fabric {*/ implements DedicatedServerModInitializer /*?}*/ {
    final static String VALUE = "value";
    Field field;
    Class<?> type;
    String modid;
    boolean isList;

    public AutoCommand() {}

    public AutoCommand(Field field, String modid) {
        this.field = field; this.modid = modid;
        this.type = MidnightConfig.getUnderlyingType(field);
        this.isList = field.getType() == List.class;

        var command = Commands.literal(field.getName()).executes(this::getValue);

        if (type.isEnum()) {
            for (Object enumValue : field.getType().getEnumConstants())
                command = command.then(Commands.literal(enumValue.toString())
                        .executes(ctx -> this.setValue(ctx.getSource(), enumValue, "")));
        } else if (isList) {
            for (String action : new String[]{"add", "remove"})
                command = command.then(Commands.literal(action)
                        .then(Commands.argument(VALUE, getArgType()).executes(ctx -> setValueFromArg(ctx, action))));
        } else command = command.then(Commands.argument(VALUE, getArgType()).executes(ctx -> setValueFromArg(ctx, "")));

        PlatformFunctions.registerCommand(Commands.literal("midnightconfig").requires(source ->
                source/*? if >= 1.21.11 {*/.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.GAMEMASTERS))/*?} else {*/ /*.hasPermission(2) *//*?}*/).then(Commands.literal(modid).then(command)));
    }

    public ArgumentType<?> getArgType() {
        Entry entry = field.getAnnotation(Entry.class);
        if (type == int.class) return IntegerArgumentType.integer((int) entry.min(), (int) entry.max());
        else if (type == double.class) return DoubleArgumentType.doubleArg(entry.min(), entry.max());
        else if (type == float.class) return FloatArgumentType.floatArg((float) entry.min(), (float) entry.max());
        else if (type == boolean.class) return BoolArgumentType.bool();
        return StringArgumentType.string();
    }

    public int setValueFromArg(CommandContext<CommandSourceStack> context, String action) {
        if (type == int.class) return setValue(context.getSource(), IntegerArgumentType.getInteger(context, VALUE), action);
        else if (type == double.class) return setValue(context.getSource(), DoubleArgumentType.getDouble(context, VALUE), action);
        else if (type == float.class) return setValue(context.getSource(), FloatArgumentType.getFloat(context, VALUE), action);
        else if (type == boolean.class) return setValue(context.getSource(), BoolArgumentType.getBool(context, VALUE), action);
        return setValue(context.getSource(), StringArgumentType.getString(context, VALUE), action);
    }
    private int setValue(CommandSourceStack source, Object value, String action) {
        boolean add = Objects.equals(action, "add");
        try {
            if (!isList) field.set(null, value);
            else {
                @SuppressWarnings("unchecked") var list = (List<Object>) field.get(null);
                if (add) list.add(value);
                else if (!list.contains(value)) throw new IllegalArgumentException("List does not contain this string!");
                else list.remove(value);
            }
            MidnightConfig.write(modid);
        }
        catch (Exception e) {
            source.sendFailure(Component.literal(isList ? "Could not %s %s %s %s: %s".formatted(add ? "add" : "remove", value, add ? "to" : "from", field.getName(), e) : "Could not set %s to value %s: %s".formatted(field.getName(), value, e)));
            return 0;
        }
        source.sendSuccess(() -> Component.literal(isList ? "Successfully %s %s %s %s".formatted(add ? "added" : "removed", value, add ? "to" : "from", field.getName()) :
                "Successfully set %s to %s".formatted(field.getName(), value)), true);
        return 1;
    }
    private int getValue(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> {
            try { return Component.literal("The value of %s is %s".formatted(field.getName(), field.get(null)));
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
            }, true);
        return 0;
    }

    public void onInitializeServer() {
        MidnightConfig.configInstances.forEach((modid, config) -> {
            for (Field field : config.configClass.getFields()) {
                if (field.isAnnotationPresent(MidnightConfig.Entry.class)
                        && !field.isAnnotationPresent(MidnightConfig.Client.class)
                        && !field.isAnnotationPresent(MidnightConfig.Hidden.class))
                    new AutoCommand(field, modid);
            }
        });
    }
}