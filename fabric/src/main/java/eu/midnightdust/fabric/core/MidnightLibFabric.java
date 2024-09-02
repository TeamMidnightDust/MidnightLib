package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLib;
import net.fabricmc.api.*;

public class MidnightLibFabric implements DedicatedServerModInitializer, ClientModInitializer {
    @Override @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        MidnightLib.onInitializeClient();
        MidnightLib.registerAutoCommand();
    }
    @Override
    public void onInitializeServer() {MidnightLib.registerAutoCommand();}
}
