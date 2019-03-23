package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import genericapp.AppFlowItemEvent;

public class GameEndScreen extends AbstractAppFlowItem {
    public static final AppFlowItemEvent CLOSE = () -> true;

    public GameEndScreen(Integer id) {
        super(id);
    }
}
