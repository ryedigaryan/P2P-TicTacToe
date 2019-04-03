package tictactoe.app.state;

import generic.networking.common.MulticastConfig;
import generic.networking.endpoint.Server;
import lombok.AccessLevel;
import lombok.Getter;
import tictactoe.connector.ui.base.ServerHostingStateUI;
import tictactoe.networking.dto.PlayerInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Getter(AccessLevel.PRIVATE)
public class ServerHostingState extends AbstractTicTacToeAppStateWithoutNotification<ServerHostingStateUI> {
    private final Server server;
    private final MulticastConfig hostingConfig;

    public ServerHostingState(Integer id, ServerHostingStateUI ui, Server server, MulticastConfig hostingConfig) {
        super(id, ui);
        this.server = server;
        this.hostingConfig = hostingConfig;

        getServer().onAccept(((address, socket) -> {
            try {
                PlayerInfo playerInfo = getServer().receive(address);
                getUi().foundClient(playerInfo.playerName(), address.toString());
            } catch(IOException | ClassNotFoundException e) {
                System.out.println("Unable to receive player info from " + address);
            }
        }));
    }

    @Override
    public void start() {
        super.start();
        startHosting();
        getAppStateLifecycleListener().appStateStarted(this);
    }

    @Override
    public void pause() {
        super.pause();
        // TODO: 4/3/2019 Implement such logic, which will do minimum operations when the hosting should be paused 
        // TODO: 4/3/2019 Now it does the same as when it is stopped - it is much more overhead, because when state is paused
        // TODO: 4/3/2019 it is assumed that in some point it will be unpaused, so the resources need to be reused
        stopHosting();
        getAppStateLifecycleListener().appStatePaused(this);
    }

    @Override
    public void resume() {
        super.resume();
        startHosting();
        getAppStateLifecycleListener().appStateResumed(this);
    }

    @Override
    public void stop() {
        super.stop();
        stopHosting();
        getAppStateLifecycleListener().appStateStopped(this);
    }

    private void startHosting() {
        getServer().startPublishing(getHostingConfig(), 10, 300, TimeUnit.MILLISECONDS);
    }

    private void stopHosting() {
        getServer().stopPublishing(getHostingConfig());
    }
}
