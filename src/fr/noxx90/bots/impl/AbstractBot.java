package fr.noxx90.bots.impl;

import fr.noxx90.bots.Bot;
import fr.noxx90.bots.evaluation.EvaluationMethod;
import fr.noxx90.bots.evaluation.Option;
import fr.noxx90.bots.helper.Helper;
import fr.noxx90.emulator.Board;
import fr.noxx90.emulator.Move;

import java.util.Comparator;

/**
 * @author noxx90
 */
public abstract class AbstractBot implements Bot, Comparator<Option> {

  protected Helper helper;
  protected EvaluationMethod evaluationMethod;

  public AbstractBot() {
    helper = new Helper();
  }

  public Option createOption(Board board, Move move) {
    Board newBoard = helper.pull(board, move);
    double score = evaluationMethod.evaluate(newBoard);
    return new Option(move, newBoard, score);
  }

  public int compare(Option o1, Option o2) {
    return Double.compare(o1.getScore(), o2.getScore());
  }
}
