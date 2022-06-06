package eu.midnightdust.lib.config;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AutoCommand {
    private LiteralArgumentBuilder<ServerCommandSource> command;
    final Field entry;
    final String modid;

    public AutoCommand(Field entry, String modid) {
        this.entry = entry;
        this.modid = modid;
    }

    public void register() {
        command = CommandManager.literal(modid);
        command();
        LiteralArgumentBuilder<ServerCommandSource> finalized = CommandManager.literal("midnightconfig").requires(source -> source.hasPermissionLevel(2)).then(command);

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, registrationEnvironment) -> dispatcher.register(finalized));
    }

    private void command() {
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
        else if (entry.getType() == boolean.class) {
            command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                    CommandManager.literal("true")
                            .executes(ctx -> this.setValue(ctx.getSource(), true))
            ));
            command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                    CommandManager.literal("false")
                            .executes(ctx -> this.setValue(ctx.getSource(), false))
            ));
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
        else
        command = command.then(CommandManager.literal(this.entry.getName()).executes(ctx -> getValue(ctx.getSource())).then(
                CommandManager.argument("value", StringArgumentType.string())
                        .executes(ctx -> this.setValue(ctx.getSource(), StringArgumentType.getString(ctx, "value")))
        ));
    }

    private int setValue(ServerCommandSource source, Object value) {
        try {
            entry.set(null,value);
            MidnightConfig.write(modid);
        }
        catch (Exception e) {
            source.sendError(Text.literal("Could not set "+entry.getName()+" to value "+value+": " + e));
            return 0;
        }

        source.sendFeedback(Text.literal("Successfully set " + entry.getName()+" to "+value), true);
        return 1;
    }
    private int getValue(ServerCommandSource source) {
        try {
            source.sendFeedback(Text.literal("The value of "+entry.getName()+" is "+entry.get(null)), false);
            return 1;
        }
        catch (IllegalAccessException ignored) {}
        return 0;
    }
}
