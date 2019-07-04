package functions;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class ClosenessCentralityFunction<V, E> implements Function<V> {

	ClosenessCentrality<V,E> f;
	

	public ClosenessCentralityFunction(UndirectedSparseGraph<V,E> graph) {
		this.f = new ClosenessCentrality<>(graph);
	}
	
	
	@Override
	public double compute(V v) {
		return f.getVertexScore(v);
	}

}
