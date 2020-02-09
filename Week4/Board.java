import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] tiles;
    private final int dimension;
    private int zero;
    private int hamm;
    private int mann;

    public Board(int[][] tiles) {
        dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) zero = getNumber(i, j);
            }
        }
        hamm = findHamming();
        mann = findManhattan();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                str.append(tiles[i][j] + " ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public int dimension() {
        return dimension;
    }
    
    public int hamming() {
        return hamm;
    }
    
    public int manhattan() {
        return mann;
    }
    
    private int findHamming() {
        int distance = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != getNumber(i, j)) distance++;
            }
        }
        return distance - 1;
    }

    private int findManhattan() {
        int man = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int num = tiles[i][j];
                if (num == getNumber(i, j) || num == 0) continue;
                int row = (num - 1) / dimension;
                int column = (num - 1) % dimension;
                man += abs(i, row) + abs(j, column);
            }
        }
        return man;
    }

    public boolean isGoal() {
        return hamm == 0;
    }

    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        if (this == y) return true;
        Board k = (Board) y;
        if (dimension != k.dimension) return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != k.tiles[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        return new NeighBor();
    }

    public Board twin() {
        int[][] twinTiles = new int[dimension][dimension];
        boolean swap = true;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                twinTiles[i][j] = tiles[i][j];
                if (swap == true && j > 0 && tiles[i][j-1] != 0 && tiles[i][j] != 0) {
                    int tmp = twinTiles[i][j-1];
                    twinTiles[i][j-1] = twinTiles[i][j];
                    twinTiles[i][j] = tmp;
                    swap = false;
                }
            }
        }
        return new Board(twinTiles);
    }

    private class NeighBor implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new NeighBorIterator();
        }
    }

    private class NeighBorIterator implements Iterator<Board> {
        private final int row;
        private final int column;
        private int possible;
        private boolean[] move;
        private int current;

        public NeighBorIterator() {
            row = (zero - 1) / dimension;
            column = (zero - 1) % dimension;
            move = new boolean[] { true, true, true, true };

            int tmp = 4;

            if (column - 1 < 0) {
                move[0] = false;
                tmp--;
            }
            if (row - 1 < 0) {
                move[1] = false;
                tmp--;
            }
            if (column + 1 >= dimension) {
                move[2] = false;
                tmp--;
            }
            if (row + 1 >= dimension) {
                move[3] = false;
                tmp--;
            }

            possible = tmp;
            current = 0;
        }

        private void leftSwap(Board b, int r, int c) {
            int lc = c - 1;
            b.tiles[r][c] = b.tiles[r][lc];
            b.tiles[r][lc] = 0;
            b.zero = getNumber(r, lc);
        }

        private void rightSwap(Board b, int r, int c) {
            int rc = c + 1;
            b.tiles[r][c] = b.tiles[r][rc];
            b.tiles[r][rc] = 0;
            b.zero = getNumber(r, rc);
        }

        private void upSwap(Board b, int r, int c) {
            int ur = r - 1;
            b.tiles[r][c] = b.tiles[ur][c];
            b.tiles[ur][c] = 0;
            b.zero = getNumber(ur, c);
        }

        private void downSwap(Board b, int r, int c) {
            int dr = r + 1;
            b.tiles[r][c] = b.tiles[dr][c];
            b.tiles[dr][c] = 0;
            b.zero = getNumber(dr, c);
        }
        
        @Override
        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();
            while (current < 4 && move[current] != true) current++;
            possible--;
            Board b = new Board(tiles);
            if (current == 0) leftSwap(b, row, column);
            else if (current == 1)  upSwap(b, row, column);
            else if (current == 2) rightSwap(b, row, column);
            else if (current == 3) downSwap(b, row, column);
            b.hamm = b.findHamming();
            b.mann = b.findManhattan();
            current++;
            return b;
        }

        @Override
        public boolean hasNext() {
            return possible > 0;
        }
    }

    private int abs(int a, int b) {
        int ans = a - b;
        return ans < 0 ? -ans : ans;
    }

    private int getNumber(int row, int column) {
        return dimension * row + (column + 1);
    }

    public static void main(String[] arg) {
        int[][] brd3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board b1 = new Board(brd3);
        StdOut.print(b1);
    }
}
