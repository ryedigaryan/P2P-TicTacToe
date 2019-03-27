package tictactoe.ui.state.common;

import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.ui.base.StateUI;

import javax.swing.*;
import java.awt.*;

@Getter @Setter
public abstract class AbstractJFrameUI<L> extends JFrame implements StateUI<L> {
    L listener;

    public AbstractJFrameUI(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
