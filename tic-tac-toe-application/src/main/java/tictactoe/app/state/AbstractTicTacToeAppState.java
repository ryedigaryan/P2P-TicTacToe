package tictactoe.app.state;

import tictactoe.connector.ui.base.StateUI;

public abstract class AbstractTicTacToeAppState<U extends StateUI> extends AbstractTicTacToeAppStateWithoutNotification<U> {

    public AbstractTicTacToeAppState(Integer id, U ui) {
        super(id, ui);
    }

    @Override
    public void start() {
        super.start();
        getAppStateLifecycleListener().appStateStarted(this);
    }

    @Override
    public void pause() {
        super.pause();
        getAppStateLifecycleListener().appStatePaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        getAppStateLifecycleListener().appStateResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        getAppStateLifecycleListener().appStateStopped(this);
    }
}
