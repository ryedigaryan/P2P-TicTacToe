package tictactoe.ui.state;

import java.awt.*;

public class SwingGameDrawnUI extends SwingGameEndUI {
    public SwingGameDrawnUI(Frame owner) {
        super(owner, null, "You all suck");
        getGameStateMessageLabel().setText("What a crap ... its DRAW");
        pack();
    }
}
