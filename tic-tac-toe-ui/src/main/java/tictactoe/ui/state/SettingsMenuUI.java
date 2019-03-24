package tictactoe.ui.state;

import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.event.ui.base.ISettingsMenuUI;
import tictactoe.connector.event.ui.listener.SettingsMenuListener;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingsMenuUI extends AbstractJFrameUI<SettingsMenuListener> implements ISettingsMenuUI {

    JSlider rowCountSlider;
    JSlider columnCountSlider;
    JSlider winLengthSlider;
    JSlider playersCountSlider;

    JButton saveButton;
    JButton exitButton;

    public SettingsMenuUI() {
        rowCountSlider = new JSlider(2, 10, 5);
        columnCountSlider = new JSlider(2, 10, 5);
        winLengthSlider = new JSlider(2, 5, 5);
        playersCountSlider = new JSlider(2, 2);
        ChangeListener setWinLengthToMin = e -> {
            winLengthSlider.setMaximum(Math.min(rowCountSlider.getValue(), columnCountSlider.getValue()));
        };
        rowCountSlider.addChangeListener(setWinLengthToMin);
        columnCountSlider.addChangeListener(setWinLengthToMin);


        saveButton = new JButton("Save changes");
        exitButton = new JButton("Exit to Main Menu");

        saveButton.addActionListener(e -> {
            setEnabled(false);
            getListener().setRowCount(rowCountSlider.getValue());
            getListener().setColumnCount(columnCountSlider.getValue());
            getListener().setWinLength(winLengthSlider.getValue());
            getListener().setPlayersCount(playersCountSlider.getValue());
            setEnabled(true);
            getListener().backToMainMenu();
        });

        exitButton.addActionListener(e -> getListener().backToMainMenu());

        setLayout(new GridLayout(6, 2));

        add(new JLabel("Row Count:")); addSlider(rowCountSlider);
        add(new JLabel("Column Count:")); addSlider(columnCountSlider);
        add(new JLabel("Win Length:")); addSlider(winLengthSlider);
        add(new JLabel("Players Count:")); addSlider(playersCountSlider);
        add(new JLabel("")); add(saveButton);
        add(new JLabel("")); add(exitButton);

        pack();
    }

    void addSlider(JSlider s) {
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setMajorTickSpacing(1);
        add(s);
    }
}
