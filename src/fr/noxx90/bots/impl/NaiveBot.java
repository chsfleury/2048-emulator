package fr.noxx90.bots.impl;

import fr.noxx90.bots.evaluation.Option;
import fr.noxx90.bots.evaluation.impl.EmptyTileFirstEvaluationMethod;
import fr.noxx90.emulator.Board;
import fr.noxx90.emulator.Move;

import java.util.List;
import java.util.Optional;

/**
 * @author noxx90
 */
public class NaiveBot extends AbstractBot {

  public NaiveBot() {
    super();
    evaluationMethod = new EmptyTileFirstEvaluationMethod();
  }

  @Override
  public Move move(Board board, List<Move> possibleMoves) {
    Optional<Option> bestOption = possibleMoves.stream()
            .map(move -> createOption(board, move))
            .max(this);

    if(bestOption.isPresent()) {
      return bestOption.get().getMove();
    } else {
      return Move.TOP;
    }
  }
}
