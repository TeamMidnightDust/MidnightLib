package eu.midnightdust.core.config;

import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.util.PlatformFunctions;

public class MidnightLibConfig extends MidnightConfig {
    @Entry // Enable or disable the MidnightConfig overview screen button
    public static ConfigButton config_screen_list = PlatformFunctions.isModLoaded("modmenu") ? ConfigButton.MODMENU : ConfigButton.TRUE;

    public enum ConfigButton {
        TRUE,FALSE,MODMENU
    }
}
