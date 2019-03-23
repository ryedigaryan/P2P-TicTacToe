package genericapp;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class AppFlow implements AppFlowItemEventHandler<AppFlowItemEvent> {
    private AppFlowItem currentItem;
    private Map<AppFlowItem, Map<AppFlowItemEvent, AppFlowItem>> flowMap = new HashMap<>();

    public AppFlow(AppFlowItem initialItem) {
        initialItem.setEventHandler(this);
        this.currentItem = initialItem;
    }

    /**
     * Register a new rule.
     * The method accepts 3 arguments: old flow item, event and new flow item. Bew rule is defined
     * like "if particular event arrives wile the old flow item is running, then switch to new flow item.
     * Switch means pause old flow item and start the new one.
     * If the {@link AppFlowItemEvent#shouldStopPreviousAppFlowItem()} returns true, then old flow item is stopped, instead of pausing.
     * If the new flow item is not started yet, then it is started.
     * @param oldFlowItem old flow item, which will be stopped or paused, when particular {@code event} arrives
     * @param event event, which causes flow item change
     * @param newFlowItem new flow item, which will be resumed or run when particular {@code event} arrives
     * @param <E> type of event
     */
    public <E extends AppFlowItemEvent> void registerAppFlowEventChangeRule(AppFlowItem oldFlowItem, E event, AppFlowItem newFlowItem) {
        Map<AppFlowItemEvent, AppFlowItem> flowItemConfig = flowMap.computeIfAbsent(oldFlowItem, f -> new HashMap<>());
        flowItemConfig.put(event, newFlowItem);
    }

    @Override
    public void handleAppFlowEvent(AppFlowItem eventSource, AppFlowItemEvent event) {
        if(event.shouldStopPreviousAppFlowItem())
            currentItem.stop();
        else
            currentItem.pause();

        currentItem = flowMap.get(eventSource).get(event);

        if(currentItem.isStarted()) {
            currentItem.resume();
        }
        else {
            currentItem.start();
        }
    }
}