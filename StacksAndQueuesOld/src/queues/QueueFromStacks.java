package queues;

import java.util.NoSuchElementException;
import java.util.Stack;

public class QueueFromStacks<T>  implements SimpleQueue<T> {
	Stack<T> stack1 = new Stack();
	Stack<T> stack2 = new Stack();
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		stack1.clear();
		stack2.clear();
	}

	@Override
	public void enqueue(T item) {
		// TODO Auto-generated method stub
		stack1.push(item);
	}

	@Override
	public T dequeue() throws NoSuchElementException {
		// TODO Auto-generated method stub
		if(stack1.isEmpty() && stack2.isEmpty()) throw new NoSuchElementException();
		int t = stack1.size();
		for(int i = 0; i< t; i++){
			T temp = stack1.pop();
			stack2.push(temp);
		}
		T a = stack2.pop();
		int t2 = stack2.size();
		for(int i = 0; i<t2; i++){
			T temp = stack2.pop();
			stack1.push(temp);
		}
				
		return a;
	}

	@Override
	public T peek() throws NoSuchElementException {
		// TODO Auto-generated method stub
		if(stack1.isEmpty() && stack2.isEmpty()) throw new NoSuchElementException();
		int t = stack1.size();
		for(int i = 0; i< t; i++){
			T temp = stack1.pop();
			stack2.push(temp);
		}
		T a = stack2.pop();
		stack2.push(a);
		int t2 = stack2.size();
		for(int i = 0; i<t2; i++){
			T temp = stack2.pop();
			stack1.push(temp);
		}
		return a;
	}

	@Override
	public boolean isEmpty() {
		// TO DO Auto-generated method stub
		return stack1.isEmpty()&& stack2.isEmpty();
	}

	@Override
	public int size() {
		// TO DO Auto-generated method stub

		return stack1.size();
	}

	@Override
	public boolean contains(T item) {
		// TODO Auto-generated method stub
		return stack1.contains(item) || stack2.contains(item);
	}

	@Override
	public String debugString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		return stack1.toString();
		
	}

}
