package eu.midnightdust.fabric.core;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.hats.witch.WitchHatFeatureRenderer;
import eu.midnightdust.lib.util.MidnightColorUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class MidnightLibClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(WitchHatFeatureRenderer.WITCH_HAT_MODEL_LAYER, WitchHatFeatureRenderer::getTexturedModelData);
        MidnightLibClient.onInitializeClient();
        ClientTickEvents.END_CLIENT_TICK.register(
                client -> MidnightColorUtil.tick()
        );
    }
}
