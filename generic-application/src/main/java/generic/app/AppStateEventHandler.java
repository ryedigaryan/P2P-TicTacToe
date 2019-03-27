package generic.app;

public interface AppStateEventHandler {

    void handleAppStateEvent(AppState eventSource, AppStateEvent event);
}
