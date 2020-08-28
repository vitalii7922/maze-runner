package maze;

import java.io.Serializable;

public class Point implements Serializable {
    private int row;
    private int col;

    public Point(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
