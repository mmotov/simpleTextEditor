package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        size = 0;
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        add(size, element);
        return true;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        LLNode<E> newNode = new LLNode<>(element);
        LLNode<E> indexNode = index == size ? tail : getNode(index);
		LLNode<E> prev = indexNode.prev;
		prev.next = newNode;
		indexNode.prev = newNode;
		newNode.prev = prev;
		newNode.next = indexNode;
		size++;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {

        return getNode(index).data;
    }

    /**
     * Return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        LLNode<E> indexNode = getNode(index);
		LLNode<E> prevNode = indexNode.prev;
		LLNode<E> nextNode = indexNode.next;

		prevNode.next = nextNode;
		nextNode.prev = prevNode;

		indexNode.next = null;
		indexNode.prev = null;

		size--;
        return indexNode.data;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
	 * @throws NullPointerException if the element is null.
     */
    public E set(int index, E element) {

    	if (element == null) {
    		throw new NullPointerException();
		}
        LLNode<E> indexNode = getNode(index);
        E oldVal = indexNode.data;
        indexNode.data = element;
        return oldVal;
    }

	/**
	 * Get the node at position index
	 *
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
    private LLNode<E> getNode(int index) {

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		LLNode<E> indexNode = head.next;
		for (int i = 0; i < index; i++) {
			indexNode = indexNode.next;
		}
		return indexNode;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor


    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
