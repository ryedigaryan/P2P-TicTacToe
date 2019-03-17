package backend.logic;

import backend.Utils;
import backend.helper.GameConfig;
import backend.model.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A basic tic-tac-toe engine designed for only 2 players.
 */
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameEngine {
    private final GameConfig gameConfig;
    private final Board board;

    @Setter(AccessLevel.NONE)
    private int currentPlayerNumber;

    public GameEngine(GameConfig gameConfig, Board board) {
        // lombok generated constructor
        this(gameConfig, board, Utils.randomInt(gameConfig.getMaxPlayersCount()));
    }


}
