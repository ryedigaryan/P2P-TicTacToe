package genericapp;

public interface AppFlowItemEventHandler {

    void handleAppFlowItemEvent(AppState eventSource, AppFlowItemEvent event);
}
