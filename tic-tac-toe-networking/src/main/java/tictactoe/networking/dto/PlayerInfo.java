package tictactoe.networking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;

import java.io.Serializable;

@Data
@Wither
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInfo implements Serializable {
    private String playerName;
    private Integer playerNumber;
}
