/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Jha'Deya Rhymes
 * @version 1.0
 * @userid jrhymes7
 * @GTID 903588553
 */
import java.util.NoSuchElementException;
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Index out of bounds.");
        } else {
            if (index == 0) {
                addToFront(data);
            } else if (index == size) {
                addToBack(data);
            } else {
                int i = 0;
                SinglyLinkedListNode<T> cursor = head;
                SinglyLinkedListNode<T> head_next = cursor.getNext();
                while (i < index - 1) {
                    cursor = head_next;
                    head_next = cursor.getNext();
                    i++;
                }
                SinglyLinkedListNode<T> node = new SinglyLinkedListNode<>(data, head_next);
                cursor.setNext(node);
                size = size + 1;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null){
            throw new IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            head = new SinglyLinkedListNode<T>(data, null);
            tail = new SinglyLinkedListNode<T>(data, null);
        } else if (size == 1) {
            head = new SinglyLinkedListNode<T>(data, tail);
        } else {
            head = new SinglyLinkedListNode<T>(data, head);
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null){
            throw new IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            tail = new SinglyLinkedListNode<>(data, null);
            head = tail;
        } else if (size == 1) {
            tail = new SinglyLinkedListNode<>(data, null);
            head.setNext(tail);
        } else {
            tail = new SinglyLinkedListNode<T>(data, null);

            SinglyLinkedListNode<T> current = head;

            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(tail);
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        T removed;
        if (index == 0) {
            removed = head.getData();
            head = head.getNext();
            size = size - 1;
            return removed;
        }
        SinglyLinkedListNode<T> cursor = head;
        for (int i = 0; i < (index - 1); i++) {
            cursor = cursor.getNext();
        }
        size = size - 1;
        removed = cursor.getNext().getData();
        cursor.setNext(cursor.getNext().getNext());
        return removed;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
            T removed;
            if (size == 0) {
                return null;
            } else if (size == 1) {
                removed = head.getData();
                clear();
            } else {
                size--;
                removed = head.getData();
                head = head.getNext();

                if (size == 1) {
                    head = tail;
                }
            }

            return removed;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
            T removed;
            if (size == 0) {
                throw new NoSuchElementException("List is empty.");
            } else if (size == 1) {

                if (head != null) {
                    removed = head.getData();
                } else {
                    removed = tail.getData();
                }
                clear();
            } else {
                SinglyLinkedListNode<T> current = head;

                for (int i = 0; i < size - 2; i++) {
                    current = current.getNext();
                }

                removed = current.getNext().getData();

                current.setNext(null);
                tail = current;
                size--;

                if (size == 1) {
                    tail = head;
                }
            }

            return removed;

        }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        SinglyLinkedListNode<T> cursor = head;
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds.");
        } else {
            if (index == 0) {
                return head.getData();
            }
            for (int i = 0; i < (index); i++) {
                cursor = cursor.getNext();
            }
            return cursor.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        if (head == null) {
            throw new NoSuchElementException("The list is already clear.");
        }
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null){
            throw new IllegalArgumentException("Data is null.");
        }
        int index = 0;
        SinglyLinkedListNode<T> cursor = head;
        T location = null;
        for (int j = 0; j < size; j++){
            if (cursor.getData() == data){
                location = cursor.getData();
                index = j;
            }
            cursor = cursor.getNext();
        }
        if (location == null){
            throw new NoSuchElementException("Data not found");
        }
        return removeAtIndex(index);
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        SinglyLinkedListNode<T> cursor = head;
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (cursor.getData());
            cursor = cursor.getNext();
        }
        return (T[]) arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}