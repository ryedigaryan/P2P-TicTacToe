package genericapp;

// It'd be better to have methods like
// will enter state xxx
// is in state xxx
// leaves state xxx
// where xxx is state name (Paused, Started ...)
// but I hope that this 4 is even more than needed :D
public interface AppStateLifecycleListener {

    void appStateStarted(AppState eventSource);

    void appStatePaused(AppState eventSource);

    void appStateResumed(AppState eventSource);

    void appStateStopped(AppState eventSource);
}
