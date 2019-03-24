package genericapp;

public abstract class AbstractAppStateWithStateChangeNotification extends AbstractAppState {

    public AbstractAppStateWithStateChangeNotification(Integer id) {
        super(id);
    }

    @Override
    public void pause() {
        super.pause();
        this.getAppStateLifecycleListener().appFlowItemPaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        this.getAppStateLifecycleListener().appFlowItemResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        this.getAppStateLifecycleListener().appFlowItemStopped(this);
    }
}
