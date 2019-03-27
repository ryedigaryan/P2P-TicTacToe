package tictactoe.ui.state;

import tictactoe.connector.ui.GameStateDescriptor;
import tictactoe.connector.ui.base.IPausedStateStateUI;
import tictactoe.connector.ui.listener.PausedStateUIListener;
import tictactoe.ui.state.common.AbstractJDialogUI;

import javax.swing.*;
import java.awt.*;

public class PausedPopUp extends AbstractJDialogUI<PausedStateUIListener> implements IPausedStateStateUI {

    JLabel gameStateInfoLabel;
    JButton resumeGameButton;
    JButton leaveGameButton;

    public PausedPopUp(Frame popUpOwner, GameStateDescriptor gameStateDescriptor) {
        super(popUpOwner, "Paused Tic-Tac-Toe");
        gameStateInfoLabel = new JLabel(gameStateDescriptor.asString());
        resumeGameButton = new JButton("Resume Game");
        leaveGameButton = new JButton("Leave Game");

        resumeGameButton.addActionListener(e -> getListener().continueGame());
        leaveGameButton.addActionListener(e -> getListener().leaveGame());

        setLayout(new GridLayout(3, 1));

        add(gameStateInfoLabel);
        add(resumeGameButton);
        add(leaveGameButton);

        setModal(true);
        pack();
    }
}
