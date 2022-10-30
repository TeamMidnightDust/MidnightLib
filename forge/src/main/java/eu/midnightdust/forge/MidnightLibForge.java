package eu.midnightdust.forge;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.core.MidnightLibServer;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod("midnightlib")
public class MidnightLibForge {
    public MidnightLibForge() {
        // Submit our event bus to let architectury register our content on the right time
        //EventBuses.registerModEventBus("midnightlib", FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (remote, server) -> true));
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MidnightLibClient::onInitializeClient);
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> MidnightLibServer::onInitializeServer);
        ModList.get().applyForEachModContainer(modContainer -> {
            if (MidnightConfig.configClass.containsKey(modContainer.getModId())) {
                ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                        new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> MidnightConfig.getScreen(parent, modContainer.getModId())));
            }
            System.out.println(modContainer.getModId());
            System.out.println("motschen");
            return true;
        }
        );
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> MidnightConfig.getScreen(parent, "midnightlib")));
        MinecraftForge.EVENT_BUS.register(new MidnightLibEvents());
    }
}
