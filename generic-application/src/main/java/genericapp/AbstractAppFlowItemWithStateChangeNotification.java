package genericapp;

public abstract class AbstractAppFlowItemWithStateChangeNotification extends AbstractAppFlowItem {

    public AbstractAppFlowItemWithStateChangeNotification(Integer id) {
        super(id);
    }

    @Override
    public void pause() {
        super.pause();
        getAppFlowItemStateChangeListener().appFlowItemPaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        getAppFlowItemStateChangeListener().appFlowItemResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        getAppFlowItemStateChangeListener().appFlowItemStopped(this);
    }
}
