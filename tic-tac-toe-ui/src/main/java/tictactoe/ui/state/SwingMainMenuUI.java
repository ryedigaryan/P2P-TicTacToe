package tictactoe.ui.state;

import tictactoe.connector.ui.listener.MainMenuStateUIListener;
import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.ui.base.MainMenuStateUI;

import javax.swing.*;
import java.awt.*;

public class SwingMainMenuUI extends AbstractJFrameUI<MainMenuStateUIListener> implements MainMenuStateUI {
    private JButton startGame;
    private JButton openSettings;

    public SwingMainMenuUI() {
        super("Tic-Tac-Toe Main Menu");
        startGame = new JButton("Start Game");
        openSettings = new JButton("Open Settings");

        startGame.addActionListener(e -> getListener().startGame());
        openSettings.addActionListener(e -> getListener().setupGame());

        setLayout(new GridLayout(1, 2));
        add(startGame);
        add(openSettings);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
