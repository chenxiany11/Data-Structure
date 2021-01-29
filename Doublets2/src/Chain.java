import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;


/**
 * @author Shuai Yuan
 * 
 * A class that stores a word and a set of links to another word
 */
public final class Chain implements Iterable<String> {
	private final LinkedList<String> word;
	
	public Chain() {
		word = new LinkedList<String>();
	}
	
	// Creates a new chain from an old one
	public Chain(LinkedList<String> oldSet, String str) {
		word = new LinkedList<String>(oldSet);
		word.addLast(str);
	}
	
	
	//  Adds a string to a new chain and returns it
	public Chain addLast(String string) {
		return new Chain(word, string);
	}

	// Returns the last String object in the Chain
	public String getLast() {
		return word.getLast();
	}

	// The number of strings in the chain
	public int length() {
		return word.size();
	}

	// Chain contains string ?
	public boolean contains(String string) {
		return word.contains(string);
	}
	
	@Override
	public String toString() {
		return word.toString();
	}

	@Override
	public Iterator<String> iterator() {
		return word.iterator();
	}
}

