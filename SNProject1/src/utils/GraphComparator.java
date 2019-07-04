package utils;

import java.util.Comparator;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class GraphComparator<V, E> implements Comparator<UndirectedSparseGraph<V,E>> {

	@Override
	public int compare(UndirectedSparseGraph<V, E> o1, UndirectedSparseGraph<V, E> o2) {
		return o2.getVertexCount() - o1.getVertexCount();
	}}
