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
