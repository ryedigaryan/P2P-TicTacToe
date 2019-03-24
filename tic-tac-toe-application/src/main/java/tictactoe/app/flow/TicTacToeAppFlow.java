package tictactoe.app.flow;

import genericapp.AbstractAppFlowItemStateChangeListener;
import genericapp.AppFlow;
import genericapp.AppFlowItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.app.flow.item.GamingScene;
import tictactoe.app.flow.item.GameLostScreen;
import tictactoe.app.flow.item.MainMenu;
import tictactoe.app.flow.item.PauseScreen;
import tictactoe.app.flow.item.SettingsMenu;
import tictactoe.app.flow.item.GameWonScreen;
import tictactoe.app.flow.item.common.Settings;
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
        setInitialAppFlowItem(getMainMenu());
        // handle Main Menu events
        registerAppFlowItemChangeRule(Constants.ID_MAIN_MENU, MainMenu.GAME_SETTINGS, this::getSettingsMenu);
        registerAppFlowItemChangeRule(Constants.ID_MAIN_MENU, MainMenu.START_GAME, this::getGamingScene);
        // handle Settings Menu events
        registerAppFlowItemChangeRule(Constants.ID_SETTINGS_MENU, SettingsMenu.BACK_TO_MAIN_MENU, this::getMainMenu);
        // handle Gaming Scene events
        registerAppFlowItemChangeRule(Constants.ID_GAMING_SCENE, GamingScene.PAUSE, this::getPauseScreen);
        registerAppFlowItemChangeRule(Constants.ID_GAMING_SCENE, GamingScene.GAME_WON, this::getGameWonScreen);
        registerAppFlowItemChangeRule(Constants.ID_GAMING_SCENE, GamingScene.GAME_LOST, this::getGameLostScreen);
        // handle Pause Screen events
        registerAppFlowItemChangeRule(Constants.ID_PAUSE_SCREEN, PauseScreen.CLOSE, this::getGamingScene);
        // TODO: 3/23/2019 Think wisely
        registerAppFlowItemChangeRule(Constants.ID_PAUSE_SCREEN, PauseScreen.LEAVE_GAME, this::getMainMenu);
        // handle End of Game Screen events
        registerAppFlowItemChangeRule(Constants.ID_GAME_WON, GameWonScreen.CLOSE, this::getMainMenu);
        registerAppFlowItemChangeRule(Constants.ID_GAME_LOST, GameLostScreen.CLOSE, this::getMainMenu);
    }

    ///////////////////////////////////////////////////////////////////////////
    // MainMenu related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private MainMenu mainMenu;
    private MainMenu getMainMenu() {
        if(mainMenu == null) {
            mainMenu = new MainMenu(Constants.ID_MAIN_MENU, new MainMenuUI());
            mainMenu.setAppFlowItemStateChangeListener(new MainMenuStateChangeListener());
        }
        return mainMenu;
    }

    private class MainMenuStateChangeListener extends AbstractAppFlowItemStateChangeListener {
        @Override
        public void appFlowItemStopped(AppFlowItem eventSource) {
            super.appFlowItemStopped(eventSource);
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
            settingsMenu.setAppFlowItemStateChangeListener(new SettingsMenuStateChangeListener());
        }
        return settingsMenu;
    }

    private class SettingsMenuStateChangeListener extends AbstractAppFlowItemStateChangeListener {
        @Override
        public void appFlowItemStopped(AppFlowItem eventSource) {
            super.appFlowItemStopped(eventSource);
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
            assert gameSettings != null : "Game Settings may not be null when initializing gamingScene";

            GameConfig config = new GameConfig(gameSettings.getWinLength(), gameSettings.getPlayersCount());
            Board board = new Board(gameSettings.getRowCount(), gameSettings.getColumnCount());
            GameEngine engine = new GameEngine(config, board);
            LocalGameManager ticTacToeGameManager = new LocalGameManager(engine, new GameBoardUI(gameSettings.getRowCount(), gameSettings.getColumnCount()));

            gamingScene = new GamingScene(Constants.ID_GAMING_SCENE, ticTacToeGameManager);
            gamingScene.setAppFlowItemStateChangeListener(new GamingSceneStateChangeListener());
        }
        return gamingScene;
    }

    private class GamingSceneStateChangeListener extends AbstractAppFlowItemStateChangeListener {

        @Override
        public void appFlowItemStarted(AppFlowItem eventSource) {
            super.appFlowItemStarted(eventSource);
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
            pauseScreen.setAppFlowItemStateChangeListener(new PauseScreenStateChangeListener());
        }
        return pauseScreen;
    }

    private class PauseScreenStateChangeListener extends AbstractAppFlowItemStateChangeListener {
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
            gameWonScreen.setAppFlowItemStateChangeListener(new GameWonScreenStateChangeListener());
        }
        return gameWonScreen;
    }

    private class GameWonScreenStateChangeListener extends AbstractAppFlowItemStateChangeListener {
        @Override
        public void appFlowItemStopped(AppFlowItem eventSource) {
            super.appFlowItemStopped(eventSource);
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
            gameLostScreen.setAppFlowItemStateChangeListener(new GameLostScreenStateChangeListener());
        }
        return gameLostScreen;
    }


    private class GameLostScreenStateChangeListener extends AbstractAppFlowItemStateChangeListener {
        @Override
        public void appFlowItemStopped(AppFlowItem eventSource) {
            super.appFlowItemStopped(eventSource);
            cleanUp();
        }
    }

    private void cleanUp() {
        settingsMenu = null;
        gamingScene = null;
        pauseScreen = null;
        gameWonScreen = null;
        gameLostScreen = null;
    }
}
