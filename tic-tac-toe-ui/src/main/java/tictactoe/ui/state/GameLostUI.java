package tictactoe.ui.state;

import java.awt.*;

public class GameLostUI extends GameEndUI {
    public GameLostUI(Frame owner, String winnerName) {
        super(owner, winnerName, "You don't even equal to " + winnerName);
        getGameStateMessageLabel().setText(String.format("Oh No... Game Won %s.", getPlayerName()));
        pack();
    }
}
