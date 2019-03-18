package tictactoe.backend.controller;

import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.model.listener.TileEventListener;
import tictactoe.ui.game.GameBoardUI;

public class BoardController implements TileEventListener {

    GameEngine gameEngine;
    GameBoardUI boardUI;

    public BoardController(GameEngine gameEngine, GameBoardUI boardUI) {
        this.gameEngine = gameEngine;
        this.boardUI = boardUI;
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
}
