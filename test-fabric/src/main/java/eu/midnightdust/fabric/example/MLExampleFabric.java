package eu.midnightdust.fabric.example;

import eu.midnightdust.fabric.example.config.MidnightConfigExample;
import net.fabricmc.api.ModInitializer;

public class MLExampleFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MidnightConfigExample.init("modid", MidnightConfigExample.class);
    }
}
