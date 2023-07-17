package eu.midnightdust.forge;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.core.MidnightLibServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod("midnightlib")
public class MidnightLibForge {
    public MidnightLibForge() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (remote, server) -> true));
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MidnightLibClient::onInitializeClient);
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> MidnightLibServer::onInitializeServer);
    }
}
