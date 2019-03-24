package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import genericapp.AppFlowItemEvent;

public class GamingScene extends AbstractAppFlowItem {

    public static final AppFlowItemEvent PAUSE = () -> false;
    public static final AppFlowItemEvent GAME_WON = () -> true;
    public static final AppFlowItemEvent GAME_LOST = () -> true;

    public GamingScene(Integer id) {
        super(id);
    }
}
