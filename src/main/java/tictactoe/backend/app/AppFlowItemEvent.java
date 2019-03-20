package tictactoe.backend.app;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AppFlowItemEvent {
    private boolean stopPreviousAppFlowItem;
}
