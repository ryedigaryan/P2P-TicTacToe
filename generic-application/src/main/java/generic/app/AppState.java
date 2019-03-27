package generic.app;

public interface AppState extends Runnable {

    AppStateEventHandler getAppStateEventHandler();
    void setAppStateEventHandler(AppStateEventHandler eventHandler);

    AppStateLifecycleListener getAppStateLifecycleListener();
    void setAppStateLifecycleListener(AppStateLifecycleListener listener);

    Integer getId();

    boolean isStarted();
    boolean isPaused();
    boolean isStopped();

    default void start() {
        run();
    }
    void pause();
    void resume();
    void stop();

    /**
     * Overriding method of {@link Runnable} for convenient navigation in source code.
     */
    @Override
    void run();
}
