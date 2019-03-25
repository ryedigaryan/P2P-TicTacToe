package tictactoe.app.state;

import genericapp.AppStateEvent;
import lombok.Getter;
import tictactoe.app.state.common.Settings;
import tictactoe.connector.event.ui.base.ISettingsMenuStateUI;
import tictactoe.connector.event.ui.listener.SettingsMenuUIListener;

@Getter
public class SettingsMenuState extends AbstractTicTacToeAppState<ISettingsMenuStateUI> implements SettingsMenuUIListener {

    public static final AppStateEvent BACK_TO_MAIN_MENU = () -> true;

    private Settings gameSettings = new Settings();

    public SettingsMenuState(Integer id, ISettingsMenuStateUI settingsMenuUI) {
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
        this.getAppStateEventHandler().handleAppStateEvent(this, BACK_TO_MAIN_MENU);
    }
}
