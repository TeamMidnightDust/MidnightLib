package eu.midnightdust.lib.config;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoCommand {
    public static List<LiteralArgumentBuilder<ServerCommandSource>> commands = new ArrayList<>();
    final static String VALUE = "value";
    final Field field;
    final Class<?> type;
    final String modid;
    final boolean isList;

    public AutoCommand(Field field, String modid) {
        this.field = field;
        this.modid = modid;
        this.type = field.getType();
        this.isList = field.getType() == List.class;

        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("midnightconfig").requires(source -> source.hasPermissionLevel(2)).then(
                CommandManager.literal(modid).then(CommandManager.literal(field.getName()).executes(this::getValue)));

        if (type.isEnum()) {
            for (Object enumValue : field.getType().getEnumConstants()) {
                command = command.then(CommandManager.literal(enumValue.toString()).executes(ctx -> this.setValue(ctx.getSource(), enumValue, "")));
            }
        }
        else if (isList) {
            for (String action : List.of("add", "remove")) {
                command = command.then(CommandManager.literal(action).then(
                        CommandManager.argument(VALUE, getArgType()).executes(ctx -> setValueFromArg(ctx, action))));
            }
        }
        else command = command.then(CommandManager.argument(VALUE, getArgType()).executes(ctx -> setValueFromArg(ctx, "")));

        PlatformFunctions.registerCommand(command); commands.add(command);
    }

    public ArgumentType<?> getArgType() {
        MidnightConfig.Entry entry = type.getAnnotation(MidnightConfig.Entry.class);
        if (type.isInstance(Number.class)) {
            if (type == int.class) return IntegerArgumentType.integer((int) entry.min(), (int) entry.max());
            else if (type == double.class) return DoubleArgumentType.doubleArg(entry.min(), entry.max());
            else if (type == float.class) return FloatArgumentType.floatArg((float) entry.min(), (float) entry.max());
        }
        else if (type == boolean.class) return BoolArgumentType.bool();
        return StringArgumentType.string();
    }
    public int setValueFromArg(CommandContext<ServerCommandSource> context, String action) {
        if (type.isInstance(Number.class)) {
            if (type == int.class) return setValue(context.getSource(), IntegerArgumentType.getInteger(context, VALUE), action);
            else if (type == double.class) return setValue(context.getSource(), DoubleArgumentType.getDouble(context, VALUE), action);
            else if (type == float.class) return setValue(context.getSource(), FloatArgumentType.getFloat(context, VALUE), action);
        }
        else if (type == boolean.class) return setValue(context.getSource(), BoolArgumentType.getBool(context, VALUE), action);
        return setValue(context.getSource(), StringArgumentType.getString(context, VALUE), action);
    }
    private int setValue(ServerCommandSource source, Object value, String action) {
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
            if (!isList) source.sendError(Text.literal("Could not set "+field.getName()+" to value "+value+": " + e));
            else source.sendError(Text.literal((add ? "Could not add "+value+" to " : "Could not remove "+value+" from ")+field.getName() +": " + e));
            return 0;
        }
        if (!isList) source.sendFeedback(() -> Text.literal("Successfully set " + field.getName()+" to "+value), true);
        else source.sendFeedback(() -> Text.literal((add ? "Successfully added " +value+" to " : "Successfully removed " +value+" from ") +field.getName()), true);
        return 1;
    }
    private int getValue(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> {
            try { return Text.literal("The value of "+field.getName()+" is "+field.get(null));
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
            }, true);
        return 0;
    }
}