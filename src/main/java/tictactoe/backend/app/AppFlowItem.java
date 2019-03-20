package tictactoe.backend.app;

public interface AppFlowItem extends Runnable {

    AppFlowItemEventHandler<? extends AppFlowItemEvent> getEventHandler();
    void setEventHandler(AppFlowItemEventHandler<? extends AppFlowItemEvent> eventHandler);

    boolean isStarted();
    boolean isPaused();
    boolean isStopped();

    void pause();
    void resume();
    void stop();
}
