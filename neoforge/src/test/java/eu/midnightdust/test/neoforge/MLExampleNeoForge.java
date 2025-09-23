package eu.midnightdust.test.neoforge;

import eu.midnightdust.test.config.MidnightConfigExample;
import net.neoforged.fml.common.Mod;

@Mod(MLExampleNeoForge.MODID)
public class MLExampleNeoForge {
    public static final String MODID = "modid";
    public MLExampleNeoForge() {
        MidnightConfigExample.init(MODID, MidnightConfigExample.class);
    }
}