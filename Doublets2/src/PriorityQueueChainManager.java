import java.util.LinkedList;

public class PriorityQueueChainManager extends ChainManager {
	private String end;
	private LinkedList<Entry> link = new LinkedList<Entry>();
	
	public PriorityQueueChainManager(String str) {
		end = str;
	}

	private class Entry implements Comparable {
		public int value = 0;
		public Chain chain;
		
		public Entry(Chain chain) {
			value = chain.length() + diff(end, chain.getLast());
			this.chain = chain;
		}

		@Override
		public int compareTo(Object arg0) {
			if(((Entry)arg0).value < this.value) return -1;
			if(((Entry)arg0).value > this.value) return 1;
			return 0;
		}
	}

	
	 // Returns the number of differences between String a and String b

	public static int diff(String a, String b) {
		if (a.length() != b.length())
			return -1;

		// if there's a difference, increment the counter
		int count = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) count++;
		}
		return count;
	}

	@Override
	public void add(Chain chain) {
		int index = 0;
		Entry newEntry = new Entry(chain);
		
		if (isEmpty()) link.add(new Entry(chain));
		
		while (index < link.size() && link.get(index).compareTo(newEntry) > 0) index++;
		link.add(index, newEntry);
	}

	@Override
	public Chain next() {
		if(isEmpty()) return null;
		else return link.pop().chain;
	}

	@Override
	public boolean isEmpty() {
		return link.size() < 1;
	}
}

