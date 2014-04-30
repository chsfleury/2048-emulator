package fr.noxx90.emulator;

import java.awt.*;
import java.util.Optional;
import java.util.Properties;

/**
 * @author
 */
public enum Tile {

  EMPTY(0),
  N2(2),
  N4(4),
  N8(8),
  N16(16),
  N32(32),
  N64(64),
  N128(128),
  N256(256),
  N512(512),
  N1024(1024),
  N2048(2048);

  private int color;
  private int value;

  private Tile(int value) {
    this.value = value;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public int getColor() {
    return color;
  }

  public int getValue() {
    return value;
  }

  public Optional<Tile> next() {
    return getByValue(value * 2);
  }

  public boolean isEmpty() {
    return this == EMPTY;
  }

  public static Optional<Tile> getByColor(int rgb) {
    for(Tile tile : Tile.values()) {
      if(tile.getColor() == rgb) {
        return Optional.of(tile);
      }
    }

    System.err.println(rgb);
    System.err.println("---");
    for(Tile tile : Tile.values()) {
      System.err.println(tile.name() + " => " + tile.getColor());
    }

    return Optional.empty();
  }

  public static Optional<Tile> getByValue(int value) {
    for(Tile tile : Tile.values()) {
      if(tile.getValue() == value) {
        return Optional.of(tile);
      }
    }
    return Optional.empty();
  }

  public static void init(Properties properties) {
    for(Tile tile : Tile.values()) {
      if(properties.containsKey(tile.name())) {
        String rgb = properties.getProperty(tile.name());
        tile.setColor(Utils.parseColor(rgb));
      } else {
        tile.setColor(Color.black.getRGB());
      }
    }
  }
}
