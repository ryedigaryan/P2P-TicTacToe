package tictactoe.ui.state;

import java.awt.*;

public class GameDrawnUI extends GameEndUI {
    public GameDrawnUI(Frame owner) {
        super(owner, null, "You all suck");
        getGameStateMessageLabel().setText("What a crap ... its DRAW");
        pack();
    }
}
