package fr.noxx90.bots.evaluation.impl;

import fr.noxx90.bots.evaluation.EvaluationMethod;
import fr.noxx90.emulator.Board;
import fr.noxx90.emulator.Tile;

/**
 * @author noxx90
 */
public class EmptyTileFirstEvaluationMethod implements EvaluationMethod {

  @Override
  public double evaluate(Board board) {
    int count = (int) board
            .stream()
            .filter(Tile::isEmpty)
            .count();

    return count * 2048 + board.max();
  }

}
