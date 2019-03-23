package genericapp;

public interface AppFlowItemEvent {
    /**
     * A shared instance of AppFlowItemEvent which {@link AppFlowItemEvent#shouldStopPreviousAppFlowItem()} returns {@code true};
     */
    AppFlowItemEvent STOP_PREVIOUS_APP_FLOW_ITEM = () -> true;

    /**
     * A shared instance of AppFlowItemEvent which {@link AppFlowItemEvent#shouldStopPreviousAppFlowItem()} returns {@code false};
     */
    AppFlowItemEvent DO_NOT_STOP_PREVIOUS_APP_FLOW_ITEM = () -> false;

    boolean shouldStopPreviousAppFlowItem();
}
