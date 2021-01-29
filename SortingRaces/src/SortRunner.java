import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * This program runs various sorts and gathers timing information on them.
 *
 * @author <<YOUR NAMES HERE>> Created May 7, 2013.
 */
public class SortRunner {
	private static Random rand = new Random(17); // uses a fixed seed for
													// debugging. Remove the
													// parameter later.

	/**
	 * Starts here.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// array size must be an int. You will need to use something much larger
		int size = 500000;

		// Each integer will have the range from [0, maxValue). If this is
		// significantly higher than size, you
		// will have small likelihood of getting duplicates.
		int maxValue = Integer.MAX_VALUE;

		// Test 1: Array of random values.
		int[] randomIntArray = getRandomIntArray(size, maxValue);
		System.err.println("Random Array");
		runAllSortsForOneArray(randomIntArray);

		// TODO: Tests 2-4
		// Generate the three other types of arrays (shuffled, almost sorted,
		// almost reverse sorted)
		// and run the sorts on those as well.
		int[] suffledIntArray = getUniqueElementArray(size);
		int[] almostSortedIntArray = getAlmostSortedIntArray(size, maxValue);
		int[] almostReverseSortedArray = getAlmostReverseSortedArray(size, maxValue);

		System.err.println("Suffled Array");
		runAllSortsForOneArray(suffledIntArray);
		System.err.println("AlmostSorted Array");
		runAllSortsForOneArray(almostSortedIntArray);
		System.err.println("AlmostReverseSorted Array");
		runAllSortsForOneArray(almostReverseSortedArray);
		//

	}

	/**
	 * 
	 * Runs all the specified sorts on the given array and outputs timing
	 * results on each.
	 *
	 * @param array
	 */
	private static void runAllSortsForOneArray(int[] array) {
		long startTime, elapsedTime;
		boolean isSorted = false;

		// TODO: Read this.
		// We prepare the arrays. This can take as long as needed to shuffle
		// items, convert
		// back and forth from ints to Integers and vice-versa, since you aren't
		// timing this
		// part. You are just timing the sort itself.

		int[] sortedIntsUsingDefaultSort = array.clone();
		Integer[] sortedIntegersUsingDefaultSort = copyToIntegerArray(array);
		Integer[] sortedIntegersUsingHeapSort = sortedIntegersUsingDefaultSort.clone();
		Integer[] sortedIntegersUsingTreeSort = sortedIntegersUsingDefaultSort.clone();
		// No skiplist this term. Integer[] sortedIntegersUsingSkipListSort =
		// sortedIntegersUsingDefaultSort.clone();
		int[] sortedIntsUsingQuickSort = array.clone();

		int size = array.length;

		System.out.println("-----------------Default sort ----------------");
		// What is the default sort for ints? Read the javadoc.
		startTime = System.currentTimeMillis();
		Arrays.sort(sortedIntsUsingDefaultSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingDefaultSort);
		displayResults("int", "the default sort", elapsedTime, size, isSorted);

		// What is the default sort for Integers (which are objects that wrap
		// ints)?
		startTime = System.currentTimeMillis();
		Arrays.sort(sortedIntegersUsingDefaultSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingDefaultSort);
		displayResults("Integer", "the default sort", elapsedTime, size, isSorted);
		System.out.println("--------------------------------------------------");
		
		System.out.println("-----------------TreeSet sort ----------------");
		// Sort using the following methods, and time and verify each like done
		// above.
		// TODO: a simple sort that uses a TreeSet but handles a few duplicates
		// gracefully.
		startTime = System.currentTimeMillis();
		TreeSort.treeSetSort(sortedIntegersUsingTreeSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingTreeSort);
		displayResults("Integer", "the treeset sort", elapsedTime, size, isSorted);
		System.out.println("--------------------------------------------------");

		// TODO: your implementation of quick sort. I suggest putting this in a
		// static method in a Quicksort class.
		System.out.println("-----------------Quick sort ----------------");
		// Sort using the following methods, and time and verify each like done
		// above.
		// TODO: a simple sort that uses a TreeSet but handles a few duplicates
		// gracefully.
		startTime = System.currentTimeMillis();
		QuickSort.quickSort(sortedIntsUsingQuickSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingQuickSort);
		displayResults("Integer", "the quick sort", elapsedTime, size, isSorted);
		System.out.println("--------------------------------------------------");

		// TODO: your BinaryHeap sort. You can put this sort in a static method
		// in another class.
		System.out.println("-----------------BinaryHeap sort ----------------");
		// Sort using the following methods, and time and verify each like done
		// above.
		// TODO: a simple sort that uses a TreeSet but handles a few duplicates
		// gracefully.
		startTime = System.currentTimeMillis();
		BinaryHeap.sort(sortedIntegersUsingHeapSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingHeapSort);
		displayResults("Integer", "the heap sort", elapsedTime, size, isSorted);
		System.out.println("--------------------------------------------------");

	}

	private static void displayResults(String typeName, String sortName, long elapsedTime, int size, boolean isSorted) {
		if (isSorted) {
			System.out.printf("Sorted %.1e %ss using %s in %d milliseconds\n", (double) size, typeName, sortName,
					elapsedTime);
		} else {
			System.out.println("ARRAY NOT SORTED");
		}
	}

	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a
	 *            An array to check to see if it is sorted.
	 */
	private static boolean verifySort(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] > a[i + 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a
	 *            An array to check to see if it is sorted.
	 */
	private static boolean verifySort(Integer[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] > a[i + 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Copies from an int array to an Integer array.
	 *
	 * @param randomIntArray
	 * @return A clone of the primitive int array, but with Integer objects.
	 */
	private static Integer[] copyToIntegerArray(int[] ints) {
		Integer[] integers = new Integer[ints.length];
		for (int i = 0; i < ints.length; i++) {
			integers[i] = ints[i];
		}
		return integers;
	}

	/**
	 * Creates and returns an array of random ints of the given size.
	 *
	 * @return An array of random ints.
	 */
	private static int[] getRandomIntArray(int size, int maxValue) {
		int[] a = new int[size];
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(maxValue);
		}
		return a;
	}

	private static int[] getAlmostReverseSortedArray(int size, int maxValue) {
		int[] returnArray = new int[size];
		int[] temp = getAlmostSortedIntArray(size, maxValue);
		int j = size;
		for (int i = 0; i < size; i++) {
			returnArray[j - 1] = temp[i];
			j--;
		}

		return returnArray;
	}

	private static int[] getAlmostSortedIntArray(int size, int maxValue) {
		int[] returnArray = getRandomIntArray(size, maxValue);
		Arrays.sort(returnArray);
		int maxShuffle = size / (int) (Math.random() * (200));
		for (int i = 0; i < maxShuffle; i++) {
			int firstIndex = (int) (Math.random() * (size - 1));
			int switchIndex = (int) (Math.random() * (size - 1));
			int temp = returnArray[firstIndex];
			returnArray[firstIndex] = returnArray[switchIndex];
			returnArray[switchIndex] = temp;
		}

		return returnArray;
	}

	/**
	 * Creates a shuffled random array.
	 *
	 * @param size
	 * @return An array of the ints from 0 to size-1, all shuffled
	 */
	private static int[] getUniqueElementArray(int size) {
		ArrayList<Integer> aryLst = new ArrayList<>();
		int[] returnArray = new int[size];
		for (int i = 0; i < size; i++) {
			aryLst.add(i);
		}
		Collections.shuffle(aryLst);
		for (int i = 0; i < size; i++) {
			returnArray[size - i - 1] = aryLst.get(i);
		}
		return returnArray;
	}

}
