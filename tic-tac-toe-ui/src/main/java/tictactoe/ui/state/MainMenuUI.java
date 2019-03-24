package tictactoe.ui.state;

import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.event.ui.generator.MainMenuEventGenerator;
import tictactoe.connector.event.ui.listener.MainMenuListener;

import javax.swing.*;
import java.awt.*;

@Getter @Setter
public class MainMenuUI extends JFrame implements MainMenuEventGenerator {
    MainMenuListener mainMenuListener;

    JButton startGame;
    JButton openSettings;

    public MainMenuUI(MainMenuListener listener) {
        this.mainMenuListener = listener;
        startGame = new JButton("Start Game");
        openSettings = new JButton("Open Settings");

        startGame.addActionListener(e -> mainMenuListener.startGame());
        openSettings.addActionListener(e -> mainMenuListener.setupGame());

        setLayout(new GridLayout(1, 2));
        add(startGame);
        add(openSettings);
    }

    @Override
    public void start() {
        setVisible(true);
    }
}
