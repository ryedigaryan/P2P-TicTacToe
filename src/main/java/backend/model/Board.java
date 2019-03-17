package backend.model;

import backend.model.listener.BoardEventListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {
    final int rowCount;
    final int columnCount;

    BoardEventListener boardEventListener;

    /**
     * A matrix of ints representing a tile values. Each value is actually a number of player who marked the tile.
     * Here -1 will denote an absence of any mark.
     * It'd be better to have {@code tiles} as matrix of {@link Integer}s, so we could denote the absence of mark by
     * simply putting null value at the corresponding position, but that will reduce performance.
     */
    @Getter(AccessLevel.PRIVATE)
    final int[][] tiles;

    public Board(int rowCount, int columnCount) {
        // lombok generated constructor
        this(rowCount, columnCount, new int[rowCount][columnCount]);
    }

    public int getTile(int row, int col) {
        return tiles[row][col];
    }

    public void setTile(int row, int col, int value) {
        assert boardEventListener != null : getClass().toString() + ".boardEventListener should not be null";

        final int oldValue = tiles[row][col];
        tiles[row][col] = value;
        boardEventListener.tileValueChanged(row, col, oldValue, value);
    }


    // TODO: 3/17/2019 May be there is a need for this???????
    @Getter
    @RequiredArgsConstructor
    public class Tile {
        int value;

        public void setValue(int value) {
            this.value = value;
        }
    }
}
