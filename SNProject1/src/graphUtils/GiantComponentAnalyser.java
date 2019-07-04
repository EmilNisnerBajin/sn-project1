package graphUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graphUtils.ComponentClustererBFS;

public class GiantComponentAnalyser<V, E> implements GiantComponentAnalyserInterface<V, E> {

	private UndirectedSparseGraph<V,E> giantComponent;
	
	
	public GiantComponentAnalyser(UndirectedSparseGraph<V, E> giantComponent) {
		this.giantComponent = giantComponent;
	}
	
	public void setGiantComponent(UndirectedSparseGraph<V,E> newGiantComponent) {
		if(newGiantComponent == null) {
			return;
		}
		this.giantComponent = newGiantComponent;
	}

	@Override
	public double[] analyseGiantComponentDecompositionVertices(ArrayList<V> vertices) {
		Iterator<V> it = vertices.iterator();
		int numberOfVertex = giantComponent.getVertexCount();
		double [] result = new double [numberOfVertex];
		int i=0;
		
		UndirectedSparseGraph<V,E> newGiant;
		ComponentClustererBFS<V,E> pom;
		int vertexInGiantComponent;
		
		// Brisem cvorove iz gigantske komponente
		while(it.hasNext() && numberOfVertex>1) {
			numberOfVertex--;
			V curr = it.next();
			giantComponent.removeVertex(curr);
			pom = new ComponentClustererBFS<>(giantComponent);
			newGiant = pom.getGiantComponent();
			vertexInGiantComponent = newGiant.getVertexCount();
			double procenat = procenat(numberOfVertex, vertexInGiantComponent);
			result[i] = procenat;
			i++;
		}
		return result;
	}

	
	@Override
	// ostavlja se brojCvorovaUGrafu/n , ostali se uklanjaju
	public int[] analyseGiantComponentDecompositionEdges(ArrayList<E> edges, int n) {
		
		int start = giantComponent.getEdgeCount()/n;
		E e;

		for(int i=start; i<edges.size();i++) {
			e = edges.get(i);
			giantComponent.removeEdge(e);
		}
		
		ComponentClustererBFS<V,E> cc = new ComponentClustererBFS<>(giantComponent);
		int numberOfComponents = cc.getComponentNumber();
		List<UndirectedSparseGraph<V,E>> com =  cc.getComponents();
		int numOfSingletoneClusters=0;
		Iterator<UndirectedSparseGraph<V,E>> it = com.iterator();
		while(it.hasNext()) {
			UndirectedSparseGraph<V,E> c = it.next();
			if(c.getVertexCount()==1) {
				numOfSingletoneClusters ++;
			}
		}
		int numEdges = cc.getGiantComponent().getEdgeCount();
		int numVertex = cc.getGiantComponent().getVertexCount();
		
		int[] result = new int[4];
		
		result[0] = numberOfComponents;
		result[1] = numOfSingletoneClusters;
		result[2] = numEdges;
		result[3] = numVertex;
		
		return result;
	}
	
	// Brisem 90% linkova iz mreze
	public int[] analyseGiantComponentDecompositionEdges(ArrayList<E> edges) {
		return analyseGiantComponentDecompositionEdges(edges,10);
	}
	
	// pomocni metod za racunaje procenta
		private double procenat(int NoOfVertex, int NoOfVertexInGiantComponent) {
			double p =  (((double)NoOfVertexInGiantComponent*100.0) / (double)NoOfVertex);
			return p;
		}

}
