package tictactoe.ui.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.ui.base.IGameEndStateUI;
import tictactoe.connector.ui.listener.GameEndStateUIListener;
import tictactoe.ui.state.common.AbstractJDialogUI;

import javax.swing.*;
import java.awt.*;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class GameEndUI extends AbstractJDialogUI<GameEndStateUIListener> implements IGameEndStateUI {
    private JButton closeButton;
    private JLabel gameStateMessageLabel;
    private String playerName;

    public GameEndUI(Frame owner, String playerName, String dialogTitle) {
        super(owner, dialogTitle);

        setLayout(new GridLayout(2, 1));

        gameStateMessageLabel = new JLabel("", SwingConstants.CENTER);
        closeButton = new JButton("Go TO Main Menu");
        closeButton.addActionListener(e -> getListener().close());
        add(gameStateMessageLabel);
        add(closeButton);
        setModal(true);

        this.playerName = playerName;
    }
}
