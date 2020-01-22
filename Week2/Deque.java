import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	
	private class Node {
		Item value;
		Node next;
	}
	
	private Node head;
	private Node tail;
	private int size;
	
	public Deque() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public int size() {
		return size;
	}
	
	private Node createNode(Item item, Node next) {
		Node newNode = new Node();
		
		newNode.value = item;
		newNode.next = next;
		
		return newNode;
	}
	
	public void addFirst(Item item) {
		if (item == null) throw new IllegalArgumentException();
		
		if (head == null) {
			head = createNode(item, null);
			tail = head;
		} else {
			Node firstNode = createNode(item, head);
			head = firstNode;
		}
		
		size++;
	}
	
	public void addLast(Item item) {
		if (item == null) throw new IllegalArgumentException();
		
		if (head == null) {
			head = createNode(item, null);
			tail = head;
		} else {
			Node lastNode = createNode(item, null);
			tail.next = lastNode;
			tail = lastNode;
		}
		
		size++;
	}
	
	private void resetQueue() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public Item removeFirst() {
		if (isEmpty()) throw new NoSuchElementException();
		
		Item tmp = head.value;
		
		if (head == tail) {
			resetQueue();
			
			return tmp;
		}
		
		head = head.next;
		size--;
		
		return tmp;
	}
	
	public Item removeLast() {
		if (isEmpty()) throw new NoSuchElementException();
		
		if (head == tail) {
			Item tmp = head.value;
			
			resetQueue();
			
			return tmp;
		}
		
		Node itr = head;
		
		while (itr.next.next != null) itr = itr.next;
		
		Item tmp = itr.next.value;
		
		itr.next = null;
		tail = itr;
		size--;
		
		return tmp;
	}
	
	public Iterator<Item> iterator() { return new QueueIterator(); }
	
	private class QueueIterator implements Iterator<Item> {
		
		private Node current = head;
		
		public boolean hasNext() { return current != null; }
		
		public Item next() {
			if (current == null) throw new NoSuchElementException();
			
			Item tmp = current.value;
			current = current.next;
			
			return tmp;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		Deque<String> k = new Deque<String>();
		k.addFirst("hello");
		k.addLast("bye");
		k.addFirst("yesyyy");
		Iterator<String> kit = k.iterator();
		while (kit.hasNext()) {
			System.out.println(kit.next());
		}
	}

}
