package genericapp;

public class AbstractAppStateEvent implements AppStateEvent {
    private boolean shouldStopPreviousAppState;

    public AbstractAppStateEvent(boolean shouldStopPreviousAppState) {
        this.shouldStopPreviousAppState = shouldStopPreviousAppState;
    }

    @Override
    public boolean shouldStopPreviousAppState() {
        return shouldStopPreviousAppState;
    }

    public void setShouldStopPreviousAppState(boolean value) {
        shouldStopPreviousAppState = value;
    }
}