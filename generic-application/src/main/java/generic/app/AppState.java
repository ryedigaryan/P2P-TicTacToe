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

    void start();
    void pause();
    void resume();
    void stop();

    /**
     * In the case if AppState should be run parallel.
     */
    @Override
    default void run() {
        start();
    }
}
