package tictactoe.app.state;

import generic.app.AppStateEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.common.data.Settings;
import tictactoe.connector.ui.base.SettingsMenuStateUI;
import tictactoe.connector.ui.listener.SettingsMenuUIListener;

@Getter
public class SettingsMenuState extends AbstractTicTacToeAppStateWithoutNotification<SettingsMenuStateUI> implements SettingsMenuUIListener {

    public static final AppStateEvent BACK_TO_MAIN_MENU = () -> false;

    @Setter(AccessLevel.PUBLIC)
    private Settings gameSettings;
    private final Settings initialSettings;

    public SettingsMenuState(Integer id, SettingsMenuStateUI settingsMenuUI, Settings initialSettings) {
        super(id, settingsMenuUI);
        this.initialSettings = initialSettings;
        setGameSettings(initialSettings);
        getUi().setListener(this);
        getUi().setSettings(getGameSettings());
    }

    ///////////////////////////////////////////////////////////////////////////
    // SettingsMenuUIListener interface methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void close(boolean saveChanges) {
        getAppStateEventHandler().handleAppStateEvent(this, BACK_TO_MAIN_MENU);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AbstractTicTacToeAppStateWithoutNotification abstract superclass methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void start() {
        super.start();
        getAppStateLifecycleListener().appStateStarted(this);
    }

    @Override
    public void pause() {
        super.pause();
        getAppStateLifecycleListener().appStatePaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        getUi().setSettings(getGameSettings());
        getAppStateLifecycleListener().appStateResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        getAppStateLifecycleListener().appStateStopped(this);
    }
}
