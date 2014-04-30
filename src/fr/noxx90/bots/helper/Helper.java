package fr.noxx90.bots.helper;

import fr.noxx90.emulator.Board;
import fr.noxx90.emulator.Move;
import fr.noxx90.emulator.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author noxx90
 */
public class Helper {

  public List<Move> computePossibleMoves(Board board) {
    List<Move> possibleMoves = new ArrayList<>(4);

    if(isTopPossible(board)) possibleMoves.add(Move.TOP);
    if(isBottomPossible(board)) possibleMoves.add(Move.BOTTOM);
    if(isLeftPossible(board)) possibleMoves.add(Move.LEFT);
    if(isRightPossible(board)) possibleMoves.add(Move.RIGHT);

    return possibleMoves;
  }

  public Board pull(Board current, Move move) {
    switch (move) {
      case TOP:
        return pullTop(current);
      case BOTTOM:
        return pullBottom(current);
      case LEFT:
        return pullLeft(current);
      case RIGHT:
        return pullRight(current);
      default:
        return null;
    }
  }

  public Board pullTop(Board current) {
    Board newBoard = new Board();

    for(int x = 0;x < 4;x++) {
      TileStack stack = new TileStack();
      for(int y = 0;y < 4;y++) {
        stack.add(current.get(y, x));
      }

      int y = 0;
      for(Tile tile : stack.getTiles()) {
        newBoard.set(y, x, tile);
        y++;
      }

      while(y < 4) {
        newBoard.set(y, x, Tile.EMPTY);
        y++;
      }
    }
    return newBoard;
  }

  public Board pullBottom(Board current) {
    Board newBoard = new Board();

    for(int x = 0;x < 4;x++) {
      TileStack stack = new TileStack();
      for(int y = 3;y >= 0;y--) {
        stack.add(current.get(y, x));
      }

      int y = 3;
      for(Tile tile : stack.getTiles()) {
        newBoard.set(y, x, tile);
        y--;
      }

      while(y >= 0) {
        newBoard.set(y, x, Tile.EMPTY);
        y--;
      }
    }
    return newBoard;
  }

  public Board pullLeft(Board current) {
    Board newBoard = new Board();

    for(int y = 0;y < 4;y++) {
      TileStack stack = new TileStack();
      for(int x = 0;x < 4;x++) {
        stack.add(current.get(y, x));
      }

      int x = 0;
      for(Tile tile : stack.getTiles()) {
        newBoard.set(y, x, tile);
        x++;
      }

      while(x < 4) {
        newBoard.set(y, x, Tile.EMPTY);
        x++;
      }
    }
    return newBoard;
  }

  public Board pullRight(Board current) {
    Board newBoard = new Board();

    for(int y = 0;y < 4;y++) {
      TileStack stack = new TileStack();
      for(int x = 3;x >= 0;x--) {
        stack.add(current.get(y, x));
      }

      int x = 3;
      for(Tile tile : stack.getTiles()) {
        newBoard.set(y, x, tile);
        x--;
      }

      while(x >= 0) {
        newBoard.set(y, x, Tile.EMPTY);
        x--;
      }
    }
    return newBoard;
  }

  private boolean isTopPossible(Board board) {
    for(int y = 1;y < 4;y++) {
      for(int x = 0;x < 4;x++) {
        Tile current = board.get(y, x);
        Tile other = board.get(y - 1, x);
        if(current != Tile.EMPTY && (other == Tile.EMPTY || other == current)) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean isBottomPossible(Board board) {
    for(int y = 0;y < 3;y++) {
      for(int x = 0;x < 4;x++) {
        Tile current = board.get(y, x);
        Tile other = board.get(y + 1, x);
        if(current != Tile.EMPTY && (other == Tile.EMPTY || other == current)) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean isLeftPossible(Board board) {
    for(int y = 0;y < 4;y++) {
      for(int x = 1;x < 4;x++) {
        Tile current = board.get(y, x);
        Tile other = board.get(y, x - 1);
        if(current != Tile.EMPTY && (other == Tile.EMPTY || other == current)) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean isRightPossible(Board board) {
    for(int y = 0;y < 4;y++) {
      for(int x = 0;x < 3;x++) {
        Tile current = board.get(y, x);
        Tile other = board.get(y, x + 1);
        if(current != Tile.EMPTY && (other == Tile.EMPTY || other == current)) {
          return true;
        }
      }
    }

    return false;
  }
}
