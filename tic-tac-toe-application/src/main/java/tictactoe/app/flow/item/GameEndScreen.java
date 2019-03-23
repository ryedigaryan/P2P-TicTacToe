package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import genericapp.AppFlowItemEvent;

public abstract class GameEndScreen extends AbstractAppFlowItem {

    public static final AppFlowItemEvent CLOSE = () -> true;

    protected GameEndScreen(Integer id) {
        super(id);
    }
}
