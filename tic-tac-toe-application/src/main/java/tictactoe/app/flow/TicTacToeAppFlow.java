package tictactoe.app.flow;

import genericapp.AbstractAppStateLifecycleListener;
import genericapp.AppFlow;
import genericapp.AppState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.app.state.GameDrawnState;
import tictactoe.app.state.GameLostState;
import tictactoe.app.state.GameWonState;
import tictactoe.app.state.GamingState;
import tictactoe.app.state.MainMenuState;
import tictactoe.app.state.PausedState;
import tictactoe.app.state.SettingsMenuState;
import tictactoe.connector.common.data.Settings;
import tictactoe.backend.logic.GameConfig;
import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.manager.LocalGameManager;
import tictactoe.backend.model.Board;
import tictactoe.connector.ui.GameStateDescriptor;
import tictactoe.ui.state.GameBoardUI;
import tictactoe.ui.state.GameDrawnUI;
import tictactoe.ui.state.GameLostUI;
import tictactoe.ui.state.GameWonUI;
import tictactoe.ui.state.MainMenuUI;
import tictactoe.ui.state.PausedPopUp;
import tictactoe.ui.state.SettingsMenuUI;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class TicTacToeAppFlow extends AppFlow {

    public TicTacToeAppFlow() {
        setCurrentAppState(getMainMenuState());
        // handle Main Menu events
        registerAppStateChangeRule(Constants.ID_MAIN_MENU, MainMenuState.GAME_SETTINGS, this::getSettingsMenuState);
        registerAppStateChangeRule(Constants.ID_MAIN_MENU, MainMenuState.START_GAME, this::getGamingState);
        // handle Settings Menu events
        registerAppStateChangeRule(Constants.ID_SETTINGS_MENU, SettingsMenuState.BACK_TO_MAIN_MENU, this::getMainMenuState);
        // handle Gaming Scene events
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingState.PAUSE, this::getPausedState);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingState.GAME_WON, this::getGameWonScreen);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingState.GAME_LOST, this::getGameLostScreen);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingState.GAME_DRAWN, this::getGameDrawnScreen);
        // handle Pause Screen events
        registerAppStateChangeRule(Constants.ID_PAUSE_SCREEN, PausedState.CLOSE, this::getGamingState);
        // TODO: 3/23/2019 Think wisely
        registerAppStateChangeRule(Constants.ID_PAUSE_SCREEN, PausedState.LEAVE_GAME, this::getMainMenuState);
        // handle End of Game Screen events
        registerAppStateChangeRule(Constants.ID_GAME_WON, GameWonState.CLOSE, this::getMainMenuState);
        registerAppStateChangeRule(Constants.ID_GAME_LOST, GameLostState.CLOSE, this::getMainMenuState);
        registerAppStateChangeRule(Constants.ID_GAME_DRAWN, GameLostState.CLOSE, this::getMainMenuState);
    }

    ///////////////////////////////////////////////////////////////////////////
    // MainMenuState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private MainMenuState mainMenuState;
    private MainMenuState getMainMenuState() {
        if(mainMenuState == null) {
            mainMenuState = new MainMenuState(Constants.ID_MAIN_MENU, new MainMenuUI());
            mainMenuState.setAppStateLifecycleListener(new MainMenuStateLifecycleListener());
        }
        cleanUpAppStates();
        return mainMenuState;
    }

    private class MainMenuStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // SettingsMenuState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private Settings gameSettings = Constants.defaultGameSettings();
    private SettingsMenuState settingsMenuState;
    private SettingsMenuState getSettingsMenuState() {
        if(settingsMenuState == null) {
            settingsMenuState = new SettingsMenuState(Constants.ID_SETTINGS_MENU, new SettingsMenuUI(), gameSettings);
            settingsMenuState.setAppStateLifecycleListener(new SettingsMenuStateLifecycleListener());
        }
        return settingsMenuState;
    }

    private class SettingsMenuStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
            saveGameSettings();
        }

        private void saveGameSettings() {
            assert gamingState == null : "GameScene should be null when retrieving game settings from SettingsMenuState";
            setGameSettings(getSettingsMenuState().getGameSettings());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GamingState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GamingState<GameBoardUI> gamingState;
    private GamingState<GameBoardUI> getGamingState() {
        if(gamingState == null) {
            GameConfig config = new GameConfig(gameSettings.getWinLength(), gameSettings.getPlayersCount());
            Board board = new Board(gameSettings.getRowCount(), gameSettings.getColumnCount());
            GameEngine engine = new GameEngine(config, board);
            LocalGameManager<GameBoardUI> ticTacToeGameManager = new LocalGameManager<>(engine, new GameBoardUI(gameSettings.getRowCount(), gameSettings.getColumnCount()));

            gamingState = new GamingState<>(Constants.ID_GAMING_SCENE, ticTacToeGameManager);
            gamingState.setAppStateLifecycleListener(new GamingSceneStateLifecycleListener());
        }
        return gamingState;
    }

    private class GamingSceneStateLifecycleListener extends AbstractAppStateLifecycleListener {

        @Override
        public void appStateStarted(AppState eventSource) {
            super.appStateStarted(eventSource);
            mainMenuState = null;
            settingsMenuState = null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PausedState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private PausedState pausedState;
    private PausedState getPausedState() {
        if(pausedState == null) {
            GameStateDescriptor gsd = () -> "It is " + gamingState.getGameManager().getPlayerName() + " turn";
            pausedState = new PausedState(Constants.ID_PAUSE_SCREEN, new PausedPopUp(gamingState.getUi(), gsd));
            pausedState.setAppStateLifecycleListener(new PauseScreenStateLifecycleListener());
        }
        return pausedState;
    }

    private class PauseScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
            pausedState = null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameWonState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GameWonState gameWonScreen;
    private GameWonState getGameWonScreen() {
        if(gameWonScreen == null) {
            gameWonScreen = new GameWonState(
                    Constants.ID_GAME_WON,
                    new GameWonUI(gamingState.getUi(), gamingState.getGameManager().getWinnerName())
            );
            gameWonScreen.setAppStateLifecycleListener(new GameWonScreenStateLifecycleListener());
        }
        return gameWonScreen;
    }

    private class GameWonScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameLostState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GameLostState gameLostScreen;
    private GameLostState getGameLostScreen() {
        if(gameLostScreen == null) {
            gameLostScreen = new GameLostState(
                    Constants.ID_GAME_LOST,
                    new GameLostUI(gamingState.getUi(), gamingState.getGameManager().getWinnerName())
            );
            gameLostScreen.setAppStateLifecycleListener(new GameLostScreenStateLifecycleListener());
        }
        return gameLostScreen;
    }


    private class GameLostScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameLostState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GameDrawnState gameDrawnScreen;
    private GameDrawnState getGameDrawnScreen() {
        if(gameDrawnScreen == null) {
            gameDrawnScreen = new GameDrawnState(
                    Constants.ID_GAME_DRAWN,
                    new GameDrawnUI(gamingState.getUi())
            );
            gameDrawnScreen.setAppStateLifecycleListener(new GameDrawnScreenStateLifecycleListener());
        }
        return gameDrawnScreen;
    }


    private class GameDrawnScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // helpers
    ///////////////////////////////////////////////////////////////////////////

    private void cleanUpAppStates() {
        settingsMenuState = null;
        gamingState = null;
        pausedState = null;
        gameWonScreen = null;
        gameLostScreen = null;
        gameDrawnScreen = null;
    }
}
