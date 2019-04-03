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
public class BoardInfo implements Serializable {
    private Integer markRow;
    private Integer markColumn;
    private Integer markValue;
    private boolean isEmptyValue = false;
}
