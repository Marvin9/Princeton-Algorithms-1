import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final int moves;
    private final Node[] solutionNodes;
    private final Board initialBoard;

    public Solver(Board init) {
        initialBoard = init;
        if (!isSolvable()) {
            solutionNodes = new Node[0];
            moves = 2; // TODO SOLVE isSolvable problem
        } else {
            if (init == null) throw new IllegalArgumentException();
            Node n = new Node(init, null);
            MinPQ<Node> pq = new MinPQ<Node>(new ByManhattan());
            pq.insert(n);
            while (!pq.min().b.isGoal()) {
                Node nd = pq.delMin();

                for (Board brd: nd.b.neighbors()) {
                    if (nd.prev != null && brd.equals(nd.prev.b)) continue;
                    Node nwnd = new Node(brd, nd);
                    pq.insert(nwnd);
                }
            }

            Node nn = pq.min();
            moves = nn.moves;
            Node[] nds = new Node[nn.moves + 1];
            int ptr = nn.moves;
            while (nn != null) {
                nds[ptr--] = nn;
                nn = nn.prev;
            }
            solutionNodes = nds.clone();
        }
    }

    private  class Node {
        Board b;
        int moves, hammingDis;
        Node prev;

        public Node(Board b, Node prev) {
            this.b = b;
            if (prev == null) this.moves = 0; 
            else this.moves = prev.moves + 1;
            this.prev = prev;
            this.hammingDis = b.manhattan();
        }
    }

    public boolean isSolvable() {
        return !(initialBoard.hamming() == 2  && initialBoard.manhattan() == 2);
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return new SolIt();
    }

    private class SolIt implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new SolIterator();
        }
    }

    private class SolIterator implements Iterator<Board> {
        private int itr;

        public SolIterator() {
            itr = 0;
        }

        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();
            Board b = solutionNodes[itr++].b;
            return b;
        }

        public boolean hasNext() {
            return itr < solutionNodes.length;
        }
    }
    
    private static class ByManhattan implements Comparator<Node> {
        public int compare(Node b1, Node b2) {
            return (b1.hammingDis + b1.moves) - (b2.hammingDis + b2.moves);
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
