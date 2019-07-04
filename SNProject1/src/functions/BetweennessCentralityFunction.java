package functions;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class BetweennessCentralityFunction<V, E> implements Function<V> {

	BetweennessCentrality<V,E> f;
	

	public BetweennessCentralityFunction(UndirectedSparseGraph<V,E> graph) {
		this.f = new BetweennessCentrality<>(graph);
	}
	
	
	@Override
	public double compute(V v) {
		return f.getVertexScore(v);
	}
	
	public double computeEdge(E e) {
		return f.getEdgeScore(e);
	}

}
