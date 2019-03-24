package genericapp;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ToString
public class AppFlow implements AppFlowItemEventHandler, Runnable {
    private AppState currentAppState;
    private Map<Integer, Map<AppFlowItemEvent, Supplier<AppState>>> flowMap = new HashMap<>();

    public AppFlow() {
    }

    public AppFlow(AppState initialAppState) {
        setInitialAppFlowItem(initialAppState);
    }

    public void setInitialAppFlowItem(AppState initialAppState) {
        initialAppState.setAppFlowItemEventHandler(this);
        this.currentAppState = initialAppState;
    }

    /**
     * Register a new rule.
     * The method accepts 3 arguments: old flow item, event and new flow item. Bew rule is defined
     * like "if particular event arrives wile the old flow item is running, then switch to new flow item.
     * Switch means pause old flow item and start the new one.
     * If the {@link AppFlowItemEvent#shouldStopPreviousAppFlowItem()} returns true, then old flow item is stopped, instead of pausing.
     * If the new flow item is not started yet, then it is started.
     * @param oldFlowItemId id of old flow item, which will be stopped or paused, when particular {@code event} arrives
     * @param event event, which causes flow item change
     * @param newFlowItem new flow item, which will be resumed or run when particular {@code event} arrives
     * @param <E> type of event
     */
    public <E extends AppFlowItemEvent> void registerAppFlowItemChangeRule(Integer oldFlowItemId, E event, Supplier<AppState> newFlowItem) {
        Map<AppFlowItemEvent, Supplier<AppState>> flowItemConfig = flowMap.computeIfAbsent(oldFlowItemId, f -> new HashMap<>());
        flowItemConfig.put(event, newFlowItem);
    }

    @Override
    public void handleAppFlowItemEvent(AppState eventSource, AppFlowItemEvent event) {
        assert currentAppState != null : "Current AppState could not be null when handling event";
        if(event.shouldStopPreviousAppFlowItem())
            currentAppState.stop();
        else
            currentAppState.pause();

        currentAppState = flowMap
                .get(eventSource.getId()) // Map<Integer, Map<AppFlowItemEvent, Supplier<AppState>>>::get
                .get(event) // Map<AppFlowItemEvent, Supplier<AppState>>::get
                .get();     // Supplier<AppState>::get

        if(currentAppState.getAppFlowItemEventHandler() == null) {
            currentAppState.setAppFlowItemEventHandler(this);
        }

        // if any application flow event handler will be someone else, the flow might broke.
        assert currentAppState.getAppFlowItemEventHandler() == this : "Event handler of all AppFlowItems should be AppFlow.";

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
