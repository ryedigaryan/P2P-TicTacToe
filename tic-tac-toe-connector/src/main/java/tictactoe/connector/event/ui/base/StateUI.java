package tictactoe.connector.event.ui.base;

public interface StateUI<L> {
    void setListener(L listener);

    void start();
    void pause();
    void resume();
    void stop();
}
