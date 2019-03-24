package tictactoe.app.flow.item;

import genericapp.AbstractAppState;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.event.ui.base.UIElementBase;

@Getter @Setter
public abstract class AbstractTicTacToeAppState<U extends UIElementBase> extends AbstractAppState {
    U ui;

    public AbstractTicTacToeAppState(Integer id, U ui) {
        super(id);
        setUi(ui);
    }

    @Override
    public void run() {
        super.run();
        getUi().start();
        this.getAppStateLifecycleListener().appStateStarted(this);
    }

    @Override
    public void pause() {
        super.pause();
        getUi().pause();
        this.getAppStateLifecycleListener().appStatePaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        getUi().resume();
        this.getAppStateLifecycleListener().appStateResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        getUi().stop();
        this.getAppStateLifecycleListener().appStateStopped(this);
    }
}
