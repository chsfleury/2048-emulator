package fr.noxx90.emulator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.noxx90.logger.Logger.log;

/**
 * @author noxx90
 */
public class Board {

  private Tile[][] tiles;
  private boolean lost;
  private boolean streamSync;
  private List<Tile> tileList;

  public Board() {
    tiles = new Tile[4][4];
  }

  public Board(Board original) {
    this();
    for(int y = 0;y < 4;y++) {
      for(int x = 0;x < 4;x++) {
        tiles[y][x] = original.get(y, x);
      }
    }
  }

  public Tile get(int row, int col) {
    return tiles[row][col];
  }

  public void set(int row, int col, Tile tile) {
    tiles[row][col] = tile;
    streamSync = false;
  }

  public void setLost(boolean lost) {
    this.lost = lost;
  }

  public boolean isLost() {
    return lost;
  }

  public int max() {
    return stream()
            .mapToInt(Tile::getValue)
            .max()
            .getAsInt();
  }

  public String toString() {
    StringBuilder builder = new StringBuilder(100);
    for(int y = 0;y < 4;y++) {
      for(int x = 0;x < 4;x++) {
        Tile tile = tiles[y][x];
        if(tile.isEmpty()) {
          builder.append("_");
        } else {
          builder.append(tile.name().substring(1));
        }
        builder.append(',');
      }
      builder.append('\n');
    }
    return builder.toString();
  }

  public Stream<Tile> stream() {
    if(!streamSync || tileList == null) {
      tileList = new ArrayList<>(16);
      for(int y = 0;y < 4;y++) {
        for(int x = 0;x < 4;x++) {
          tileList.add(tiles[y][x]);
        }
      }
      streamSync = true;
    }

    return tileList.stream();
  }
}
