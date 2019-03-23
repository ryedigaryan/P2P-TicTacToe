package genericapp;

// It'd be better to have methods like
// will enter state xxx
// is in state xxx
// leaves state xxx
// where xxx is state name (Paused, Started ...)
// but I hope that this 4 is even more than needed :D
public interface AppFlowItemStateChangeListener {

    void appFlowItemPaused();

    void appFlowItemStarted();

    void appFlowItemResumed();

    void appFlowItemStopped();
}
