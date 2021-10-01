package eu.midnightdust.core;

import eu.midnightdust.hats.bunny.BunnyEarsFeatureRenderer;
import eu.midnightdust.hats.christmas.ChristmasHatFeatureRenderer;
import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.hats.tater.TinyPotatoFeatureRenderer;
import eu.midnightdust.hats.web.HatLoader;
import eu.midnightdust.hats.witch.WitchHatFeatureRenderer;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;

import java.util.Calendar;

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public class MidnightLibClient implements ClientModInitializer {

    public static final String MOD_ID = "midnightlib";
    public static Event EVENT = Event.NONE;

    @Override
    public void onInitializeClient() {
        MidnightConfig.init("midnightlib", MidnightLibConfig.class);
        MidnightConfig.useTooltipForTitle = MidnightLibConfig.titleStyle.equals(MidnightLibConfig.TitleStyle.TOOLTIP);

        EntityModelLayerRegistry.registerModelLayer(BunnyEarsFeatureRenderer.RABBIT_EARS_MODEL_LAYER, BunnyEarsFeatureRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ChristmasHatFeatureRenderer.CHRISTMAS_HAT_MODEL_LAYER, ChristmasHatFeatureRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TinyPotatoFeatureRenderer.TINY_POTATO_MODEL_LAYER, TinyPotatoFeatureRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WitchHatFeatureRenderer.WITCH_HAT_MODEL_LAYER, WitchHatFeatureRenderer::getTexturedModelData);
        if (MidnightLibConfig.special_hats) HatLoader.init();
        if (MidnightLibConfig.event_hats) EVENT = getEvent();
    }
    private Event getEvent() {
        if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.APRIL && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 4) return Event.EASTER;
        if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.OCTOBER && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 30) return Event.HALLOWEEN;
        if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 10) return Event.FABRIC;
        if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 23 && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 26) return Event.CHRISTMAS;
        return Event.NONE;
    }

    public enum Event {
        NONE, EASTER, HALLOWEEN, FABRIC, CHRISTMAS
    }
}
