package graphUtils;

import java.util.ArrayList;

public interface GiantComponentAnalyserInterface<V, E> {

	public double[] analyseGiantComponentDecompositionVertices(ArrayList<V> vertices);
	
	public int [] analyseGiantComponentDecompositionEdges(ArrayList<E> edges, int n);
}
