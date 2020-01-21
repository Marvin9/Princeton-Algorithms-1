import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    private int[][] grid;
	final private WeightedQuickUnionUF modal;
	private int openSites;
	final private int numberOfRows;
	final private int totalSites;
	
	public Percolation(int n) {
		if (n < 0) throw new IllegalArgumentException();
		grid = new int[n][n];
		numberOfRows = n;
		totalSites = n * n;
		openSites = 0;
		modal = new WeightedQuickUnionUF(n * n + 2);
	}
	
	public void open(int row, int col) {
		validate(row, col);
		if (!isOpen(row, col)) {
			int currentKey = key(row, col);
			
			// first row is connected to virtual top node
			if (row == 1) { 
				modal.union(currentKey, 0);
			}
			
			// check left open
			if (col - 1 > 0 && isOpen(row, col - 1)) {
				int leftKey = key(row, col - 1);
				modal.union(currentKey, leftKey);
			}
			
			// check bottom open
			if (row + 1 <= numberOfRows && isOpen(row + 1, col)) {
				int downKey = key(row + 1, col);
				modal.union(currentKey, downKey);
			}
			
			// check right open
			if (col + 1 <= numberOfRows && isOpen(row, col + 1)) {
				int rightKey = key(row, col + 1);
				modal.union(currentKey, rightKey);
			}
			
			// check upper open
			if (row - 1 > 0 && isOpen(row - 1, col)) {
				int upKey = key(row - 1, col);
				modal.union(currentKey, upKey);
			}
			
			grid[row-1][col-1] = 1;
			openSites++;
			
			// last row is connected to virtual bottom row
			if (row == numberOfRows) modal.union(currentKey, totalSites + 1);
		}
	}
	
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return grid[row-1][col-1] == 1 ? true : false;
	}
	
	private int key(int row, int col) {
		return (numberOfRows * (row - 1)) + col;
	}
	
	public boolean isFull(int row, int col) {
		validate(row, col);
		int currKey = key(row, col);
		if (isOpen(row, col) && (modal.find(currKey) == modal.find(0))) {
			return true;
		}
		return false;
	}
	
	public int numberOfOpenSites() {
		return openSites;
	}
	
	public boolean percolates() {
		// if virtual top and bottom row are connected then system perculates.
		return modal.find(0) == modal.find(totalSites+1);
	}
	
	private void validate(int row, int col) {
		if (row <= 0) throw new IllegalArgumentException("Row should not be negative & zero");
		if (col <= 0) throw new IllegalArgumentException("Column should not be negative & zero");
		if (row > numberOfRows) throw new IllegalArgumentException("Row should not exceed " + (numberOfRows));
		if (col > numberOfRows) throw new IllegalArgumentException("Row should not exceed " + (numberOfRows));
	}
	
	public static void main(String[] arg) {
		Percolation p = new Percolation(5);
		while (true) {
			int r = StdIn.readInt();
			int c = StdIn.readInt();
			if (r == -1 && c == -1) break;
			p.open(r, c);
			StdOut.println("Percolates : " + p.isFull(r, c));
		}
	}
}
