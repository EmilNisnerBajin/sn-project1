package graphUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import utils.GraphComparator;

public class ComponentClustererBFS<V,E> implements ComponentClustererInterface<V,E> {

	private UndirectedSparseGraph<V,E> graph;
	
	private HashSet<V> visited;
	
	private List<UndirectedSparseGraph<V,E>> components;
	
	
	public ComponentClustererBFS(UndirectedSparseGraph<V,E> graph) {
		if(graph==null || graph.getVertexCount()==0) {
			throw new IllegalArgumentException("Prazna Mreza");
		}
		this.graph = graph;
		components = new ArrayList<UndirectedSparseGraph<V,E>>();
		visited = new HashSet<>();
		identifyComponents();
	}
	
	@Override
	public int getComponentNumber() {
		return components.size();
	}

	@Override
	public List<UndirectedSparseGraph<V, E>> getComponents() {
		return components;
	}

	@Override
	public UndirectedSparseGraph<V, E> getGiantComponent() {
		components.sort(new GraphComparator<V,E> ());
		return components.get(0);
	}


	@Override
	public void identifyComponents() {
		Iterator<V> iterator = graph.getVertices().iterator();
		while(iterator.hasNext()) {
			V node = iterator.next();
			if(!visited.contains(node)) {
				identifyComponent(node);
			}
		}
	}
	
	private void identifyComponent(V node) {
		LinkedList<V> queue = new LinkedList<>();
		queue.add(node);
		visited.add(node);
		UndirectedSparseGraph<V,E> comp = new UndirectedSparseGraph<>();
		comp.addVertex(node);
		
		while(!queue.isEmpty()) {
			V cur = queue.removeFirst();
			Iterator<V> it = graph.getNeighbors(cur).iterator();
			while(it.hasNext()) {
				V neighbor = it.next();
				if(!visited.contains(neighbor)) {
					visited.add(neighbor);
					queue.addLast(neighbor);
					comp.addVertex(neighbor);
				}
				if(comp.findEdge(cur, neighbor)==null) {
					E link = graph.findEdge(cur, neighbor);
					comp.addEdge(link, cur, neighbor);
				}
			}
				
		}
		components.add(comp);
	}

}
