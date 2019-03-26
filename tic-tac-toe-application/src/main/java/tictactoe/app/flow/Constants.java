package tictactoe.app.flow;

import genericapp.AbstractAppStateLifecycleListener;
import genericapp.AppState;
import genericapp.AppStateLifecycleListener;
import tictactoe.connector.common.data.Settings;

public interface Constants {
    Integer ID_MAIN_MENU = 0;
    Integer ID_SETTINGS_MENU = 1;
    Integer ID_GAMING_SCENE = 2;
    Integer ID_PAUSE_SCREEN = 3;
    Integer ID_GAME_WON = 4;
    Integer ID_GAME_LOST = 5;
    Integer ID_GAME_DRAWN = 6;

    AppStateLifecycleListener EMPTY_STATE_CHANGE_LISTENER = new AbstractAppStateLifecycleListener() {
        public void appStatePaused(AppState eventSource)  {}
        public void appStateStarted(AppState eventSource) {}
        public void appStateResumed(AppState eventSource) {}
        public void appStateStopped(AppState eventSource) {}
    };

    static Settings defaultGameSettings() {
        return new Settings(3, 3, 3, 2);
    }
}
