package tictactoe.app.flow;

import generic.app.AbstractAppStateLifecycleListener;
import generic.app.AppFlow;
import generic.app.AppState;
import generic.networking.common.MulticastConfig;
import generic.networking.endpoint.Client;
import generic.networking.endpoint.Server;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.app.grdon.TemporarySolution;
import tictactoe.app.state.ClientGamingState;
import tictactoe.app.state.GameDrawnState;
import tictactoe.app.state.GameLostState;
import tictactoe.app.state.GameWonState;
import tictactoe.app.state.LocalGamingState;
import tictactoe.app.state.MainMenuState;
import tictactoe.app.state.PausedState;
import tictactoe.app.state.ServerGamingState;
import tictactoe.app.state.SettingsMenuState;
import tictactoe.connector.common.data.Settings;
import tictactoe.backend.logic.GameConfig;
import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.model.Board;
import tictactoe.connector.ui.GameStateDescriptor;
import tictactoe.ui.state.GameBoardUI;
import tictactoe.ui.state.GameDrawnUI;
import tictactoe.ui.state.GameLostUI;
import tictactoe.ui.state.GameWonUI;
import tictactoe.ui.state.MainMenuUI;
import tictactoe.ui.state.PausedPopUp;
import tictactoe.ui.state.SettingsMenuUI;

