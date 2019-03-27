package tictactoe.ui.state;

import java.awt.*;

public class GameWonUI extends GameEndUI {

    public GameWonUI(Frame owner, String playerName) {
        super(owner, playerName, "Bravo " + playerName);
        getGameStateMessageLabel().setText(String.format("Congrats %s, you WON the game", getPlayerName()));
        pack();
    }
}
