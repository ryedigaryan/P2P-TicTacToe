package tictactoe.ui.state;

import tictactoe.connector.event.ui.base.IGameResultUI;
import tictactoe.connector.event.ui.listener.GameResultScreenListener;
import tictactoe.ui.state.common.AbstractJDialogUI;

import javax.swing.*;
import java.awt.*;

public class GameResultUI extends AbstractJDialogUI<GameResultScreenListener> implements IGameResultUI {
    JButton closeButton;

    public GameResultUI(Frame owner, String winnerName, String loserName) {
        super(owner);

        setLayout(new GridLayout(2, 1));

        closeButton = new JButton("Go TO Main Menu");
        closeButton.addActionListener(e -> getListener().close());


        if(winnerName == null && loserName == null)
            add(new JLabel("DRAW"));
        else
            add(new JLabel("Won : " + winnerName + " Lost: " + loserName));
        add(closeButton);

        setModal(true);

        pack();
    }
}
