package tictactoe.ui.state;

import tictactoe.connector.ui.listener.MainMenuStateUIListener;
import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.ui.base.IMainMenuStateUI;

import javax.swing.*;
import java.awt.*;

public class MainMenuUI extends AbstractJFrameUI<MainMenuStateUIListener> implements IMainMenuStateUI {
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
