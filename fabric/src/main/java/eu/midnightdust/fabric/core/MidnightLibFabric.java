package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLib;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class MidnightLibFabric implements ClientModInitializer, DedicatedServerModInitializer {
    @Override @Environment(EnvType.CLIENT)
    public void onInitializeClient() {MidnightLib.onInitializeClient();}
    @Override @Environment(EnvType.SERVER)
    public void onInitializeServer() {MidnightLib.onInitializeServer();}
}
