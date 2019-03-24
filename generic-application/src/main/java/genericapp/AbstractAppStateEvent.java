package genericapp;

public class AbstractAppStateEvent implements AppStateEvent {
    private boolean shouldStopPreviousAppFlowItem;

    public AbstractAppStateEvent(boolean shouldStopPreviousAppFlowItem) {
        this.shouldStopPreviousAppFlowItem = shouldStopPreviousAppFlowItem;
    }

    @Override
    public boolean shouldStopPreviousAppFlowItem() {
        return shouldStopPreviousAppFlowItem;
    }

    public void setShouldStopPreviousAppFlowItem(boolean value) {
        shouldStopPreviousAppFlowItem = value;
    }
}
