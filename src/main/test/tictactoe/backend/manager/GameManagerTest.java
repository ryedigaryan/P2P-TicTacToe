package tictactoe.backend.manager;

import tictactoe.backend.helper.GameConfig;
import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.model.Board;
import tictactoe.ui.game.GameBoardUI;

public class GameManagerTest {
    public static void main(String[] args) {
        GameConfig c = new GameConfig(3, 2);
        Board b = new Board(3, 3);
        GameEngine e = new GameEngine(c, b);

        GameBoardUI bUI = new GameBoardUI(b.getRowCount(), b.getColumnCount());
        GameManager m = new GameManager(e, bUI);
    }
}
