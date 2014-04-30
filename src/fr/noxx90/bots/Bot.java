package fr.noxx90.bots;

import fr.noxx90.emulator.Board;
import fr.noxx90.emulator.Move;

import java.util.List;

/**
 * @author noxx90
 */
public interface Bot {

  Move move(Board board, List<Move> possibleMoves);

}
