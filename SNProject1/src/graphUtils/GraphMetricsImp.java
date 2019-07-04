package graphUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import functions.BetweennessCentralityFunction;
import functions.ClosenessCentralityFunction;
import functions.EigenvectorCentralityFunction;
import functions.Function;
import functions.NodeDegreeFunction;

public class GraphMetricsImp<V,E>implements GraphMetricsInterface<V, E> {
	
	private UndirectedSparseGraph<V,E> graph;
	
	private PearsonsCorrelation pc;
	private SpearmansCorrelation sc;
	
	private BetweennessCentralityFunction<V,E> bc;
	private ClosenessCentralityFunction<V,E> cc;
	private EigenvectorCentralityFunction<V,E> ec;
	private NodeDegreeFunction<V,E> df;
	
	
	public GraphMetricsImp(UndirectedSparseGraph<V,E> graph) {
		this.graph = graph;
		this.pc = new PearsonsCorrelation();
		this.sc = new SpearmansCorrelation();
		this.bc = new BetweennessCentralityFunction<V,E>(graph);
		this.cc = new ClosenessCentralityFunction<V,E>(graph);
		this.ec = new EigenvectorCentralityFunction<V,E>(graph);
		this.df = new NodeDegreeFunction<V,E>(graph);
	}
	
	public void setNewGraph(UndirectedSparseGraph<V,E> graph) {
		this.graph=graph;
		this.bc = new BetweennessCentralityFunction<V,E>(graph);
		this.cc = new ClosenessCentralityFunction<V,E>(graph);
		this.ec = new EigenvectorCentralityFunction<V,E>(graph);
		this.df = new NodeDegreeFunction<V,E>(graph);
		
	}

	// Metod izracunava zeljenu korelaciju na osnovu zeljene metrike
	// U slucaju da se unese ne validna opcija za metriku metod vraca
	// NaN.
	// opt = 1 : Racunanje korelacije za BetweennessCentrality metriku
	// opt = 2 : Racunanje korelacije za ClosenessCentrality metriku
	// opt = 3 : Racunanje korelacije za EigenvectorCentrality metriku
	// opt = 4 : Racunanje korelacije za NodeDegreeCentrality metriku
	@Override
	public double computeCorrelation(int opt, boolean type) {
		switch(opt) {
			case 1: return computeCorrelation(bc,type);
			case 2: return computeCorrelation(cc,type);
			case 3: return computeCorrelation(ec,type);
			case 4: return computeCorrelation(df,type);
			default: return java.lang.Double.NaN;
		}
	}
	
	// Vraca niz sortiranig linkova po Betweenness metrici Centralnosti
	public ArrayList<E> getSortedEdgesByBetweennessCentralityFunction(){
		ArrayList<E> result = new ArrayList<>(graph.getEdgeCount());
		result.addAll(graph.getEdges());
		
		result.sort(new Comparator<E>() {
			@Override
			public int compare(E e1, E e2) {
				return (int) (bc.computeEdge(e2) - bc.computeEdge(e1));
			}
		});
		
		return result;
	}
	
	// Racunanje odredjene korelacije na osnovu prosledjene metrike
	private double computeCorrelation(Function<V> function, boolean type) {
		int i=0;
		double [] x = new double[graph.getEdgeCount()*2];
		double [] y = new double[graph.getEdgeCount()*2];
		
		Iterator<E> it = graph.getEdges().iterator();
		double value1, value2;
		Pair<V> pair;
		E edge;
		
		while(it.hasNext()) {
			edge = it.next();
			pair = graph.getEndpoints(edge);
			
			value1 = function.compute(pair.getFirst());
			value2 = function.compute(pair.getSecond());
			
			x[i] = value1;
			y[i] = value2;
			i++;
			
			x[i] = value2;
			y[i] = value1;
			i++;
		}
		
		if(type) {
			return pc.correlation(x, y);
		}else {
			return sc.correlation(x, y);
		}
	}

	public ArrayList<V> getSortedVerticesByEigenvectorCentrality(){
		return getSortedVertices(ec);
	}
	
	public ArrayList<V> getSortedVerticesByClosenessCentrality(){
		return getSortedVertices(cc);
	}
	
	public ArrayList<V> getSortedVerticesByNodeDegreeCentrality(){
		return getSortedVertices(df);
	}
	
	public ArrayList<V> getSortedVerticesByBetweennessCentrality(){
		return getSortedVertices(bc);
	}
	
	private ArrayList<V> getSortedVertices(Function<V> function){
		return getSortedVertices(function, graph);
	}
	
	private ArrayList<V> getSortedVertices(Function<V> function, UndirectedSparseGraph<V, E> graph) {
		ArrayList<V> result = new ArrayList<>(graph.getVertexCount());
		Iterator<V> it = graph.getVertices().iterator();
		V curr;
		
		while(it.hasNext()) {
			curr = it.next();
			result.add(curr);
		}
		result.sort(new Comparator<V>() {
			@Override
			public int compare(V arg0, V arg1) {
				return (int) (function.compute(arg1)-function.compute(arg0));
			}
		});
		
		return result;
	}

}
