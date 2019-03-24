package tictactoe.app.flow;

import genericapp.AbstractAppStateLifecycleListener;
import genericapp.AppFlow;
import genericapp.AppState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.app.state.GameDrawnScreen;
import tictactoe.app.state.GamingScene;
import tictactoe.app.state.GameLostScreen;
import tictactoe.app.state.MainMenu;
import tictactoe.app.state.PauseScreen;
import tictactoe.app.state.SettingsMenu;
import tictactoe.app.state.GameWonScreen;
import tictactoe.app.state.common.Settings;
import tictactoe.backend.logic.GameConfig;
import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.manager.LocalGameManager;
import tictactoe.backend.model.Board;
import tictactoe.connector.event.ui.GameStateDescriptor;
import tictactoe.ui.game.GameBoardUI;
import tictactoe.ui.state.GameResultUI;
import tictactoe.ui.state.MainMenuUI;
import tictactoe.ui.state.PausedPopUp;
import tictactoe.ui.state.SettingsMenuUI;

import java.awt.*;
import java.util.Arrays;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class TicTacToeAppFlow extends AppFlow {

    public TicTacToeAppFlow() {
        setInitialAppState(getMainMenu());
        // handle Main Menu events
        registerAppStateChangeRule(Constants.ID_MAIN_MENU, MainMenu.GAME_SETTINGS, this::getSettingsMenu);
        registerAppStateChangeRule(Constants.ID_MAIN_MENU, MainMenu.START_GAME, this::getGamingScene);
        // handle Settings Menu events
        registerAppStateChangeRule(Constants.ID_SETTINGS_MENU, SettingsMenu.BACK_TO_MAIN_MENU, this::getMainMenu);
        // handle Gaming Scene events
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingScene.PAUSE, this::getPauseScreen);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingScene.GAME_WON, this::getGameWonScreen);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingScene.GAME_LOST, this::getGameLostScreen);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, GamingScene.GAME_DRAWN, this::getGameDrawnScreen);
        // handle Pause Screen events
        registerAppStateChangeRule(Constants.ID_PAUSE_SCREEN, PauseScreen.CLOSE, this::getGamingScene);
        // TODO: 3/23/2019 Think wisely
        registerAppStateChangeRule(Constants.ID_PAUSE_SCREEN, PauseScreen.LEAVE_GAME, this::getMainMenu);
        // handle End of Game Screen events
        registerAppStateChangeRule(Constants.ID_GAME_WON, GameWonScreen.CLOSE, this::getMainMenu);
        registerAppStateChangeRule(Constants.ID_GAME_LOST, GameLostScreen.CLOSE, this::getMainMenu);
        registerAppStateChangeRule(Constants.ID_GAME_DRAWN, GameLostScreen.CLOSE, this::getMainMenu);
    }

    ///////////////////////////////////////////////////////////////////////////
    // MainMenu related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private MainMenu mainMenu;
    private MainMenu getMainMenu() {
        if(mainMenu == null) {
            mainMenu = new MainMenu(Constants.ID_MAIN_MENU, new MainMenuUI());
            mainMenu.setAppStateLifecycleListener(new MainMenuStateLifecycleListener());
        }
        return mainMenu;
    }

    private class MainMenuStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // SettingsMenu related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private Settings gameSettings;
    private SettingsMenu settingsMenu;
    private SettingsMenu getSettingsMenu() {
        if(settingsMenu == null) {
            settingsMenu = new SettingsMenu(Constants.ID_SETTINGS_MENU, new SettingsMenuUI());
            settingsMenu.setAppStateLifecycleListener(new SettingsMenuStateLifecycleListener());
        }
        return settingsMenu;
    }

    private class SettingsMenuStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
            assert gamingScene == null : "GameScene should be null when retrieving game settings from SettingsMenu";
            gameSettings = settingsMenu.getGameSettings();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GamingScene related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GamingScene gamingScene;
    private GamingScene getGamingScene() {
        if(gamingScene == null) {
            if(gameSettings == null) {
                System.out.println("Game Settings is null, so running game with default settings");
                gameSettings = defaultSettings();
            }

            GameConfig config = new GameConfig(gameSettings.getWinLength(), gameSettings.getPlayersCount());
            Board board = new Board(gameSettings.getRowCount(), gameSettings.getColumnCount());
            GameEngine engine = new GameEngine(config, board);
            LocalGameManager ticTacToeGameManager = new LocalGameManager(engine, new GameBoardUI(gameSettings.getRowCount(), gameSettings.getColumnCount()));

            gamingScene = new GamingScene(Constants.ID_GAMING_SCENE, ticTacToeGameManager);
            gamingScene.setAppStateLifecycleListener(new GamingSceneStateLifecycleListener());
        }
        return gamingScene;
    }

    private class GamingSceneStateLifecycleListener extends AbstractAppStateLifecycleListener {

        @Override
        public void appStateStarted(AppState eventSource) {
            super.appStateStarted(eventSource);
            mainMenu = null;
            settingsMenu = null;
            gameSettings = null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PauseScreen related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private PauseScreen pauseScreen;
    private PauseScreen getPauseScreen() {
        if(pauseScreen == null) {
            GameStateDescriptor gsd =
                    () -> "Player Names: " +
                            Arrays.toString(gamingScene.getLocalTicTacToeGameManager().getPlayerNames());
            pauseScreen = new PauseScreen(Constants.ID_PAUSE_SCREEN, new PausedPopUp((Frame)gamingScene.getUi(), gsd));
            pauseScreen.setAppStateLifecycleListener(new PauseScreenStateLifecycleListener());
        }
        return pauseScreen;
    }

    private class PauseScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameWonScreen related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GameWonScreen gameWonScreen;
    private GameWonScreen getGameWonScreen() {
        if(gameWonScreen == null) {
            gameWonScreen = new GameWonScreen(
                    Constants.ID_GAME_WON,
                    new GameResultUI(
                            (Frame)gamingScene.getUi(),
                            gamingScene.getLocalTicTacToeGameManager().getGameEngine().getWinnerNumber().toString(),
                            Integer.toString(1 - gamingScene.getLocalTicTacToeGameManager().getGameEngine().getWinnerNumber()))
            );
            gameWonScreen.setAppStateLifecycleListener(new GameWonScreenStateLifecycleListener());
        }
        return gameWonScreen;
    }

    private class GameWonScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
            cleanUp();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameLostScreen related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GameLostScreen gameLostScreen;
    private GameLostScreen getGameLostScreen() {
        if(gameLostScreen == null) {
            gameLostScreen = new GameLostScreen(
                    Constants.ID_GAME_LOST,
                    new GameResultUI(
                            (Frame)gamingScene.getUi(),
                            gamingScene.getLocalTicTacToeGameManager().getGameEngine().getWinnerNumber().toString(),
                            Integer.toString(1 - gamingScene.getLocalTicTacToeGameManager().getGameEngine().getWinnerNumber()))
            );
            gameLostScreen.setAppStateLifecycleListener(new GameLostScreenStateLifecycleListener());
        }
        return gameLostScreen;
    }


    private class GameLostScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
            cleanUp();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameLostScreen related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private GameDrawnScreen gameDrawnScreen;
    private GameDrawnScreen getGameDrawnScreen() {
        if(gameDrawnScreen == null) {
            gameDrawnScreen = new GameDrawnScreen(
                    Constants.ID_GAME_DRAWN,
                    new GameResultUI((Frame)gamingScene.getUi(), null, null)
            );
            gameDrawnScreen.setAppStateLifecycleListener(new GameDrawnScreenStateLifecycleListener());
        }
        return gameDrawnScreen;
    }


    private class GameDrawnScreenStateLifecycleListener extends AbstractAppStateLifecycleListener {
        @Override
        public void appStateStopped(AppState eventSource) {
            super.appStateStopped(eventSource);
            cleanUp();
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // helpers
    ///////////////////////////////////////////////////////////////////////////

    private void cleanUp() {
        settingsMenu = null;
        gamingScene = null;
        pauseScreen = null;
        gameWonScreen = null;
        gameLostScreen = null;
        gameDrawnScreen = null;
    }

    private Settings defaultSettings() {
        return new Settings(3, 3, 3, 2);
    }
}
