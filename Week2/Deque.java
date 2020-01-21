import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Type> implements Iterable<Type> {
	
	private class Node {
		Type value;
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
	
	private Node createNode(Type item, Node next) {
		Node newNode = new Node();
		
		newNode.value = item;
		newNode.next = next;
		
		return newNode;
	}
	
	public void addFirst(Type item) {
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
	
	public void addLast(Type item) {
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
	
	public Type removeFirst() {
		if (isEmpty()) throw new NoSuchElementException();
		
		Type tmp = head.value;
		
		if (head == tail) {
			resetQueue();
			
			return tmp;
		}
		
		head = head.next;
		size--;
		
		return tmp;
	}
	
	public Type removeLast() {
		if (isEmpty()) throw new NoSuchElementException();
		
		if (head == tail) {
			Type tmp = head.value;
			
			resetQueue();
			
			return tmp;
		}
		
		Node itr = head;
		
		while (itr.next.next != null) itr = itr.next;
		
		Type tmp = itr.next.value;
		
		itr.next = null;
		tail = itr;
		size--;
		
		return tmp;
	}
	
	public Iterator<Type> iterator() { return new QueueIterator(); }
	
	private class QueueIterator implements Iterator<Type> {
		
		private Node current = head;
		
		public boolean hasNext() { return current != null; }
		
		public Type next() {
			if (current == null) throw new NoSuchElementException();
			
			Type tmp = current.value;
			current = current.next;
			
			return tmp;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		Deque<String> k = new Deque<String>();
		k.addFirst("first");
		k.addLast("second");
		k.addFirst("third in sequence but first in queue");
		Iterator<String> kit = k.iterator();
		while (kit.hasNext()) {
			System.out.println(kit.next());
		}
	}

}
