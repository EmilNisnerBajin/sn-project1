package functions;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class NodeDegreeFunction<V,E> implements Function<V> {

	UndirectedSparseGraph<V,E> graph;
	
	public NodeDegreeFunction(UndirectedSparseGraph<V,E> graph) {
		this.graph = graph;
	}
	
	@Override
	public double compute(V v) {
		return graph.degree(v);
	}

}
