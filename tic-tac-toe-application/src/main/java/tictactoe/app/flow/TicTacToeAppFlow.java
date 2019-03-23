package tictactoe.app.flow;

import genericapp.AppFlow;
import tictactoe.app.flow.item.GamingScene;
import tictactoe.app.flow.item.LoserScreen;
import tictactoe.app.flow.item.MainMenu;
import tictactoe.app.flow.item.PauseScreen;
import tictactoe.app.flow.item.SettingsMenu;
import tictactoe.app.flow.item.WinnerScreen;

public class TicTacToeAppFlow extends AppFlow {
    public TicTacToeAppFlow() {
        super(new MainMenu());
        mainMenu = getCurrentAppFlowItem();
        registerAppFlowItemChangeRule(getCurrentAppFlowItem(), MainMenu.GAME_SETTINGS, this::lazySettingsMenuInit);
        registerAppFlowItemChangeRule(getCurrentAppFlowItem(), MainMenu.START_GAME, this::lazyGamingSceneInit);
    }

    private MainMenu mainMenu;
    private MainMenu lazyMainMenuInit() {
        if(mainMenu == null)
            mainMenu = new MainMenu();
        return mainMenu;
    }

    private SettingsMenu settingsMenu;
    private SettingsMenu lazySettingsMenuInit() {
        if(settingsMenu == null)
            settingsMenu = new SettingsMenu();
        return settingsMenu;
    }

    private GamingScene gamingScene;
    private GamingScene lazyGamingSceneInit() {
        if(gamingScene == null)
            gamingScene = new GamingScene();
        return gamingScene;
    }

    private PauseScreen pauseScreen;
    private PauseScreen lazyPauseScreenInit() {
        if(pauseScreen == null)
            pauseScreen = new PauseScreen();
        return pauseScreen;
    }

    private WinnerScreen winnerScreen;
    private WinnerScreen lazyWinnerScreenInit() {
        if(winnerScreen == null)
            winnerScreen = new WinnerScreen();
        return winnerScreen;
    }

    private LoserScreen loserScreen;
    private LoserScreen lazyLoserScreenInit() {
        if(loserScreen == null)
            loserScreen = new LoserScreen();
        return loserScreen;
    }
}
