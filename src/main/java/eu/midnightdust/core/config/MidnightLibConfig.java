package eu.midnightdust.core.config;

import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.util.PlatformFunctions;

public class MidnightLibConfig extends MidnightConfig {
    //? if fabric {
    @Entry public static ConfigButton config_screen_list = PlatformFunctions.isModLoaded("modmenu") ? ConfigButton.MODMENU : ConfigButton.TRUE;

    public static boolean shouldShowButton() {
        return config_screen_list.equals(ConfigButton.TRUE) || (config_screen_list.equals(ConfigButton.MODMENU) && !PlatformFunctions.isModLoaded("modmenu"));
    }
    //?} else {
    /*@Entry public static ConfigButton config_screen_list = ConfigButton.FALSE;

    public static boolean shouldShowButton() {
        return config_screen_list.equals(ConfigButton.TRUE);
    }
    *///?}
    public enum ConfigButton {
        TRUE, FALSE /*? if fabric {*/, MODMENU /*?}*/
    }
}
