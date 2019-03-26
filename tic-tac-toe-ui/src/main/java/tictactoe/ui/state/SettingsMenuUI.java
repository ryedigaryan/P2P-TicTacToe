package tictactoe.ui.state;

import tictactoe.connector.event.ui.listener.SettingsMenuUIListener;
import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.event.ui.base.ISettingsMenuStateUI;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingsMenuUI extends AbstractJFrameUI<SettingsMenuUIListener> implements ISettingsMenuStateUI {

    JSlider rowCountSlider;
    JSlider columnCountSlider;
    JSlider winLengthSlider;
    JSlider playersCountSlider;

    JButton saveButton;
    JButton exitButton;

    public SettingsMenuUI() {
        rowCountSlider = newSlider(2, 10, 5);
        columnCountSlider = newSlider(2, 10, 5);
        winLengthSlider = newSlider(2, 5, 5);
        playersCountSlider = newSlider(2, 10, 2);
        ChangeListener setWinLengthToMin = e -> winLengthSlider.setMaximum(Math.min(rowCountSlider.getValue(), columnCountSlider.getValue()));
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
            getListener().close(true);
        });

        exitButton.addActionListener(e -> getListener().close(false));

        setLayout(new GridLayout(6, 1));

        add("Row Count:", rowCountSlider);
        add("Column Count:", columnCountSlider);
        add("Win Length:", winLengthSlider);
        add("Players Count:", playersCountSlider);
        add(saveButton);
        add(exitButton);

        pack();
    }

    private void add(String labelText, JSlider s) {
        JPanel p = panel(new JLabel(labelText), s);
        add(p);
    }

    private static JPanel panel(JLabel l, JSlider s) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(l);
        panel.add(s);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }

    private static JSlider newSlider(int min, int max, int value) {
        JSlider s = new JSlider(min, max, value);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setMajorTickSpacing(1);
        return s;
    }
}
