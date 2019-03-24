package tictactoe.app.state.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {
    private int rowCount;
    private int columnCount;
    private int winLength;
    private int playersCount;
}
