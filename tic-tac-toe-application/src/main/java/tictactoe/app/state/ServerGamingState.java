package tictactoe.app.state;

import generic.networking.common.MulticastConfig;
import generic.networking.endpoint.Server;
import lombok.AccessLevel;
import lombok.Getter;
import tictactoe.backend.logic.GameEngine;
import tictactoe.backend.model.GameState;
import tictactoe.connector.ui.base.GamingStateUI;
import tictactoe.networking.dto.BoardInfo;
import tictactoe.networking.dto.GameInfo;

import java.io.IOException;
import java.io.Serializable;

@Getter(AccessLevel.PRIVATE)
public class ServerGamingState<UIType extends GamingStateUI> extends GamingState<UIType> {
    private final Server gameServer;
    private final MulticastConfig serverMulticastConfig;
    private final GameEngine gameEngine;

    public ServerGamingState(Integer id, GameEngine gameEngine, UIType ui, Server gameServer, MulticastConfig serverMulticastConfig) {
        super(id, ui);

        this.gameEngine = gameEngine;
        this.gameServer = gameServer;
        this.serverMulticastConfig = serverMulticastConfig;
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameStateChangeListener overridden methods
    // (showing backend changes on UI)
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void playerWon(int playerNumber) {
        broadcastGameState(playerNumber, GameState.WON);
    }

    @Override
    public void playerLost(int playerNumber) {
        broadcastGameState(playerNumber, GameState.LOST);
    }

    @Override
    public void draw() {
        broadcastGameState(null, GameState.DRAWN);
    }

    ///////////////////////////////////////////////////////////////////////////
    // TileEventListener (backend) overridden methods
    // (showing backend changes on UI)
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void valueChanged(int row, int col, int oldValue, int newValue) {
        broadcastTileValueChange(row, col, newValue);
    }

    @Override
    public void valueErased(int row, int col, int oldValue) {
        broadcast(
                new BoardInfo()
                        .markRow(row)
                        .markColumn(col)
                        .isEmptyValue(true)
        );
    }

    ///////////////////////////////////////////////////////////////////////////
    // GamingStateUIListener overridden methods
    // (passing UI calls to backend)
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void tileClicked(int row, int col) {
        processUserInput(row, col, getPlayerNumber());
    }

    @Override
    public void pauseGame() {
        broadcast(
                new GameInfo()
                        .gameState(GameState.IN_PROGRESS)
                        .shouldPauseGame(true)
        );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helper methods
    ///////////////////////////////////////////////////////////////////////////

    private void broadcastGameState(Integer playerNumber, GameState won) {
        broadcast(
                new GameInfo()
                        .gameState(won)
                        .winnerNumber(playerNumber)
        );
    }

    private void broadcastTileValueChange(Integer row, Integer column, Integer value) {
        broadcast(
                new BoardInfo()
                        .markRow(row)
                        .markColumn(column)
                        .markValue(value)
        );
    }

    private void broadcast(Serializable o) {
        try {
            getGameServer().sendToAll(o);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void processUserInput(int row, int col, int value) {
        if(getGameEngine().getCurrentPlayerNumber() == value) {
            getGameEngine().acceptNextPlayerMark(row, col);
        }
    }
}
