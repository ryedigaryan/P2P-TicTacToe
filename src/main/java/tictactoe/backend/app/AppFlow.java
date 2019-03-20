package tictactoe.backend.app;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class AppFlow implements AppFlowItemEventHandler<AppFlowItemEvent> {
    private AppFlowItem currentItem;
    private Map<AppFlowItem, Map<Object, AppFlowItem>> flowMap;

    public AppFlow(AppFlowItem initialItem) {
        this.currentItem = initialItem;
        initialItem.setEventHandler(this);
    }

    public <E extends AppFlowItemEvent> void registerAppFlowEventChangeRule(AppFlowItem oldFlow, E event, AppFlowItem newFlow) {
        Map<Object, AppFlowItem> flowItemConfig = flowMap.getOrDefault(oldFlow, new HashMap<>());
        flowItemConfig.put(event, newFlow);
    }

    @Override
    public void handleAppFlowEvent(AppFlowItem eventSource, AppFlowItemEvent event) {
        if(event.isStopPreviousAppFlowItem())
            currentItem.stop();
        else
            currentItem.pause();

        currentItem = flowMap.get(eventSource).get(event);

        if(currentItem.isStarted()) {
            assert currentItem.isPaused() : "New AppFlowItem should be in paused state when old one is running";
            currentItem.resume();
        }
        else {
            assert !currentItem.isPaused() : "AppFlowItem should NOT be in paused state if it is not started yet";
            currentItem.run();
        }
    }
}