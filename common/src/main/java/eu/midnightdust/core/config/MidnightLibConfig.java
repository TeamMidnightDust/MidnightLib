package eu.midnightdust.core.config;

import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.util.PlatformVariables;

public class MidnightLibConfig extends MidnightConfig {
    @Comment(centered = true) public static Comment midnightlib_description;
    @Entry // Enable or disable the MidnightConfig overview screen button
    public static ConfigButton config_screen_list = PlatformVariables.isModLoaded("modmenu") ? ConfigButton.MODMENU : ConfigButton.TRUE;
    @Comment(centered = true) public static Comment midnighthats_description;
    @Entry // Enable or disable hats for contributors, friends and donors.
    public static boolean special_hats = true;

    public enum ConfigButton {
        TRUE,FALSE,MODMENU
    }
}
