package genericapp;

public interface AppStateEventHandler {

    void handleAppStateEvent(AppState eventSource, AppStateEvent event);
}
