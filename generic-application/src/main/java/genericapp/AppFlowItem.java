package genericapp;

public interface AppFlowItem extends Runnable {

    AppFlowItemEventHandler<? extends AppFlowItemEvent> getAppFlowItemEventHandler();
    void setAppFlowItemEventHandler(AppFlowItemEventHandler<? extends AppFlowItemEvent> eventHandler);

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
