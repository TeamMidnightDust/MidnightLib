package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.core.MidnightLibServer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class MidnightLibFabric implements ClientModInitializer, DedicatedServerModInitializer {
    @Override @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        MidnightLibClient.onInitializeClient();
    }
    @Override @Environment(EnvType.SERVER)
    public void onInitializeServer() {
        MidnightLibServer.onInitializeServer();
    }
}
