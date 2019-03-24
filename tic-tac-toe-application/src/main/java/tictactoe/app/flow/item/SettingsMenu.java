package tictactoe.app.flow.item;

import genericapp.AppStateEvent;
import lombok.Getter;
import tictactoe.app.flow.item.common.Settings;
import tictactoe.connector.event.ui.base.ISettingsMenuUI;
import tictactoe.connector.event.ui.listener.SettingsMenuListener;

@Getter
public class SettingsMenu extends AbstractTicTacToeAppState<ISettingsMenuUI> implements SettingsMenuListener {

    public static final AppStateEvent BACK_TO_MAIN_MENU = () -> true;

    private Settings gameSettings = new Settings();

    public SettingsMenu(Integer id, ISettingsMenuUI settingsMenuUI) {
        super(id, settingsMenuUI);
        getUi().setListener(this);
    }

    @Override
    public void setRowCount(int rowCount) {
        gameSettings.setRowCount(rowCount);
    }

    @Override
    public void setColumnCount(int columnCount) {
        gameSettings.setColumnCount(columnCount);
    }

    @Override
    public void setWinLength(int winLength) {
        gameSettings.setWinLength(winLength);
    }

    @Override
    public void setPlayersCount(int playersCount) {
        gameSettings.setPlayersCount(playersCount);
    }

    @Override
    public void close(boolean saveChanges) {
        if(!saveChanges)
            gameSettings = null;
        this.getAppStateEventHandler().handleAppFlowItemEvent(this, BACK_TO_MAIN_MENU);
    }
}
