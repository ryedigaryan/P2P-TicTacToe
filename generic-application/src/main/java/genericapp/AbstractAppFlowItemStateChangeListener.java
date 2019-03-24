package genericapp;

public abstract class AbstractAppFlowItemStateChangeListener implements AppFlowItemStateChangeListener {
    @Override
    public void appFlowItemStarted(AppFlowItem eventSource) {
        System.out.println("Started: " + eventSource);
    }

    @Override
    public void appFlowItemPaused(AppFlowItem eventSource) {
        System.out.println("Paused: " + eventSource);
    }

    @Override
    public void appFlowItemResumed(AppFlowItem eventSource) {
        System.out.println("Resumed: " + eventSource);
    }

    @Override
    public void appFlowItemStopped(AppFlowItem eventSource) {
        System.out.println("Stopped: " + eventSource);
    }
}
