package eu.midnightdust.quilt.core;

import eu.midnightdust.core.MidnightLibServer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;

public class MidnightLibServerQuilt implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer(ModContainer mod) {
        MidnightLibServer.onInitializeServer();
    }
}
