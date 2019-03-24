package tictactoe.ui.state;

import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.event.ui.base.IMainMenuUI;
import tictactoe.connector.event.ui.listener.MainMenuListener;

import javax.swing.*;
import java.awt.*;

public class MainMenuUI extends AbstractJFrameUI<MainMenuListener> implements IMainMenuUI {
    private JButton startGame;
    private JButton openSettings;

    public MainMenuUI() {
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
