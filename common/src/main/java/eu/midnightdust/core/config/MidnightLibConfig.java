package eu.midnightdust.core.config;

import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.util.PlatformFunctions;

import java.util.Objects;

public class MidnightLibConfig extends MidnightConfig {
    public static final boolean HAS_MODMENU = PlatformFunctions.isModLoaded("modmenu") || Objects.equals(PlatformFunctions.getPlatformName(), "neoforge");

    @Entry public static ConfigButton config_screen_list = HAS_MODMENU ? ConfigButton.MODMENU : ConfigButton.TRUE;

    public enum ConfigButton {
        TRUE, FALSE, MODMENU
    }

    public static boolean shouldShowButton() {
        return config_screen_list.equals(ConfigButton.TRUE) || (config_screen_list.equals(ConfigButton.MODMENU) && !HAS_MODMENU);
    }
}
