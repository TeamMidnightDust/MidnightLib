package eu.midnightdust.lib.util;

import java.awt.*;

public class MidnightColorUtil {
    public static float hue;
    public static void tick() {
        if (hue > 1) hue = 0f;
        hue = hue + 0.01f;
    }

    /**
     * @param colorStr e.g. "FFFFFF" or "#FFFFFF"
     * @return Color as RGB
     */
    public static Color hex2Rgb(String colorStr) {
        try {
            return Color.decode("#" + colorStr.replace("#", ""));
        } catch (Exception ignored) {}
        return Color.BLACK;
    }

    public static Color radialRainbow(float saturation, float brightness) {
        return Color.getHSBColor(hue, saturation, brightness);
    }
}
