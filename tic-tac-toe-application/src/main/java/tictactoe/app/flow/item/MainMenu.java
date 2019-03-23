package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import genericapp.AppFlowItemEvent;

public class MainMenu extends AbstractAppFlowItem {

    public static final AppFlowItemEvent GAME_SETTINGS = () -> false;
    public static final AppFlowItemEvent START_GAME = () -> true;

    @Override
    public void run() {
        super.run();
    }
}
