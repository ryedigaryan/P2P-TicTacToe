package backend.model;

import backend.model.listener.TileEventListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class Board {
    final int rowCount;
    final int columnCount;

    TileEventListener tileEventListener;

    /**
     * A matrix of ints representing a tile values. Each value is actually a number of player who marked the tile.
     * Here -1 will denote an absence of any mark.
     * It'd be better to have {@code tiles} as matrix of {@link Integer}s, so we could denote the absence of mark by
     * simply putting null value at the corresponding position, but that will reduce performance.
     */
    @Getter(AccessLevel.PRIVATE)
    final Tile[][] tiles;

    public Board(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tiles = new Tile[rowCount][columnCount];
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    @Getter
    @RequiredArgsConstructor
    public class Tile {
        public static final int EMPTY_VALUE = -1;
        // for now these fields are final, because in tic-tac-toe there is no need to move the tiles
        // but in common this fields also should be modifiable
        final int row;
        final int col;

        int value = EMPTY_VALUE;

        public void setValue(int value) {
            final int oldValue = this.value;
            this.value = value;
            tileEventListener.valueChanged(row, col, oldValue, this.value);
        }

        public void makeEmpty() {
            final int oldValue = this.value;
            this.value = EMPTY_VALUE;
            tileEventListener.valueErased(row, col, oldValue);
        }

        public boolean isEmpty() {
            return value == EMPTY_VALUE;
        }
    }
}
