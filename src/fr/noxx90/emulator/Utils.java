package fr.noxx90.emulator;

import java.awt.*;

/**
 * @author noxx90
 */
public class Utils {

  public static int parseColor(String rgb) {
    String[] values = rgb.split(",");
    int r = Integer.parseInt(values[0]);
    int g = Integer.parseInt(values[1]);
    int b = Integer.parseInt(values[2]);
    return new Color(r, g, b).getRGB();
  }

}
