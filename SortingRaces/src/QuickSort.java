public class QuickSort {

	private static void insertionSort(int[] array, int startPoint, int endPoint) {
		int i = startPoint + 1;
		while (i <= endPoint) {
			int key = array[i];
			int j = i - 1;
			while (j >= 0 && array[j] > key) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j + 1] = key;
			i++;
		}
	}

	public static void quickSort(int[] array) {
		quickSortHelper(array, 0, array.length - 1);
	}

	public static void quickSortHelper(int[] array, int startPoint, int endPoint) {
		if (endPoint - startPoint < 25) {
			insertionSort(array, startPoint, endPoint);
		} else {

			int middlePoint = (startPoint + endPoint) / 2;

			int[] pivotArray = { array[startPoint], array[middlePoint], array[endPoint] };
			insertionSort(pivotArray, 0, 2);
			array[startPoint] = pivotArray[0];
			array[middlePoint] = pivotArray[1];
			array[endPoint] = pivotArray[2];

			swap(array, middlePoint, endPoint - 1);
			int pivot = array[endPoint - 1];

			int i = startPoint;
			int j = endPoint - 1;

			while (true) {
				do {
					i++;
				} while (array[i] < pivot);

				do {
					j--;
				} while (array[j] > pivot);

				if (i >= j)
					break;
				swap(array, i, j);
			}

			swap(array, i, endPoint - 1);
			quickSortHelper(array, startPoint, i - 1);
			quickSortHelper(array, i + 1, endPoint);
		}

	}

	public static void swap(int[] array, int position1, int position2) {
		int temp = array[position1];
		array[position1] = array[position2];
		array[position2] = temp;
	}

}
