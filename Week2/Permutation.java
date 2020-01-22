import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		while (!StdIn.isEmpty()) {
			rq.enqueue(StdIn.readString());
		}
		
		while (k > 0) {
			StdOut.println(rq.dequeue());
			k--;
		}
	}

}
