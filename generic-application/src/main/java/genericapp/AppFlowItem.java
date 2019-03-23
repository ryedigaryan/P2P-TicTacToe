package genericapp;

public interface AppFlowItem extends Runnable {

    AppFlowItemEventHandler<? extends AppFlowItemEvent> getEventHandler();
    void setEventHandler(AppFlowItemEventHandler<? extends AppFlowItemEvent> eventHandler);

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
     * Overriding method of {@link Runnable} for easy navigation in source code.
     */
    @Override
    void run();
}
