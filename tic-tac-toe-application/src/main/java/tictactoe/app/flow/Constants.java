package tictactoe.app.flow;

import generic.app.AbstractAppStateLifecycleListener;
import generic.app.AppState;
import generic.app.AppStateLifecycleListener;
import tictactoe.connector.common.data.Settings;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

    byte[] SERVER_MULTICAST_MESSAGE = "SERVER_SEARCHES_FOR_CLIENT".getBytes();
    static byte[] serverMulticastMessage() {
        return SERVER_MULTICAST_MESSAGE;
    }

    String CLIENT_ACCEPTANCE_OBJECT = "CLIENT_AGREE_TO_CONNECT_SERVER";
    static String clientAcceptanceObject() {
        return CLIENT_ACCEPTANCE_OBJECT;
    }

    ScheduledExecutorService SHARED_SCHEDULED_THREAD_POOL = Executors.newScheduledThreadPool(4);
    static ScheduledExecutorService sharedScheduledThreadPool() {
        return SHARED_SCHEDULED_THREAD_POOL;
    }

    ExecutorService SHARED_CACHED_THREAD_POOL = Executors.newCachedThreadPool();
    static ExecutorService sharedCachedThreadPool() {
        return SHARED_CACHED_THREAD_POOL;
    }

    InetSocketAddress SERVER_MULTICAST_GROUP = new InetSocketAddress(0);
}
