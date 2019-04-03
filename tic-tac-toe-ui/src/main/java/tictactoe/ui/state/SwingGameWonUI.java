package tictactoe.ui.state;

import java.awt.*;

public class SwingGameWonUI extends SwingGameEndUI {

    public SwingGameWonUI(Frame owner, String playerName) {
        super(owner, playerName, "Bravo " + playerName);
        getGameStateMessageLabel().setText(String.format("Congrats %s, you WON the game", getPlayerName()));
        pack();
    }
}
