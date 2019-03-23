package tictactoe.app.flow;

import genericapp.AppFlow;
import tictactoe.app.flow.item.GamingScene;
import tictactoe.app.flow.item.GameLostScreen;
import tictactoe.app.flow.item.MainMenu;
import tictactoe.app.flow.item.PauseScreen;
import tictactoe.app.flow.item.SettingsMenu;
import tictactoe.app.flow.item.GameWonScreen;

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

    private MainMenu mainMenu;
    private MainMenu getMainMenu() {
        if(mainMenu == null)
            mainMenu = new MainMenu(Constants.ID_MAIN_MENU);
        return mainMenu;
    }

    private SettingsMenu settingsMenu;
    private SettingsMenu getSettingsMenu() {
        if(settingsMenu == null)
            settingsMenu = new SettingsMenu(Constants.ID_SETTINGS_MENU);
        return settingsMenu;
    }

    private GamingScene gamingScene;
    private GamingScene getGamingScene() {
        if(gamingScene == null)
            gamingScene = new GamingScene(Constants.ID_GAMING_SCENE);
        return gamingScene;
    }

    private PauseScreen pauseScreen;
    private PauseScreen getPauseScreen() {
        if(pauseScreen == null)
            pauseScreen = new PauseScreen(Constants.ID_PAUSE_SCREEN);
        return pauseScreen;
    }

    private GameWonScreen gameWonScreen;
    private GameWonScreen getGameWonScreen() {
        if(gameWonScreen == null)
            gameWonScreen = new GameWonScreen(Constants.ID_GAME_WON);
        return gameWonScreen;
    }

    private GameLostScreen gameLostScreen;
    private GameLostScreen getGameLostScreen() {
        if(gameLostScreen == null)
            gameLostScreen = new GameLostScreen(Constants.ID_GAME_LOST);
        return gameLostScreen;
    }
}
