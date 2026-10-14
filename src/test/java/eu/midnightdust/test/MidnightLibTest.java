package eu.midnightdust.test;

//? fabric {
import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.test.config.MidnightConfigExample;
import net.fabricmc.api.ModInitializer;

public class MidnightLibTest implements ModInitializer {
    @Override
    public void onInitialize() {
        MidnightConfig.register("modid", MidnightConfigExample.class);
    }
}
//?}