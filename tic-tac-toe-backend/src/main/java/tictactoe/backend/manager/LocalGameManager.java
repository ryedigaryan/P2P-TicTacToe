package tictactoe.backend.manager;

import tictactoe.backend.listener.GameStateChangeListener;
import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.model.listener.TileEventListener;
import tictactoe.backend.grdon.TemporarySolution;
import tictactoe.connector.event.generator.BoardEventGenerator;
import tictactoe.connector.event.listener.TileClickListener;

/**
 * A basic game manager which is designed for a game in one computer.
 */
public class LocalGameManager implements TileEventListener, GameStateChangeListener, TileClickListener {

    private final GameEngine gameEngine;
    private final BoardEventGenerator boardUI;

    // TODO: 3/18/2019 Extend GameBoardUI from interface, which will be common for all UserInterfaces which wanna play this game...
    public LocalGameManager(GameEngine gameEngine, BoardEventGenerator boardUI) {
        this.gameEngine = gameEngine;
        this.boardUI = boardUI;
        gameEngine.setGameStateChangeListener(this);
        gameEngine.getBoard().setTileEventListener(this);
        boardUI.setTileClickListener(this);
    }

    @Override
    public void valueChanged(int row, int col, int oldValue, int newValue) {
        assert oldValue == -1 : String.format("Player shouldn't change value of cell (%d,%d) to %d, because it had value %d", row, col, newValue, oldValue);
        if(gameEngine.getCurrentPlayerNumber() == 0) {
            boardUI.markX(row, col);
        }
        else if(gameEngine.getCurrentPlayerNumber() == 1) {
            boardUI.markO(row, col);
        }
        else {
            assert false : "GameEngine.currentPlayerNumber should be 0 or 1";
        }
    }

    @Override
    public void valueErased(int row, int col, int oldValue) {
        boardUI.removeMark(row, col);
    }

    @Override
    public void playerWon(int playerNumber) {
        TemporarySolution.showWinnerNumber(playerNumber);
    }

    @Override
    public void tileClicked(int row, int col) {
        gameEngine.acceptNextPlayerMark(row, col);
    }
}
