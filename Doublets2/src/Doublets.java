import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Shuai Yuan
 * 
 *         A class used to methodically search through a dictionary to find
 *         doublets of words
 * 
 *         The main method is an interactive program to enter words to find the
 *         doublet chain
 */
public class Doublets {
	private LinksInterface dictionary;

	public Doublets(LinksInterface links) {
		this.dictionary = links;
	}

	/**
	 * An interactive program to enter words to find the doublet chain between
	 * them
	 * 
	 * @param args
	 *            Not used, require for console arguments
	 * @throws FileNotFoundException
	 *             If it can't find the dictionary it breaks
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Welcome to Doublets, a game of \"verbal torture.\"\nLoading...");
		Scanner sc = new Scanner(System.in);
		Links dictionary = new Links("../DoubletsData/english.cleaned.all.35.txt");
		Doublets dblts = new Doublets(dictionary);
		String startWord = "";
		String endWord = "";
		String managerType;

		while (true) {
			// Get the words
			System.out.print("Enter starting word: ");
			startWord = sc.nextLine();
			System.out.print("Enter ending word: ");
			endWord = sc.nextLine();

			// Check if words are valid
			if (!dictionary.exists(startWord)) {
				System.out.println("The word \"" + startWord + "\" is not valid. Please try again.");
				continue;
			}
			if (!dictionary.exists(endWord)) {
				System.out.println("The word \"" + endWord + "\" is not valid. Please try again.");
				continue;
			}

			// Allow exit, but also get manager type
			System.out.print("Enter chain manager (s: stack, q: queue, p: priority queue, x: exit): ");
			managerType = sc.nextLine();

			if (managerType.equals("x"))
				break;

			ChainManager manager = new StackChainManager(); // default stack,
															// only change if
															// not stack
			if (managerType.equals("q"))
				manager = new QueueChainManager();
			else if (managerType.equals("p"))
				manager = new PriorityQueueChainManager(endWord);

			Chain path = dblts.findChain(startWord, endWord, manager);

			if (path == null)
				System.out.println("No doublet chain exists from " + startWord + " to " + endWord + ".");
			else {
				System.out.println("Chain: " + path.toString());
				System.out.println("Length: " + path.length());
				System.out.println("Candidates: " + manager.getNumberOfNexts());
				System.out.println("Candidates: " + manager.maxSize());
			}
		}
		System.out.println("Goodbye!");
		sc.close();
	}

	/**
	 * Finds the chain of words from the 'start' to the 'end'
	 * 
	 * @param start
	 *            - start word
	 * @param end
	 *            - end word
	 * @param manager
	 *            - chain manager
	 * @return Calls the recursive helper method which will ultimately return
	 *         the chain.
	 */
	public Chain findChain(String start, String end, ChainManager manager) {
		if (start.length() != end.length())
			return null;

		Chain startChain = new Chain();
		startChain = startChain.addLast(start);
		manager.add(startChain);
		manager.updateMax(1);

		Chain nextChain = manager.next();

		while (nextChain != null) {
			if (nextChain.getLast().equals(end))
				return nextChain;
			else {
				String last = nextChain.getLast();
				Set<String> candidateSet = dictionary.getCandidates(last);

				if (candidateSet == null) {
					if (!manager.isEmpty()) {
						nextChain = manager.next();
						continue;
					} else
						break;
				}

				for (String candidate : candidateSet) {
					if (!nextChain.contains(candidate))
						manager.add(nextChain.addLast(candidate));
				}
			}

			nextChain = manager.next();
		}

		return null;
	}

}
