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
 * implement the Graph ADT in an adjacency list representation 
 * @author yuans
 *
 * @param <T>
 */

public class AdjacencyListGraph<T> extends Graph<T> {
	Map<T,Vertex> keyToVertex;
	private int countv;
	private int counte;
	
	private class Vertex {
		T key;
		List<T> successors;
		List<T> predecessors;
		Set<Vertex> successorsSet;
		Set<Vertex> predecessorsSet;
		Vertex(T key) {
			this.key = key;
			this.successors = new ArrayList<>();
			this.predecessors = new ArrayList<>();
			this.successorsSet = new HashSet<>();
			this.predecessorsSet = new HashSet<>();
		}
	}
	
	AdjacencyListGraph(Set<T> keys) {
		this.keyToVertex = new HashMap<T,Vertex>();
		for (T key : keys) {
			Vertex v = new Vertex(key);
			this.keyToVertex.put(key, v);
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
	public boolean addEdge(T from, T to) throws NoSuchElementException {
		// TO DO Auto-generated method stub
		Vertex f = this.keyToVertex.get(from);
		Vertex t = this.keyToVertex.get(to);
		
		if (!this.keyToVertex.containsKey(from) || !this.keyToVertex.containsKey(to)) throw new NoSuchElementException();
		if (f.successorsSet.add(t) && t.predecessorsSet.add(f)) {
			f.successors.add(to);
			t.predecessors.add(from);
			this.counte++; 
			return true;
		}
		return false;
	}

	@Override
	public boolean hasVertex(T key) {
		// TO DO Auto-generated method stub
		return this.keyToVertex.containsKey(key);
	}

	@Override
	public boolean hasEdge(T from, T to) throws NoSuchElementException {
		// TO DO Auto-generated method stub
		Vertex f = this.keyToVertex.get(from);
		Vertex t = this.keyToVertex.get(to);
		
		if (!this.keyToVertex.containsKey(from) || !this.keyToVertex.containsKey(to)) throw new NoSuchElementException();
		return f.successorsSet.contains(t);
	}

	@Override
	public boolean removeEdge(T from, T to) throws NoSuchElementException {
		// TO DO Auto-generated method stub
		Vertex f = this.keyToVertex.get(from);
		Vertex t = this.keyToVertex.get(to);
		if (!this.keyToVertex.containsKey(from) || !this.keyToVertex.containsKey(to)) throw new NoSuchElementException();
		if (f.successorsSet.remove(t) && t.predecessorsSet.remove(f)) {
			this.counte--; 
			f.successors.remove(to);
			t.predecessors.remove(from);
			return true;
		}
		return false;
	}

	@Override
	public int outDegree(T key) {
		// TO DO Auto-generated method stub
		try {
			return this.keyToVertex.get(key).successorsSet.size();
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public int inDegree(T key) {
		// TO DO Auto-generated method stub
		try {
			return this.keyToVertex.get(key).predecessorsSet.size();
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Set<T> keySet() {
		// TO DO Auto-generated method stub
		return this.keyToVertex.keySet();
	}

	@Override
	public Set<T> successorSet(T key) {
		// TO DO Auto-generated method stub
		try {
			Set<T> s = new HashSet<>();
			for (Vertex v : this.keyToVertex.get(key).successorsSet){
				s.add(v.key);	
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
			for (Vertex v : this.keyToVertex.get(key).predecessorsSet){
				s.add(v.key);
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
			return this.keyToVertex.get(key).successors.iterator();
		} catch (NullPointerException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Iterator<T> predecessorIterator(T key) {
		// TO DO Auto-generated method stub
		try {
			return this.keyToVertex.get(key).predecessors.iterator();
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
	
}