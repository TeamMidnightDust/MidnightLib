package eu.midnightdust.forge;

import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.util.MidnightColorUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(modid = "midnightlib", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MidnightLibClientEvents {
    @SubscribeEvent
    public void registerClientTick(TickEvent.ClientTickEvent event) {
        MidnightColorUtil.tick();
    }
    @SubscribeEvent
    public void onPostInit(FMLLoadCompleteEvent event) {
        ModList.get().applyForEachModContainer(modContainer -> {
            if (MidnightConfig.configClass.containsKey(modContainer.getModId())) {
                modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                        new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> MidnightConfig.getScreen(parent, modContainer.getModId())));
            }
            return true;
        });
    }
}
