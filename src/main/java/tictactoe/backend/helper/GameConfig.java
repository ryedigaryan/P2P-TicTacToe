package tictactoe.backend.helper;

import lombok.Data;

@Data
public class GameConfig {
    private final int winLength;
    private final int maxPlayersCount;
}
