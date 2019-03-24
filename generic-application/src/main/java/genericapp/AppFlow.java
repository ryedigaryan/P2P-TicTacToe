package genericapp;

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
        setInitialAppState(initialAppState);
    }

    public void setInitialAppState(AppState initialAppState) {
        initialAppState.setAppStateEventHandler(this);
        this.currentAppState = initialAppState;
    }

    /**
     * Register a new rule.
     * The method accepts 3 arguments: old flow item, event and new flow item. Bew rule is defined
     * like "if particular event arrives wile the old flow item is running, then switch to new flow item.
     * Switch means pause old flow item and start the new one.
     * If the {@link AppStateEvent#shouldStopPreviousAppState()} returns true, then old flow item is stopped, instead of pausing.
     * If the new flow item is not started yet, then it is started.
     * @param oldFlowItemId id of old flow item, which will be stopped or paused, when particular {@code event} arrives
     * @param event event, which causes flow item change
     * @param newFlowItem new flow item, which will be resumed or run when particular {@code event} arrives
     * @param <E> type of event
     */
    public <E extends AppStateEvent> void registerAppStateChangeRule(Integer oldFlowItemId, E event, Supplier<AppState> newFlowItem) {
        Map<AppStateEvent, Supplier<AppState>> flowItemConfig = flowMap.computeIfAbsent(oldFlowItemId, f -> new HashMap<>());
        flowItemConfig.put(event, newFlowItem);
    }

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

        if(currentAppState.getAppStateEventHandler() == null) {
            currentAppState.setAppStateEventHandler(this);
        }

        // if any application flow event handler will be someone else, the flow might broke.
        assert currentAppState.getAppStateEventHandler() == this : "Event handler of all AppStates should be AppFlow.";

        if(currentAppState.isStarted()) {
            currentAppState.resume();
        }
        else {
            currentAppState.start();
        }
    }

    protected <T extends AppState> T getCurrentAppState() {
        return (T) currentAppState;
    }

    @Override
    public void run() {
        currentAppState.start();
    }
}
