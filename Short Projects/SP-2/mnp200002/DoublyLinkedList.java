package mnp200002;
import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

/*
 * Team Number - SP 37
 * Manav Prajapati - mnp200002
 * Rahul Bosamia - rnb200003
 */

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
    
	static class Entry<E> extends SinglyLinkedList.Entry<E> {
		Entry<E> prev;
		Entry(E x, Entry<E> next, Entry<E> prev) {
			super(x, next);
			this.prev = prev;
		}
	}

	Entry<T> head, tail;

	public DoublyLinkedList() {
		head = new Entry<T>(null, null, null);
		tail = null;
		size = 0;
	}

	public Iterator<T> dlliterator() { return new DLLIterator(); }

	protected class DLLIterator extends SLLIterator {
		Entry<T> cursor, prev;
		
		DLLIterator() {
			super();
			this.cursor = head;
			this.prev = null;
		}

		public boolean hasNext() {
			return cursor.next != null;
		}
		
		public T next() {
			prev = cursor;
			cursor = (Entry<T>) cursor.next;
			ready = true;
			return cursor.element;
		}

		public void remove() {
			if(!ready) {
				throw new NoSuchElementException();
			}

			if(cursor == tail) {
				prev.next = cursor.next;
				cursor.prev = null;
				cursor = prev;
				tail = prev;
				prev = cursor.prev;
			}
			else {
				prev.next = cursor.next;
				Entry<T> nextNode = (Entry<T>) cursor.next;
				nextNode.prev = prev;
				cursor.next = null;
				cursor.prev = null;
				cursor = prev;
				prev = cursor.prev;
			}

			ready = false;  // Calling remove again without calling next will result in exception thrown
			size--;
		}

		public boolean hasPrev() {
			return cursor.prev != null;
		}

		public T prev() {
			cursor = prev;
			prev = prev.prev;
			ready = true;
			return cursor.element;
		}

		public void add(T x) {
			add(new Entry<T>(x, null, null));
		}

		public void add(Entry<T> node) {
			node.next = cursor.next;
			node.prev = cursor;
			cursor.next = node;
			if(cursor != tail) {
				Entry<T> nextNode = (Entry<T>) cursor.next;
				nextNode.prev = node;
			}
			if(cursor == tail) {
				tail = node;
			}
			size++;
		}
	};

	public void add(T x) {
		add(new Entry<T>(x, null, null));
	}

	public void add(Entry<T> node) {
		if(head.element == null) {
			head = node;
			tail = node;
			size++;
			return;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size++;
	}

	public void printList() {
		System.out.print(size + ": ");
		Entry<T> node = (Entry<T>) head;
		while(node!=null) {
			System.out.print(node.element + " ");
			node = (Entry<T>) node.next;
		}
		System.out.println();
    }

	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

		DoublyLinkedList<Integer> dlst = new DoublyLinkedList<>();
		for(int i=1; i<=n; i++) {
            dlst.add(Integer.valueOf(i));
        }
        dlst.printList();
		
		DoublyLinkedList<Integer>.DLLIterator it = (DoublyLinkedList<Integer>.DLLIterator) dlst.dlliterator();
		Scanner in = new Scanner(System.in);

		whileloop:
		while(in.hasNext()) {
			int com = in.nextInt();
			switch(com) {
			case 1:  // Move to next element and print it
				if (it.hasNext()) {
					System.out.println("Current element: " + it.next());
				} else {
					break whileloop;
				}
				break;
			case 2:  // Remove element
				it.remove();
				dlst.printList();
				break;
			case 3: // Add element
				System.out.print("Enter element to add: ");
				int val = in.nextInt();
				it.add(Integer.valueOf(val));
				dlst.printList();
				break;
			case 4: // Previous Element
				if (it.hasPrev()) {
					System.out.println("Current element: " + it.prev());
				} else {
					break whileloop;
				}
				break;
			default:  // Exit loop
				break whileloop;
			}
		}
	}
};