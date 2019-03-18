package tictactoe.backend.logic;

import tictactoe.backend.listener.GameStateChangeEventListener;
import tictactoe.backend.model.Board;
import tictactoe.backend.model.listener.TileEventListener;

public class GameEngineTest {
    public static void main(String[] args) {
        GameConfig c = new GameConfig(3, 2);

        Board b = new Board(3, 3);
        b.setTileEventListener(new TileEventListener() {
            @Override
            public void valueChanged(int row, int col, int oldValue, int newValue) {
                System.out.println(String.format("tile value at (%d,%d) changed from %d to %d", row, col, oldValue, newValue));
                System.out.println(b.toString());
            }

            @Override
            public void valueErased(int row, int col, int oldValue) {
                System.out.println(String.format("tile value at (%d,%d) erased. Old value was %d", row,col,oldValue));
            }
        });

        GameEngine e = new GameEngine(c, b);
        e.setGameStateChangeEventListener(new GameStateChangeEventListener() {
            @Override
            public void playerWon(int playerNumber) {
                System.out.println("We have a winner: " + playerNumber);
            }
        });

        e.acceptNextPlayerMark(0,0);
        e.acceptNextPlayerMark(1,0);
        e.acceptNextPlayerMark(0, 1);
        e.acceptNextPlayerMark(1,1);
        e.acceptNextPlayerMark(0,2);
        assert e.getWinnerNumber() != null : "We should have a winner!!!";
        assert e.getWinnerNumber() == 0 : "Winner should be 0";
    }
}
