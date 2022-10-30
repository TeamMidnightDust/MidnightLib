package eu.midnightdust.forge;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.hats.witch.WitchHatFeatureRenderer;
import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.util.MidnightColorUtil;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "midnightlib", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MidnightLibEvents {
    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WitchHatFeatureRenderer.WITCH_HAT_MODEL_LAYER, WitchHatFeatureRenderer::getTexturedModelData);
    }
    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        for (LiteralArgumentBuilder<ServerCommandSource> command : AutoCommand.commands){
            event.getDispatcher().register(command);
        }
    }
    @SubscribeEvent
    public void registerClientTick(TickEvent.ClientTickEvent event) {
        MidnightColorUtil.tick();
    }
}
