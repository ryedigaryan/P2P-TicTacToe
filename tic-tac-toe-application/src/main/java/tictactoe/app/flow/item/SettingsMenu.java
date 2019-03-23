package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import genericapp.AppFlowItem;
import genericapp.AppFlowItemEvent;

public class SettingsMenu extends AbstractAppFlowItem {

    public static final AppFlowItemEvent BACK_TO_MAIN_MENU = () -> true;

    public SettingsMenu(Integer id) {
        super(id);
    }
}
