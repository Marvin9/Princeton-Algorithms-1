import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] queue;
	private int elements; // element will be placed at queue[elements]
	
	public RandomizedQueue() {
		queue = (Item[]) new Object[2];
		elements = 0;
	}
	
	public boolean isEmpty() {
		return elements == 0;
	}
	
	public int size() {
		return elements;
	}
	
	public void enqueue(Item item) {
		if (item == null) throw new IllegalArgumentException();
		
		if (elements == queue.length) queue = resize(queue.length * 2);
		
		queue[elements] = item;
		elements++;
	}
	
	public Item dequeue() {
		if (elements == 0) throw new NoSuchElementException();
		
		int random = generateRandom();
		Item tmpRandom = queue[random];
		
		queue[random] = queue[elements - 1];
		queue[elements - 1] = null;
		elements--;
		
		if (elements == (queue.length)/4) queue = resize(queue.length / 2);
		
		return tmpRandom;
	}
	
	private Item[] resize(int len) {
		Item[] resizedQueue = (Item[]) new Object[len];
		
		for (int i = 0; i < elements; i++) {
			resizedQueue[i] = queue[i];
		}
		
		return resizedQueue;
	}
	
	public Iterator<Item> iterator() { return new RQIterator(); }
	
	private class RQIterator implements Iterator<Item> {
		private int current = 0;
		
		public boolean hasNext() {
			return current < elements;
		}
		
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			
			Item tmp = queue[current];
			current++;
			
			return tmp;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private int generateRandom() {
		return StdRandom.uniform(0, elements);
	}
	
	public Item sample() {
		if (elements == 0) throw new NoSuchElementException();
		
		return queue[generateRandom()];
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> e = new RandomizedQueue<Integer>();
		e.enqueue(10);
		e.enqueue(20);
		e.enqueue(30);
		e.enqueue(40);
		e.enqueue(50);
		Iterator<Integer> eit = e.iterator();
		
		while (eit.hasNext())
			System.out.println(eit.next());
	}
}
