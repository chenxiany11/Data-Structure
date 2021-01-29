package queues;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A circular queue that grows as needed.
 * 
 * @author Matt Boutell and <<<your name here>>>
 * @param <T>
 */
public class GrowableCircularArrayQueue<T> implements SimpleQueue<T> {
	// TO DO: Declare this class to implement SimpleQueue<T>, then add the
	// missing methods (Eclipse will help).
	// TODO: The javadoc for overridden methods is in the SimpleQueue interface.
	// Read it!

	private static final int INITIAL_CAPACITY = 5;

	private T[] array;
	private Class<T> type;
	private int index = 0;
	private int deq_index = 0;

	/**
	 * Creates an empty queue with an initial capacity of 5
	 * 
	 * @param type
	 *            So that an array of this type can be constructed.
	 */
	@SuppressWarnings("unchecked")
	public GrowableCircularArrayQueue(Class<T> type) {
		this.type = type;
		// This is a workaround due to a limitation Java has with
		// constructing generic arrays.
		this.array = (T[]) Array.newInstance(this.type, INITIAL_CAPACITY);
	}

	/**
	 * Displays the contents of the queue in insertion order, with the
	 * most-recently inserted one last, in other words, not wrapped around. Each
	 * adjacent pair will be separated by a comma and a space, and the whole
	 * contents will be bounded by square brackets. See the unit tests for
	 * examples.
	 */
	@Override
	public String toString() {
		// TODO: implement this method
		StringBuilder retString = new StringBuilder();
		retString.append('[');
		for(int i = 0; i < this.array.length-1; i++){
			if(this.array[i] != null){
				if(this.array[i+1] != null){
					retString.append(this.array[i]);
					retString.append(", ");	
				}
				else{
					retString.append(this.array[i]);	
				}
				
			}
		}
		retString.append(']');
		return retString.toString();
	}

	@Override
	public void clear() {
		// TO DO Auto-generated method stub
		for(int i= 0; i<this.array.length; i++) this.array[i] = null;
	
	}

	@Override
	public void enqueue(T item) {
		this.array[index] = item;
		index++;
		if(index == this.array.length) {
			T[] temp = Arrays.copyOf(this.array, 2*this.array.length);
			this.array = temp;
		}
	}

	@Override
	public T dequeue() throws NoSuchElementException {
		// TO DO Auto-generated method stub
		if(this.array[deq_index] == null) throw new NoSuchElementException();
		T a = this.array[deq_index];
		this.array[deq_index] = null;
		deq_index++;
		return a;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if(this.array[deq_index] == null) throw new NoSuchElementException();
		// TO DO Auto-generated method stub
		T a = this.array[deq_index];
//		deq_index++;
		return a;
	}

	@Override
	public boolean isEmpty() {
		// TO DO Auto-generated method stub
		return this.array[deq_index] == null;
	}

	@Override
	public int size() {
		// TO DO Auto-generated method stub
		int count = 0;
		for(int i = 0; i< this.array.length; i++){
			if (this.array[i]!=null) ++count;	
		}
		
		return count;
	}

	@Override
	public boolean contains(T item) {
		// TODO Auto-generated method stub
		for(int i = 0; i<this.array.length; i++){
			if(this.array[i] == item) return true;
		}
		return false;
	}

	@Override
	public String debugString() {
		// TODO Auto-generated method stub
		return null;
	}


}
