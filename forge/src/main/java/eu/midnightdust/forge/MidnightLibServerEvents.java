package eu.midnightdust.forge;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.lib.config.AutoCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "midnightlib", value = Dist.DEDICATED_SERVER)
public class MidnightLibServerEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        for (LiteralArgumentBuilder<ServerCommandSource> command : AutoCommand.commands){
            event.getDispatcher().register(command);
        }
    }
}
