package fr.noxx90.bots.impl;

import fr.noxx90.bots.Bot;
import fr.noxx90.bots.helper.Helper;
import fr.noxx90.emulator.Board;
import fr.noxx90.emulator.Move;

import java.util.List;
import java.util.Random;

/**
 * @author noxx90
 */
public class RandomBot implements Bot {

  private Random random;
  private Helper helper;

  public RandomBot() {
    random = new Random();
    helper = new Helper();
  }

  @Override
  public Move move(Board board, List<Move> possibleMoves) {
    return possibleMoves.get(random.nextInt(possibleMoves.size()));
  }
}
