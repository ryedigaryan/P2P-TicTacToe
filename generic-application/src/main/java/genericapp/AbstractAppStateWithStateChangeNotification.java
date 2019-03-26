package genericapp;

public abstract class AbstractAppStateWithStateChangeNotification extends AbstractAppState {

    public AbstractAppStateWithStateChangeNotification(Integer id) {
        super(id);
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
