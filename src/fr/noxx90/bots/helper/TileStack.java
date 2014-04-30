package fr.noxx90.bots.helper;

import fr.noxx90.emulator.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fr.noxx90.logger.Logger.log;

/**
 * @author noxx90
 */
public class TileStack {

  private List<Tile> tiles;
  private boolean lastIsMerged;

  public TileStack() {
    tiles = new ArrayList<>(4);
    lastIsMerged = false;
  }

  public boolean add(Tile tile) {
    if(tiles.isEmpty() || last() != tile || lastIsMerged) {
      lastIsMerged = false;
      return tiles.add(tile);
    } else {
      lastIsMerged = true;
      return merge();
    }
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  private Tile last() {
    return tiles.get(tiles.size() - 1);
  }

  private boolean merge() {
    Tile last = tiles.remove(tiles.size() - 1);
    Optional<Tile> next = last.next();
    if(next.isPresent()) {
      tiles.add(next.get());
      return true;
    } else {
      log("impossible merge");
      return false;
    }
  }

}
