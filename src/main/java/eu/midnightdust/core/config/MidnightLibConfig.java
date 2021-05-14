package eu.midnightdust.core.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.loader.api.FabricLoader;

public class MidnightLibConfig extends MidnightConfig {
    @Comment public static Comment midnightlib_description;
    @Entry // Enable or disable the MidnightConfig overview screen button
    public static boolean config_screen_list = !FabricLoader.getInstance().isModLoaded("modmenu");
    @Comment public static Comment midnighthats_description;
    @Entry // Enable or disable event hats
    public static boolean event_hats = true;
    @Entry // Enable or disable hats for contributors, friends and donors.
    public static boolean special_hats = true;

}
