package genericapp;

public interface AppStateEventHandler {

    void handleAppFlowItemEvent(AppState eventSource, AppStateEvent event);
}
