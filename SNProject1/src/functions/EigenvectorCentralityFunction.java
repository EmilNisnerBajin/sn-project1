package functions;

import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class EigenvectorCentralityFunction<V, E> implements Function<V> {

	EigenvectorCentrality<V,E> f;
	

	public EigenvectorCentralityFunction(UndirectedSparseGraph<V,E> graph) {
		this.f = new EigenvectorCentrality<>(graph);
		f.evaluate();
	}
	
	
	@Override
	public double compute(V v) {
		return f.getVertexScore(v);
	}

}
