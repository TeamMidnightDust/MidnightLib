package eu.midnightdust.test.fabric;

import eu.midnightdust.test.config.MidnightConfigExample;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

public class MLExampleFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MidnightConfig.init("modid", MidnightConfigExample.class);
    }
}
