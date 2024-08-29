package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLib;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

public class MidnightLibFabric implements ModInitializer, ClientModInitializer {
    @Override @Environment(EnvType.CLIENT)
    public void onInitializeClient() {MidnightLib.onInitializeClient();}
    @Override
    public void onInitialize() {MidnightLib.onInitialize();}
}
