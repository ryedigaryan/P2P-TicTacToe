package tictactoe.ui.state;


import javax.swing.*;
import java.awt.*;

public class GameLostUI extends GameEndUI {
    public GameLostUI(Frame owner, String winnerName) {
        super(owner, winnerName);
        getGameStateMessageLabel().setText(String.format("Oh No... Game Won %s.", getPlayerName()));
        pack();
    }
}
