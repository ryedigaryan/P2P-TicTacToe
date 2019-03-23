package genericapp;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ToString
public class AppFlow implements AppFlowItemEventHandler<AppFlowItemEvent> {
    private AppFlowItem currentAppFlowItem;
    private Map<Integer, Map<AppFlowItemEvent, Supplier<AppFlowItem>>> flowMap = new HashMap<>();

    public AppFlow() {
    }

    public AppFlow(AppFlowItem initialAppFlowItem) {
        setInitialAppFlowItem(initialAppFlowItem);
    }

    public void setInitialAppFlowItem(AppFlowItem initialAppFlowItem) {
        initialAppFlowItem.setAppFlowItemEventHandler(this);
        this.currentAppFlowItem = initialAppFlowItem;
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
    public <E extends AppFlowItemEvent> void registerAppFlowItemChangeRule(Integer oldFlowItemId, E event, Supplier<AppFlowItem> newFlowItem) {
        Map<AppFlowItemEvent, Supplier<AppFlowItem>> flowItemConfig = flowMap.computeIfAbsent(oldFlowItemId, f -> new HashMap<>());
        flowItemConfig.put(event, newFlowItem);
    }

    @Override
    public void handleAppFlowEvent(AppFlowItem eventSource, AppFlowItemEvent event) {
        assert currentAppFlowItem != null : "Current AppFlowItem could not be null when handling event";
        if(event.shouldStopPreviousAppFlowItem())
            currentAppFlowItem.stop();
        else
            currentAppFlowItem.pause();

        currentAppFlowItem = flowMap
                .get(eventSource.getId()) // Map<Integer, Map<AppFlowItemEvent, Supplier<AppFlowItem>>>::get
                .get(event) // Map<AppFlowItemEvent, Supplier<AppFlowItem>>::get
                .get();     // Supplier<AppFlowItem>::get

        if(currentAppFlowItem.getAppFlowItemEventHandler() == null) {
            currentAppFlowItem.setAppFlowItemEventHandler(this);
        }

        // if any application flow event handler will be someone else, the flow might broke.
        assert currentAppFlowItem.getAppFlowItemEventHandler() == this : "Event handler of all AppFlowItems should be AppFlow.";

        if(currentAppFlowItem.isStarted()) {
            currentAppFlowItem.resume();
        }
        else {
            currentAppFlowItem.start();
        }
    }

    protected <T extends AppFlowItem> T getCurrentAppFlowItem() {
        return (T)currentAppFlowItem;
    }
}