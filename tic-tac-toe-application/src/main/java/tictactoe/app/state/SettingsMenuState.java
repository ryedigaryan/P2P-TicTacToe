package tictactoe.app.state;

import genericapp.AppStateEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.common.data.Settings;
import tictactoe.connector.ui.base.ISettingsMenuStateUI;
import tictactoe.connector.ui.listener.SettingsMenuUIListener;

@Getter
@Setter(AccessLevel.PROTECTED)
public class SettingsMenuState extends AbstractTicTacToeAppState<ISettingsMenuStateUI> implements SettingsMenuUIListener {

    public static final AppStateEvent BACK_TO_MAIN_MENU = () -> true;

    private Settings gameSettings;
    private final Settings initialSettings;

    public SettingsMenuState(Integer id, ISettingsMenuStateUI settingsMenuUI, Settings initialSettings) {
        super(id, settingsMenuUI);
        this.initialSettings = initialSettings;
        setGameSettings(initialSettings);
        getUi().setListener(this);
        getUi().setSettings(initialSettings);
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
            gameSettings = initialSettings;
        this.getAppStateEventHandler().handleAppStateEvent(this, BACK_TO_MAIN_MENU);
    }
}