import java.io.IOException;
import java.io.ObjectInputStream;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class TicTacToeAppFlow extends AppFlow {

    public TicTacToeAppFlow() {
        setCurrentAppState(getMainMenuState());
        // handle Main Menu events
        registerAppStateChangeRule(Constants.ID_MAIN_MENU, MainMenuState.GAME_SETTINGS, this::getSettingsMenuState);
        registerAppStateChangeRule(Constants.ID_MAIN_MENU, MainMenuState.START_GAME, this::getLocalGamingState);
        // handle Settings Menu events
        registerAppStateChangeRule(Constants.ID_SETTINGS_MENU, SettingsMenuState.BACK_TO_MAIN_MENU, this::getMainMenuState);
        // handle Gaming Scene events
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, LocalGamingState.PAUSE, this::getPausedState);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, LocalGamingState.GAME_WON, this::getGameWonScreen);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, LocalGamingState.GAME_LOST, this::getGameLostScreen);
        registerAppStateChangeRule(Constants.ID_GAMING_SCENE, LocalGamingState.GAME_DRAWN, this::getGameDrawnScreen);
        // handle Pause Screen events
        registerAppStateChangeRule(Constants.ID_PAUSE_SCREEN, PausedState.CLOSE, this::getLocalGamingState);
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
        cleanupAppStates();
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
            assert localGamingState == null : "GameScene should be null when retrieving game settings from SettingsMenuState";
            setGameSettings(getSettingsMenuState().getGameSettings());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // LocalGamingState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private LocalGamingState<GameBoardUI> localGamingState;
    private LocalGamingState<GameBoardUI> getLocalGamingState() {
        if(localGamingState == null) {
            GameConfig config = new GameConfig(gameSettings.getWinLength(), gameSettings.getPlayersCount());
            Board board = new Board(gameSettings.getRowCount(), gameSettings.getColumnCount());
            GameEngine engine = new GameEngine(config, board);

            localGamingState = new LocalGamingState<>(Constants.ID_GAMING_SCENE, engine, new GameBoardUI(gameSettings.getRowCount(), gameSettings.getColumnCount()));
            localGamingState.setAppStateLifecycleListener(new GamingSceneStateLifecycleListener());
        }
        return localGamingState;
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
    // ServerGamingState
    ///////////////////////////////////////////////////////////////////////////

    private ServerGamingState<GameBoardUI> serverGamingState;
    private ServerGamingState<GameBoardUI> getServerGamingState() {
        if(serverGamingState == null) {
            GameConfig config = new GameConfig(gameSettings.getWinLength(), gameSettings.getPlayersCount());
            Board board = new Board(gameSettings.getRowCount(), gameSettings.getColumnCount());
            GameEngine engine = new GameEngine(config, board);

            Server gameServer = createGameServer();
            serverGamingState = new ServerGamingState<>(
                    Constants.ID_GAMING_SCENE,
                    engine,
                    new GameBoardUI(gameSettings.getRowCount(), gameSettings.getColumnCount()),
                    gameServer,
                    new MulticastConfig(Constants.SERVER_MULTICAST_GROUP, Constants.SERVER_MULTICAST_MESSAGE, null)
            );
            serverGamingState.setAppStateLifecycleListener(new GamingSceneStateLifecycleListener());
        }
        return serverGamingState;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ClientGamingState
    ///////////////////////////////////////////////////////////////////////////

    private ClientGamingState/*<GameBoardUI>*/ clientGamingState;
    private ClientGamingState/*<GameBoardUI>*/ getClientGamingState() {
        if(clientGamingState == null) {
            GameConfig config = new GameConfig(gameSettings.getWinLength(), gameSettings.getPlayersCount());
            Board board = new Board(gameSettings.getRowCount(), gameSettings.getColumnCount());
            GameEngine engine = new GameEngine(config, board);

            Client gameClient = createGameClient();
            // clientGamingState = new ClientGamingState<>(
            //         Constants.ID_GAMING_SCENE,
            //         engine,
            //         new GameBoardUI(gameSettings.getRowCount(), gameSettings.getColumnCount()),
            //         gameClient,
            //         new MulticastConfig(Constants.SERVER_MULTICAST_GROUP, Constants.SERVER_MULTICAST_MESSAGE, null)
            // );
            // clientGamingState.setAppStateLifecycleListener(new GamingSceneStateLifecycleListener());
        }
        return clientGamingState;
    }

    ///////////////////////////////////////////////////////////////////////////
    // PausedState related fields, methods, inner classes
    ///////////////////////////////////////////////////////////////////////////

    private PausedState pausedState;
    private PausedState getPausedState() {
        if(pausedState == null) {
            GameStateDescriptor gsd = () -> "It is " + localGamingState.getPlayerName() + " turn";
            pausedState = new PausedState(Constants.ID_PAUSE_SCREEN, new PausedPopUp(localGamingState.getUi(), gsd));
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
                    new GameWonUI(localGamingState.getUi(), localGamingState.getWinnerName())
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
                    new GameLostUI(localGamingState.getUi(), localGamingState.getWinnerName())
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
                    new GameDrawnUI(localGamingState.getUi())
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

    private void cleanupAppStates() {
        settingsMenuState = null;
        localGamingState = null;
        pausedState = null;
        gameWonScreen = null;
        gameLostScreen = null;
        gameDrawnScreen = null;
    }

    private Server createGameServer() {
        return Server.builder()
                .socketSendExceptionHandler(TemporarySolution::printOnErr)
                .socketReceiveExceptionHandler(TemporarySolution::printOnErr)
                .multicastJoinExceptionHandler(TemporarySolution::printOnErr)
                .datagramInitExceptionHandler(TemporarySolution::printOnErr)
                .socketInitExceptionHandler(TemporarySolution::printOnErr)
                .clientSocketAcceptor(s -> {
                    try {
                        Object clientObject = new ObjectInputStream(s.getInputStream()).readObject();
                        return Constants.CLIENT_ACCEPTANCE_OBJECT.equals(clientObject);
                    } catch (IOException | ClassNotFoundException e) {
                        TemporarySolution.printOnErr(e);
                        return false;
                    }
                })
                .scheduledThreadPoolSupplier(Constants::sharedScheduledThreadPool)
                .threadPoolSupplier(Constants::sharedCachedThreadPool)
                .serverPort(0)
                .build();
    }

    private Client createGameClient() {
        return null;
    }
}
