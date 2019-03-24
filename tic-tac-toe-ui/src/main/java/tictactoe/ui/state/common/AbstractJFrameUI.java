package tictactoe.ui.state.common;

import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.event.ui.base.UIElementBase;

import javax.swing.*;

@Getter @Setter
public abstract class AbstractJFrameUI<L> extends JFrame implements UIElementBase {
    L listener;

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
