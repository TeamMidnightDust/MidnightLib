package eu.midnightdust.quilt.core;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.lib.util.MidnightColorUtil;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

public class MidnightLibClientQuilt implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        MidnightLibClient.onInitializeClient();
        ClientTickEvents.END.register(
                client -> MidnightColorUtil.tick()
        );
    }
}
