package tictactoe.ui.state.common;

import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.event.ui.base.UIElementBase;

import javax.swing.*;
import java.awt.*;

@Getter @Setter
public class AbstractJDialogUI<L> extends JDialog implements UIElementBase<L> {
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
