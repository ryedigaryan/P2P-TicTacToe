package generic.app;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ToString
public class AppFlow implements AppStateEventHandler, Runnable {
    private AppState currentAppState;
    private Map<Integer, Map<AppStateEvent, Supplier<AppState>>> flowMap = new HashMap<>();

    public AppFlow() {
    }

    public AppFlow(AppState initialAppState) {
        setCurrentAppState(initialAppState);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter/Setter which require custom logic
    ///////////////////////////////////////////////////////////////////////////

    protected void setCurrentAppState(AppState appState) {
        appState.setAppStateEventHandler(this);
        currentAppState = appState;
    }

    protected <T extends AppState> T getCurrentAppState() {
        return (T) currentAppState;
    }

    /**
     * Register a new rule.
     * The method accepts 3 arguments: old appState, event and new appState. Bew rule is defined
     * like "if particular event arrives wile the old appState is running, then switch to new appState.
     * Switch means pause old appState and start the new one.
     * If the {@link AppStateEvent#shouldStopPreviousAppState()} returns true, then old appState is stopped, instead of pausing.
     * If the new appState is not started yet, then it is started.
     * @param oldAppStateId id of old appState, which will be stopped or paused, when particular {@code event} arrives
     * @param event event, which causes appState change
     * @param newAppState new appState, which will be resumed or run when particular {@code event} arrives
     * @param <E> type of event
     */
    public <E extends AppStateEvent> void registerAppStateChangeRule(Integer oldAppStateId, E event, Supplier<AppState> newAppState) {
        Map<AppStateEvent, Supplier<AppState>> appStateConfig = flowMap.computeIfAbsent(oldAppStateId, f -> new HashMap<>());
        appStateConfig.put(event, newAppState);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods overridden from interfaces
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void handleAppStateEvent(AppState eventSource, AppStateEvent event) {
        assert currentAppState != null : "Current AppState could not be null when handling event";

        if(event.shouldStopPreviousAppState())
            currentAppState.stop();
        else
            currentAppState.pause();

        currentAppState = flowMap
                .get(eventSource.getId()) // Map<Integer, Map<AppStateEvent, Supplier<AppState>>>::get
                .get(event) // Map<AppStateEvent, Supplier<AppState>>::get
                .get();     // Supplier<AppState>::get

        if(currentAppState.getAppStateEventHandler() == null)
            currentAppState.setAppStateEventHandler(this);

        // if any application flow event handler will be someone else, the flow might broke.
        assert currentAppState.getAppStateEventHandler() == this : "Event handler of all AppStates should be AppFlow.";

        if(currentAppState.isStarted())
            currentAppState.resume();
        else
            currentAppState.start();
    }

    @Override
    public void run() {
        currentAppState.start();
    }
}
