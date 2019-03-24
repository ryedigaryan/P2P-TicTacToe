package genericapp;

public abstract class AbstractAppFlowItemStateChangeListener implements AppFlowItemStateChangeListener {
    @Override
    public void appFlowItemStarted(AppState eventSource) {
        System.out.println("Started: " + eventSource);
    }

    @Override
    public void appFlowItemPaused(AppState eventSource) {
        System.out.println("Paused: " + eventSource);
    }

    @Override
    public void appFlowItemResumed(AppState eventSource) {
        System.out.println("Resumed: " + eventSource);
    }

    @Override
    public void appFlowItemStopped(AppState eventSource) {
        System.out.println("Stopped: " + eventSource);
    }
}
