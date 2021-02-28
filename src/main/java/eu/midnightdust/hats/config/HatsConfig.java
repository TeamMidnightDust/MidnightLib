package eu.midnightdust.hats.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class HatsConfig extends MidnightConfig {

    @Entry // Enable or disable event hats
    public static boolean event_hats = true;

    @Entry // Enable or disable hats for contributors, friends and donors.
    public static boolean special_hats = true;
}
