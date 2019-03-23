package genericapp;

public interface AppFlowItemEventHandler<E extends AppFlowItemEvent> {

    void handleAppFlowItemEvent(AppFlowItem eventSource, E event);
}
