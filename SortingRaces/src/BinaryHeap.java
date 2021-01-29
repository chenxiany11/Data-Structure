import java.lang.reflect.Array;
/**
 * BinaryHeap class that will be serving two distinct purposes: 
 * (a) as a data structure, namely a binary heap
 * (b) as a static-like class that heapsorts any array passed as a parameter. 
 * 
 * @author yuans
 *
 * @param <T>
 */
public class BinaryHeap<T extends Comparable<? super T>> {

	public T[] t;
	public Class<T> c;
	public int size;
	public int cap;
	public int count1;
	public int count2;

	public BinaryHeap(Class<T> class1) {
		this.cap = 5;
		this.c = class1;
		this.t = (T[]) Array.newInstance(this.c, this.cap + 1);
	}

	public T deleteMin() {
		// TO DO Auto-generated method stub
		T last,min,l,r;
		int i, ri, li;
		
		if (this.size == 0) return null;
		min = this.t[1];
		i = 1;
		last = this.t[this.size];
		this.t[i] = last;
		this.t[this.size] = null;
		
		while (i <= (this.size-1) / 2) {
			li = 2*i;
			ri = 2*i+1;
			l = this.t[li];
			r = this.t[ri];
			if (r == null) {
				if (last.compareTo(l) > 0) {
					this.t[i] = l;
					this.t[li] = last;
					this.count2 ++;
				}
				break;
			}
			
			if (last.compareTo(l) < 0 && last.compareTo(r) < 0) break;
			
			if (l.compareTo(r) < 0) {
				this.t[i] = l;
				this.t[li] = last;
				this.count2 ++;
				i *= 2;
			} 
			
			else {
				this.t[i] = r;
				this.t[ri] = last;
				this.count2 ++;
				i = i * 2 + 1;
			}
		}
		this.size--;
		return min;
	}
	
	public void insert(T element) {
		// TO DO Auto-generated method stub
		if (this.size >= this.cap) {
			this.cap *= 2;
			T[] array = (T[]) Array.newInstance(this.c, this.cap + 1);
			System.arraycopy(this.t, 1, array, 1, this.size);
			this.count1++;
			this.t = array;
		}
		int i = this.size + 1;
		this.t[i] = element;
		this.size++;
		while (i > 1 && this.t[i / 2].compareTo(element) > 0) {
			this.t[i] = this.t[i / 2];
			this.t[i / 2] = element;
			this.count2++;
			i /= 2;
		}
	}
	
	public static void sort(String[] array, Class<String> class1) {
		// TO DO Auto-generated method stub
		int size = array.length;
		for (int i = size / 2 - 1; i >= 0; i--) {
			BinaryHeap.percolateDown(array, i, size);
		}
		for (int i = size - 1; i >= 0; i--) {
			String temp = array[i];
			array[i] = array[0]; 
			array[0] = temp;
			size--;
			BinaryHeap.percolateDown(array, 0, size);
		}
	}
	
	public static void sort(Comparable[] array) {
		int size = array.length;
		for (int i = size / 2 - 1; i >= 0; i--) {
			BinaryHeap.percolateDown(array, i, size);
		}
		
		for (int i = size - 1; i >= 0; i--) {
			Comparable temp = array[i];
			array[i] = array[0]; 
			array[0] = temp;
			size--;
			BinaryHeap.percolateDown(array, 0, size);
		}
	}

	public String toString() {
		String s = "null" + (this.size != 0 ? ", " : "");
		for (int i = 0; i < this.size; i++){
			if(	i + 1 != this.size) s += this.t[i + 1] +  ", " ;
			else s += this.t[i + 1] + "";
		}
		return "[" + s + "]";
	}

	private static void percolateDown(Comparable[] array, int i, int size) {
		while (i <= size / 2 - 1) {
			Comparable item, l, r, temp;
			int li, ri;
			li = 2 * (i + 1) - 1;
			ri = 2 * (i + 1);
			item = array[i];
			if(li < size) l = array[li];
			else l = null;
			
			if(ri < size) r = array[ri]; 
			else r = null;
			
			if (r == null) {
				if (item.compareTo(l) < 0) {
					array[i] = l;
					array[li] = item;
				} 
				break;
			} 
			
			if (item.compareTo(l) > 0 && item.compareTo(r) > 0) break;
			
			if (l.compareTo(r) > 0) {
				array[i] = l;
				array[li] = item;
				i = li;
			} 
			
			else {
				array[i] = r;
				array[ri] = item;
				i = ri;
			}
		}
	}
}