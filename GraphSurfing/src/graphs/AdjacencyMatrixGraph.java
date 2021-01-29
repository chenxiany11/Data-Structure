package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * implement the Graph ADT in an adjacency matrix representation
 * @author Shuai Yuan
 *
 * @param <T>
 */

public class AdjacencyMatrixGraph<T> extends Graph<T> {
	Map<T,Integer> keyToIndex;
	List<T> indexToKey;
	int[][] matrix;
	private int countv;
	private int counte;
	
	AdjacencyMatrixGraph(Set<T> keys) {
		int size = keys.size();
		this.keyToIndex = new HashMap<T,Integer>();
		this.indexToKey = new ArrayList<T>();
		this.matrix = new int[size][size];
		// need to populate keyToIndex and indexToKey with info from keys
		for (T key : keys) {
			this.keyToIndex.put(key, this.countv);
			this.indexToKey.add(key);
			this.countv++;
		}
	}
	
	@Override
	public int size() {
		// TO DO Auto-generated method stub
		return this.countv;
	}

	@Override
	public int numEdges() {
		// TO DO Auto-generated method stub
		return this.counte;
	}

	@Override
	public boolean addEdge(T from, T to) {
		// TO DO Auto-generated method stub
		if (!this.keyToIndex.containsKey(from) || !this.keyToIndex.containsKey(to)) 
			throw new NoSuchElementException();
		
		int indexf = this.keyToIndex.get(from);
		int indext = this.keyToIndex.get(to);
		if (this.matrix[indexf][indext] == 0) {
			this.counte++;
			this.matrix[indexf][indext] = 1;
			return true;
		}
		return false;
	}

	@Override
	public boolean hasVertex(T key) {
		// TO DO Auto-generated method stub
		return this.keyToIndex.containsKey(key);
	}

	@Override
	public boolean hasEdge(T from, T to) throws NoSuchElementException {
		// TO DO Auto-generated method stub
		if (!this.keyToIndex.containsKey(from) || !this.keyToIndex.containsKey(to)) 
			throw new NoSuchElementException();

		int indexf = this.keyToIndex.get(from);
		int indext = this.keyToIndex.get(to);
		return this.matrix[indexf][indext] == 1;
	}

	@Override
	public boolean removeEdge(T from, T to) throws NoSuchElementException {
		// TO DO Auto-generated method stub
		if (!this.keyToIndex.containsKey(from) || !this.keyToIndex.containsKey(to)) 
			throw new NoSuchElementException();
		
		int indexf = this.keyToIndex.get(from);
		int indext = this.keyToIndex.get(to);
		if (this.matrix[indexf][indext] == 1) {
			this.counte--;
			this.matrix[indexf][indext] = 0;
			return true;
		}
		return false;
	}

	@Override
	public int outDegree(T key) {
		// TO DO Auto-generated method stub
		try {
			int temp = this.keyToIndex.get(key);
			int result = 0;
			for (int i = 0; i < this.countv; i++) {
				if (this.matrix[temp][i] == 1) result++;
			}
			return result;
		}
		catch (NullPointerException e) {
			throw new NoSuchElementException(); 
		}
	}

	@Override
	public int inDegree(T key) {
		// TO DO Auto-generated method stub
		try {
			int temp = this.keyToIndex.get(key);
			int result = 0;
			for (int i = 0; i < this.countv; i++){
				if (this.matrix[i][temp] == 1) result++;
			}		
			return result;
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Set<T> keySet() {
		// TO DO Auto-generated method stub
		return this.keyToIndex.keySet();
	}

	@Override
	public Set<T> successorSet(T key) {
		// TO DO Auto-generated method stub
		try {
			Set<T> s = new HashSet<>();
			int temp = this.keyToIndex.get(key);
			for (int i = 0; i < this.countv; i++){
				if (this.matrix[temp][i] == 1) s.add(this.indexToKey.get(i));
			}	
			return s;
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Set<T> predecessorSet(T key) {
		// TO DO Auto-generated method stub
		try {
			Set<T> s = new HashSet<>();
			int temp = this.keyToIndex.get(key);
			for (int i = 0; i < this.countv; i++){
				if (this.matrix[i][temp] == 1) s.add(this.indexToKey.get(i));
			}
			return s;
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Iterator<T> successorIterator(T key) {
		// TO DO Auto-generated method stub
		try {
			return new Iter(this.indexToKey, this.matrix, this.keyToIndex.get(key), true);
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Iterator<T> predecessorIterator(T key) {
		// TO DO Auto-generated method stub
		try {
			return new Iter(this.indexToKey, this.matrix, this.keyToIndex.get(key), false);
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Set<T> stronglyConnectedComponent(T key) {
		// TO DO Auto-generated method stub
		Queue<T> qa = new LinkedList<T>();
		Set<T> sa = new HashSet<T>();
		Queue<T> qb = new LinkedList<T>();
		Set<T> sb = new HashSet<T>();
		sa.add(key);
		sb.add(key);
		qa.add(key);
		qb.add(key);
		while (!qb.isEmpty()) {
			Set<T> sList = successorSet(qb.poll());
			for (T l : sList) {
				if (!sb.contains(l)) {
					qb.add(l);
					sb.add(l);
				}
			}
		}
		while (!qa.isEmpty()) {
			Set<T> pList = predecessorSet(qa.poll());
			for (T l : pList) {
				if (!sa.contains(l)) {
					qa.add(l);
					sa.add(l);
				}
			}
		}

		Set<T> result = new HashSet<T>();
		for (T temp : sa) {
			if (sb.contains(temp)) result.add(temp);
		}
		return result;
	}

	@Override
	public List<T> shortestPath(T startLabel, T endLabel) {
		// TO DO Auto-generated method stub
		HashMap<T, T> map = new HashMap<T, T>();
		Queue<T> q = new LinkedList<T>();
		q.add(startLabel);
		map.put(startLabel, null);
		while (!q.isEmpty()) {
			T temp = q.poll();
			if (temp.equals(endLabel)) return showPath(map, temp);
			Iterator<T> iterator = successorIterator(temp);
			while (iterator.hasNext()) {
				T next = iterator.next();
				if (!map.containsKey(next)) {
					map.put(next, temp);
					q.add(next);
				}
			}
		}
		return null;
	}
	
	private List<T> showPath(HashMap<T, T> path, T end) {
		T t = end;
		LinkedList<T> result = new LinkedList<T>();
		while (t != null) {
			result.addFirst(t);
			t = path.get(t);
		}
		return result;
	}
	
	private class Iter implements Iterator<T> {
		List<T> indexToKey;
		int[][] matrix;
		int count;
		int f;
		int t = -1;
		boolean boo;

		Iter(List<T> indexToKey, int[][] matrix, int from, boolean b) {
			this.indexToKey = indexToKey;
			this.matrix = matrix;
			this.count = this.matrix.length;
			this.f = from;
			this.boo = b;
		}

		@Override
		public boolean hasNext() {
			for (int i = this.t + 1; i < this.count; i++) {
				if (this.boo){
					if(this.matrix[f][i] == 1) return true;
				}
				else{
					if(this.matrix[i][f] == 1) return true;
				}
			}
			return false;
		}

		@Override
		public T next() {
			for (int i = this.t + 1; i < this.count; i++) {
				if (this.boo) {
					if (this.matrix[f][i] == 1) {
						this.t = i;
						break;
					}
				} else {
					if (this.matrix[i][f] == 1) {
						this.t = i;
						break;
					}
				}
			}
			return this.indexToKey.get(this.t);
		}
	}

}
