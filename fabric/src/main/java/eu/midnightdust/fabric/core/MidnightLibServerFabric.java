package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLibServer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class MidnightLibServerFabric implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        MidnightLibServer.onInitializeServer();
    }
}
