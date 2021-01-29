import java.util.LinkedList;
/**
 * @author Shuai Yuan
 */
public class QueueChainManager extends ChainManager {
	private LinkedList<Chain> connections = new LinkedList<Chain>();
	
	@Override
	public void add(Chain chain) {
		connections.add(connections.size(), chain);
		updateMax(connections.size());
	}

	@Override
	public Chain next() {
		this.incrementNumNexts();
		if(connections.isEmpty()) return null;
		return connections.pop();
	}

	@Override
	public boolean isEmpty() {
		return connections.isEmpty();
	}
}

