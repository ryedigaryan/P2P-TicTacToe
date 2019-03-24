package genericapp;

public abstract class AbstractAppStateWithStateChangeNotification extends AbstractAppState {

    public AbstractAppStateWithStateChangeNotification(Integer id) {
        super(id);
    }

    @Override
    public void pause() {
        super.pause();
        this.getAppStateLifecycleListener().appStatePaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        this.getAppStateLifecycleListener().appStateResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        this.getAppStateLifecycleListener().appStateStopped(this);
    }
}
