package eu.midnightdust.lib.util;

import java.awt.*;

public class MidnightColorUtil {
    /**
     * @credit https://stackoverflow.com/questions/4129666/how-to-convert-hex-to-rgb-using-java
     * @param colorStr e.g. "FFFFFF"
     * @return Color as RGB
     */
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 0, 2 ), 16 ),
                Integer.valueOf( colorStr.substring( 2, 4 ), 16 ),
                Integer.valueOf( colorStr.substring( 4, 6 ), 16 ));
    }
}
