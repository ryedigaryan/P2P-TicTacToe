package tictactoe.app.state;

import generic.app.AbstractAppState;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.ui.base.StateUI;

@Getter
@Setter
public class AbstractTicTacToeAppStateWithoutNotification<U extends StateUI> extends AbstractAppState {
    U ui;

    public AbstractTicTacToeAppStateWithoutNotification(Integer id, U ui) {
        super(id);
        setUi(ui);
    }

    @Override
    public void run() {
        super.run();
        getUi().start();
    }

    @Override
    public void pause() {
        super.pause();
        getUi().pause();
    }

    @Override
    public void resume() {
        super.resume();
        getUi().resume();
    }

    @Override
    public void stop() {
        super.stop();
        getUi().stop();
    }
}
