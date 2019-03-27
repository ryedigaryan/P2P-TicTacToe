package tictactoe.test.backend.manager;

import tictactoe.backend.logic.GameConfig;
import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.manager.LocalGameManager;
import tictactoe.backend.model.Board;
import tictactoe.ui.state.GameBoardUI;

public class LocalGameManagerTest {
    public static void main(String[] args) {
        GameConfig c = new GameConfig(4, 2);
        Board b = new Board(5, 6);
        GameEngine e = new GameEngine(c, b);

        GameBoardUI bUI = new GameBoardUI(b.getRowCount(), b.getColumnCount());
        LocalGameManager m = new LocalGameManager(e, bUI);
    }
}
