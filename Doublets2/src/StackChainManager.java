import java.io.FileNotFoundException;
import java.util.Stack;

/**
 * @author Shuai Yuan
 */

public class StackChainManager extends ChainManager {
	private Stack<Chain> connections = new Stack<Chain>();
	
	@Override
	public void add(Chain chain) {
		connections.push(chain);
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

