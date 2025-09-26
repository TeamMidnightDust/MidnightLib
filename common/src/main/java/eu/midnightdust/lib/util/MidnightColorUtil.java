package eu.midnightdust.lib.util;

import java.awt.Color;

@Deprecated
public class MidnightColorUtil {
    /**
     * @param colorStr e.g. "FFFFFF" or "#FFFFFF"
     * @return Color as RGB
     */
    @Deprecated
    public static Color hex2Rgb(String colorStr) {
        try { return Color.decode("#" + colorStr.replace("#", ""));
        } catch (Exception ignored) { return Color.BLACK; }
    }
}
