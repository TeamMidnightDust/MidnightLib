package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class MidnightLibFabric implements ClientModInitializer, DedicatedServerModInitializer {
    @Override @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        MidnightLib.onInitializeClient();
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            MidnightConfigOverviewScreen.addButtonToOptionsScreen(screen, client);
        });
    }
    @Override @Environment(EnvType.SERVER)
    public void onInitializeServer() {MidnightLib.onInitializeServer();}
}
