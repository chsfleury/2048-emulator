package fr.noxx90.bots.evaluation;

import fr.noxx90.emulator.Board;
import fr.noxx90.emulator.Move;

/**
 * @author noxx90
 */
public class Option {

  private Move move;
  private Board board;
  private double score;

  public Option(Move move, Board board, double score) {
    this.move = move;
    this.board = board;
    this.score = score;
  }

  public double getScore() {
    return score;
  }

  public Move getMove() {
    return move;
  }

}
