package tictactoe.ui.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.common.data.Settings;
import tictactoe.connector.ui.listener.SettingsMenuUIListener;
import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.ui.base.ISettingsMenuStateUI;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class SettingsMenuUI extends AbstractJFrameUI<SettingsMenuUIListener> implements ISettingsMenuStateUI {

    JSlider rowCountSlider;
    JSlider columnCountSlider;
    JSlider winLengthSlider;
    JSlider playersCountSlider;

    JButton saveButton;
    JButton exitButton;

    Settings settings;

    public SettingsMenuUI() {
        super("Setup Tic-Tac-Toe");
        rowCountSlider = newSlider(2, 10);
        columnCountSlider = newSlider(2, 10);
        winLengthSlider = newSlider(2, 5);
        playersCountSlider = newSlider(2, 10);
        ChangeListener setWinLengthToMin = e -> winLengthSlider.setMaximum(Math.min(rowCountSlider.getValue(), columnCountSlider.getValue()));
        rowCountSlider.addChangeListener(setWinLengthToMin);
        columnCountSlider.addChangeListener(setWinLengthToMin);


        saveButton = new JButton("Save changes");
        exitButton = new JButton("Exit to Main Menu");

        saveButton.addActionListener(e -> {
            setEnabled(false);
            getSettings().setRowCount(rowCountSlider.getValue());
            getSettings().setColumnCount(columnCountSlider.getValue());
            getSettings().setWinLength(winLengthSlider.getValue());
            getSettings().setPlayersCount(playersCountSlider.getValue());
            setEnabled(true);
            getListener().setGameSettings(getSettings());
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

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
        rowCountSlider.setValue(settings.getRowCount());
        columnCountSlider.setValue(settings.getColumnCount());
        winLengthSlider.setValue(settings.getWinLength());
        playersCountSlider.setValue(settings.getPlayersCount());
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

    private static JSlider newSlider(int min, int max) {
        JSlider s = new JSlider(min, max);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setMajorTickSpacing(1);
        return s;
    }
}
