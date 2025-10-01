package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLib;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class MidnightLibFabric implements DedicatedServerModInitializer, ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MidnightLib.onInitializeClient();
        MidnightLib.registerAutoCommand();
    }
    @Override
    public void onInitializeServer() {MidnightLib.registerAutoCommand();}
}
