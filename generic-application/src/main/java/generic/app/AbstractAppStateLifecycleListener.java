package generic.app;

public abstract class AbstractAppStateLifecycleListener implements AppStateLifecycleListener {
    @Override
    public void appStateStarted(AppState eventSource) {
        System.out.println("Started: " + eventSource);
    }

    @Override
    public void appStatePaused(AppState eventSource) {
        System.out.println("Paused: " + eventSource);
    }

    @Override
    public void appStateResumed(AppState eventSource) {
        System.out.println("Resumed: " + eventSource);
    }

    @Override
    public void appStateStopped(AppState eventSource) {
        System.out.println("Stopped: " + eventSource);
    }
}
