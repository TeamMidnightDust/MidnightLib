package eu.midnightdust.lib.config;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class AutoCommand {
    public static List<LiteralArgumentBuilder<ServerCommandSource>> commands = new ArrayList<>();
    final Field entry;
    final String modid;

    public AutoCommand(Field entry, String modid) {
        this.entry = entry;
        this.modid = modid;
    }

    public void register() {
        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal(modid);
        if (entry.getType() == int.class)
            command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                    CommandManager.argument("value", IntegerArgumentType.integer((int) entry.getAnnotation(MidnightConfig.Entry.class).min(),(int) entry.getAnnotation(MidnightConfig.Entry.class).max()))
                            .executes(ctx -> this.setValue(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value")))
            ));
        else if (entry.getType() == double.class)
            command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                    CommandManager.argument("value", DoubleArgumentType.doubleArg(entry.getAnnotation(MidnightConfig.Entry.class).min(),entry.getAnnotation(MidnightConfig.Entry.class).max()))
                            .executes(ctx -> this.setValue(ctx.getSource(), DoubleArgumentType.getDouble(ctx, "value")))
            ));
        else if (entry.getType() == float.class)
            command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                    CommandManager.argument("value", FloatArgumentType.floatArg((float) entry.getAnnotation(MidnightConfig.Entry.class).min(), (float) entry.getAnnotation(MidnightConfig.Entry.class).max()))
                            .executes(ctx -> this.setValue(ctx.getSource(), FloatArgumentType.getFloat(ctx, "value")))
            ));
        else if (entry.getType() == boolean.class) {
            for (int i = 0; i < 2; i++) {
                command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                        CommandManager.literal(i==0 ? "true":"false")
                                .executes(ctx -> this.setValue(ctx.getSource(), ctx.getInput().endsWith("true")))
                ));
            }
        }
        else if (entry.getType().isEnum()) {
            for (int i = 0; i < entry.getType().getEnumConstants().length; ++i) {
                Object enumValue = Arrays.stream(entry.getType().getEnumConstants()).toList().get(i);
                command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                        CommandManager.literal(enumValue.toString())
                                .executes(ctx -> this.setValue(ctx.getSource(), enumValue))
                ));
            }
        }
        else if (entry.getType() == List.class) {
            for (int i = 0; i < 2; i++) {
                int finalI = i;
                command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(CommandManager.literal(i==0 ? "add":"remove").then(
                        CommandManager.argument("value", StringArgumentType.string())
                                .executes(ctx -> this.setList(ctx.getSource(), StringArgumentType.getString(ctx, "value"), finalI==0))
                )));
            }
        }
        else {
            command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                    CommandManager.argument("value", StringArgumentType.string())
                            .executes(ctx -> this.setValue(ctx.getSource(), StringArgumentType.getString(ctx, "value")))
            ));
        }
        LiteralArgumentBuilder<ServerCommandSource> finalized = CommandManager.literal("midnightconfig").requires(source -> source.hasPermissionLevel(2)).then(command);

        PlatformFunctions.registerCommand(finalized); commands.add(finalized);
    }
    private int setValue(ServerCommandSource source, Object value) {
        try {
            if (entry.getType() != List.class) entry.set(null,value);
            MidnightConfig.write(modid);
        }
        catch (Exception e) {
            source.sendError(Text.literal("Could not set "+entry.getName()+" to value "+value+": " + e));
            return 0;
        }

        source.sendFeedback(() -> Text.literal("Successfully set " + entry.getName()+" to "+value), true);
        return 1;
    }
    private int setList(ServerCommandSource source, String value, boolean add) {
        try {
            List<String> e = (List<String>)entry.get(null);
            if (add) e.add(value);
            else if (!e.contains(value)) throw new IllegalArgumentException("List does not contain this string!");
            else e.remove(value);
            MidnightConfig.write(modid);
        }
        catch (Exception e) {
            source.sendError(Text.literal((add ? "Could not add "+value+" to " : "Could not remove "+value+" from ")+entry.getName() +": " + e));
            return 0;
        }
        source.sendFeedback(() -> Text.literal((add ? "Successfully added " +value+" to " : "Successfully removed " +value+" from ") +entry.getName()), true);
        return 1;
    }
    private int getValue(ServerCommandSource source) {
        source.sendFeedback(() -> {
            try {return Text.literal("The value of "+entry.getName()+" is "+entry.get(null));
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
            }, true);
        return 0;
    }
}
