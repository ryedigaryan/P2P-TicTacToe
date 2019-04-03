package tictactoe.networking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;
import tictactoe.backend.model.GameState;

import java.io.Serializable;

@Data
@Wither
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class GameInfo implements Serializable {
    private GameState gameState;
    private Integer winnerNumber;
    private boolean shouldPauseGame = false;
}

