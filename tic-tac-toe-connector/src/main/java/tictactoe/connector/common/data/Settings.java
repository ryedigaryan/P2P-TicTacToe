package tictactoe.connector.common.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings implements Cloneable {
    private int rowCount;
    private int columnCount;
    private int winLength;
    private int playersCount;

    @Override
    public Settings clone() {
        try {
            return (Settings) super.clone();
        } catch (CloneNotSupportedException e) {
            // this will not happen... but still, print stack trace just in case...
            e.printStackTrace();
            return null;
        }
    }
}
