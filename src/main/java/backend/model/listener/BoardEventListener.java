package backend.model.listener;

public interface BoardEventListener {

    void tileValueChanged(int row, int col, int oldValue, int newValue);
}
