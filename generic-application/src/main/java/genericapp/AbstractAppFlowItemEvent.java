package genericapp;

public class AbstractAppFlowItemEvent implements AppFlowItemEvent {
    private boolean shouldStopPreviousAppFlowItem;

    public AbstractAppFlowItemEvent(boolean shouldStopPreviousAppFlowItem) {
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
