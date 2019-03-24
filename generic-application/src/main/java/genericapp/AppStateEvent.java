package genericapp;

public interface AppStateEvent {
    /**
     * A shared instance of AppStateEvent which {@link AppStateEvent#shouldStopPreviousAppFlowItem()} returns {@code true};
     */
    AppStateEvent STOP_PREVIOUS_APP_FLOW_ITEM = () -> true;

    /**
     * A shared instance of AppStateEvent which {@link AppStateEvent#shouldStopPreviousAppFlowItem()} returns {@code false};
     */
    AppStateEvent DO_NOT_STOP_PREVIOUS_APP_FLOW_ITEM = () -> false;

    boolean shouldStopPreviousAppFlowItem();
}
