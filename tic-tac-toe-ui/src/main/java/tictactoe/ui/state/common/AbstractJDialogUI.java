package tictactoe.ui.state.common;

import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.ui.base.StateUI;

import javax.swing.*;
import java.awt.*;

@Getter @Setter
public class AbstractJDialogUI<L> extends JDialog implements StateUI<L> {
    L listener;

    public AbstractJDialogUI(Frame owner) {
        super(owner);
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void pause() {
        setVisible(false);
    }

    @Override
    public void resume() {
        setVisible(true);
    }

    @Override
    public void stop() {
        dispose();
    }
}
