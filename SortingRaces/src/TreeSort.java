import java.util.HashMap;
import java.util.TreeSet;

public class TreeSort {
	public static void treeSetSort(Integer[] array) {
		TreeSet<Integer> treeSet = new TreeSet<>();
		HashMap<Integer, Integer> hash = new HashMap<>();

		for (int k : array) {
			if (treeSet.contains(k)) {
				incrementHash(hash, k);
			} else {
				treeSet.add(k);
			}
		}

		for (int i = 0; i < array.length; i++) {
			int temp = treeSet.first();

			if (hash.keySet().contains(temp)) {
				decrementHash(hash, temp);

				array[i] = temp;
			} else {
				array[i] = treeSet.pollFirst();
			}

		}
	}

	public static void incrementHash(HashMap hash, Integer key) {
		if (hash.containsKey(key)) {
			int temp = (int) hash.get(key) + 1;
			hash.put(key, temp);
		} else {
			hash.put(key, 1);
		}
	}

	public static void decrementHash(HashMap hash, int key) {
		if ((int) hash.get(key) == 1) {
			hash.remove(key);
		} else {
			hash.put(key, (int) hash.get(key) - 1);
		}
	}
}
