package genericapp;

public interface AppState extends Runnable {

    AppFlowItemEventHandler getAppFlowItemEventHandler();
    void setAppFlowItemEventHandler(AppFlowItemEventHandler eventHandler);

    AppFlowItemStateChangeListener getAppFlowItemStateChangeListener();
    void setAppFlowItemStateChangeListener(AppFlowItemStateChangeListener listener);

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
