package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.lib.util.MidnightColorUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MidnightLibClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MidnightLibClient.onInitializeClient();
    }
}
