package graphUtils;

import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public interface ComponentClustererInterface<V,E> {

	
	public int getComponentNumber();
	
	public List<UndirectedSparseGraph<V,E>> getComponents();
	
	public UndirectedSparseGraph<V,E> getGiantComponent();
	
	public void identifyComponents();
}
