package tictactoe.app.flow.item.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {
    private int rowCount;
    private int columnCount;
    private int winLength;
    private int playersCount;
}
