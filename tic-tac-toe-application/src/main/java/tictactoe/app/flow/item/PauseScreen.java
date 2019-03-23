package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import genericapp.AppFlowItemEvent;

public class PauseScreen extends AbstractAppFlowItem {

    public static final AppFlowItemEvent CLOSE = () -> true;
    public static final AppFlowItemEvent LEAVE_GAME = () -> true;

    public PauseScreen(Integer id) {
        super(id);
    }
}
