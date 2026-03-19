package eu.midnightdust.test;

import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.test.config.MidnightConfigExample;
import net.fabricmc.api.ModInitializer;

//? fabric {
public class MidnightLibTest implements ModInitializer {
    @Override
    public void onInitialize() {
        MidnightConfig.init("modid", MidnightConfigExample.class);
    }
}
//?}