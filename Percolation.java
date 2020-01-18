import java.util.Scanner;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int grid[][];
	private WeightedQuickUnionUF modal;
	private int openSites;
	private int numberOfRows;
	
	public Percolation(int n) {
		grid = new int[n][n];
		numberOfRows = n;
		openSites = 0;
		modal = new WeightedQuickUnionUF(n * n + 1);
	}
	
	public void open(int row, int col) {
		row--;
		col--;
		validate(row, col);
		if (!isOpen(row, col)) {
			int currentKey = key(row, col);
			
			if (row == 0) { 
				modal.union(currentKey, 0);
			}
			
			// check left open
			if (col - 1 >= 0 && isOpen(row, col - 1)) {
				int leftKey = key(row, col - 1);
				modal.union(currentKey, leftKey);
			}
			
			// check bottom open
			if (row + 1 < numberOfRows && isOpen(row + 1, col)) {
				int downKey = key(row + 1, col);
				modal.union(currentKey, downKey);
			}
			
			// check right open
			if (col + 1 < numberOfRows && isOpen(row, col + 1)) {
				int rightKey = key(row, col + 1);
				modal.union(currentKey, rightKey);
			}
			
			// check upper open
			if (row - 1 >= 0 && isOpen(row - 1, col)) {
				int upKey = key(row - 1, col);
				modal.union(currentKey, upKey);
			}
			
			grid[row][col] = 1;
			openSites++;
		}
	}
	
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return grid[row][col] == 1 ? true : false;
	}
	
	private int key(int row, int col) {
		return (numberOfRows * row) + col + 1;
	}
	
	public boolean isFull(int row, int col) {
		validate(row, col);
		if (isOpen(row, col)) {
			
			// check left
			if (col - 1 >= 0 && isOpen(row, col - 1)) {
				int leftKey = key(row, col - 1);
				if (modal.find(leftKey) == modal.find(0)) return true;
			}
			
			// check down
			if (row + 1 < numberOfRows && isOpen(row + 1, col)) {
				int downKey = key(row + 1, col);
				if (modal.find(downKey) == modal.find(0)) return true;
			}
			
			// check right
			if (col + 1 < numberOfRows && isOpen(row, col + 1)) {
				int rightKey = key(row, col + 1);
				if (modal.find(rightKey) == modal.find(0)) return true; 
			}
			
			// check up
			if (row - 1 >= 0 && isOpen(row - 1, col)) {
				int upKey = key(row - 1, col);
				if (modal.find(upKey) == modal.find(0)) return true; 
			}
			
			return false;
		}
		return false;
	}
	
	public int numberOfOpenSites() {
		return openSites;
	}
	
	public boolean percolates() {
		int row = numberOfRows - 1;
		for (int i = 0; i < numberOfRows; i++) {
			if (isFull(row, i)) return true;
		}
		return false;
	}
	
	private void validate(int row, int col) throws IllegalArgumentException {
		if (row < 0) throw new IllegalArgumentException("Row should not be negative & zero");
		if (col < 0) throw new IllegalArgumentException("Column should not be negative & zero");
		if (row >= numberOfRows) throw new IllegalArgumentException("Row should not exceed " + (numberOfRows - 1));
		if (col >= numberOfRows) throw new IllegalArgumentException("Row should not exceed " + (numberOfRows - 1));
	}
	
	public static void main(String arg[]) {
		Percolation p = new Percolation(5);
		Scanner sc = new Scanner(System.in);
		while (true) {
			int r = sc.nextInt();
			int c = sc.nextInt();
			if (r == -1 && c == -1) break;
			p.open(r, c);
			System.out.println("Percolates : " + p.percolates());
		}
	}
}
