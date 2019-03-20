package genericapp;

public interface AppFlowItemEventHandler<E extends AppFlowItemEvent> {

    void handleAppFlowEvent(AppFlowItem eventSource, E event);
}
