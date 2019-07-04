package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import dataStruct.Link;
import dataStruct.Node;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graphUtils.ComponentClustererBFS;
import graphUtils.GiantComponentAnalyser;
import graphUtils.GraphMetricsImp;
import utils.FileUtil;
import utils.GraphLoader;

public class GlavniProgram {

	// Metod koji kreira kopiju prosledjenog grafa
	public static UndirectedSparseGraph<Node,Link> createCopy(UndirectedSparseGraph<Node,Link> graph){
		
		UndirectedSparseGraph<Node,Link> copy = new UndirectedSparseGraph<>();
		Iterator<Node> it = graph.getVertices().iterator();
		HashMap<Integer,Node> added =  new HashMap<>();
		while(it.hasNext()) {
			Node n = it.next();
			Node novi = new Node(n.getId());
			copy.addVertex(novi);
			added.put(novi.getId(), novi);
		}
		Iterator<Link> eit = graph.getEdges().iterator();
		while(eit.hasNext()) {
			Link l = eit.next();
			Pair<Node> pair = graph.getEndpoints(l);
			Link novi = new Link();
			Node first = added.get(pair.getFirst().getId());
			Node second = added.get(pair.getSecond().getId());
			copy.addEdge(novi, first, second);
		}
		return copy;	
	}

	public static void main(String[] args) throws IOException {
		String fileName = "src/CA-GrQc.txt";
		GraphLoader gl = new GraphLoader();
		UndirectedSparseGraph<Node,Link> graph = gl.load(fileName);
		
		System.out.println("Ucitani graf");
		System.out.println("Number of nodes: "+graph.getVertexCount());
		System.out.println("Number of edges: "+graph.getEdgeCount());
		System.out.println();
		
		ComponentClustererBFS<Node,Link> cc = new ComponentClustererBFS<>(graph);
		
		// Gigantska komponenta
		System.out.println("Giant Component");
		UndirectedSparseGraph<Node,Link> giantComponent = cc.getGiantComponent();
		System.out.println("Number of nodes: "+giantComponent.getVertexCount());
		System.out.println("Number of edges: "+giantComponent.getEdgeCount());
		System.out.println();
		
		GraphMetricsImp<Node,Link> graphMetrics = new GraphMetricsImp<>(giantComponent);
		
		// F1: Izracunavanje Pearsonovog i Spearmansovog koeficijenta
		
		System.out.println("Stage F1");
		System.out.println();
		
		System.out.println("******************F1 RESULTS**************************");
		System.out.println();

		
		System.out.println("Parsons correlation");
		System.out.printf("%-30s:%1.20f\n", "Stepen Cvora",graphMetrics.computeCorrelation(4, true));
//		System.out.printf("%-30s:%0.20f\n", "Closeness Centralnost cvora",graphMetrics.computeCorrelation(2, true));
		System.out.printf("%-30s:%1.20f\n", "Betweenness Centralnosti cvora",graphMetrics.computeCorrelation(1, true));
		System.out.printf("%-30s:%1.20f\n", "Eigenvector centralnosti cvora",graphMetrics.computeCorrelation(3, true));
		
		System.out.println();
		System.out.println("Spearsons correlation");
		System.out.printf("%-30s:%1.20f\n", "Stepen Cvora",graphMetrics.computeCorrelation(4, false));
//		System.out.printf("%-30s:%0.20f\n", "Closeness Centralnost cvora",graphMetrics.computeCorrelation(2, false));
		System.out.printf("%-30s:%1.20f\n", "Betweenness Centralnosti cvora",graphMetrics.computeCorrelation(1, false));
		System.out.printf("%-30s:%1.20f\n", "Eigenvector centralnosti cvora",graphMetrics.computeCorrelation(3, false));
		System.out.println();
		
		System.out.println("Stage F1 - FINISHED");
		System.out.println();
		
		
		// F2: Analiza raspada gigantske komponente
		
		System.out.println("Stage F2");
		
		GiantComponentAnalyser<Node,Link> analyser;
		UndirectedSparseGraph<Node,Link> giantComponentCopy;
		
		ArrayList<Node> listaDegree = graphMetrics.getSortedVerticesByNodeDegreeCentrality();
		ArrayList<Node> listaBetweenness = graphMetrics.getSortedVerticesByBetweennessCentrality();
//		ArrayList<Node> listaCloseness = graphMetrics.getSortedVerticesByClosenessCentrality();
		ArrayList<Node> listaEigenvector = graphMetrics.getSortedVerticesByEigenvectorCentrality();
		
		giantComponentCopy = createCopy(giantComponent);
		analyser = new GiantComponentAnalyser<>(giantComponentCopy);
		double[] nizStepeni = analyser.analyseGiantComponentDecompositionVertices(listaDegree);
				
		giantComponentCopy= createCopy(giantComponent);
		analyser.setGiantComponent(giantComponentCopy);
		double[] nizBetweenness = analyser.analyseGiantComponentDecompositionVertices(listaBetweenness);
				
		giantComponentCopy= createCopy(giantComponent);
		analyser.setGiantComponent(giantComponentCopy);
		double[] nizEigenvector = analyser.analyseGiantComponentDecompositionVertices(listaEigenvector);
				
//		giantComponentCopy= createCopy(giantComponent);
//		analyser.setGiantComponent(giantComponentCopy);
//		double[] nizCloseness = analyser.analyseGiantComponentDecompositionVertices(listaCloseness);
				
		double[] dummy = new double[nizStepeni.length];
		Arrays.fill(dummy, 0);
		
//		FileUtil.createCSVFile(listaStepeni, listaBetweenness, listaCloseness, listaEigenvector);
		
		FileUtil.createCSVFile(nizStepeni, nizBetweenness, dummy, nizEigenvector);
		
		System.out.println();
		System.out.println("Stage F2 - FINISHED");
		System.out.println();
		
		// F3 Analiza podmreze
		
		System.out.println("Stage F3");
		
		
		giantComponentCopy= createCopy(giantComponent);
		graphMetrics.setNewGraph(giantComponentCopy);
		analyser = new GiantComponentAnalyser<>(giantComponentCopy); // to remove after test
		analyser.setGiantComponent(giantComponentCopy);
		
		
		ArrayList<Link> sortedEdges = graphMetrics.getSortedEdgesByBetweennessCentralityFunction();
		int [] result = analyser.analyseGiantComponentDecompositionEdges(sortedEdges);
		
		System.out.println("******************F3 RESULTS**************************");
		System.out.printf("%-33s: %5d\n","Number of Components",result[0]);
		System.out.printf("%-33s: %5d\n","Number of Singletone Clusters",result[1]);
		System.out.printf("%-33s: %5d\n","Edge number in giant component",result[2]);
		System.out.printf("%-33s: %5d\n","Vertex number in giant component",result[3]);
		
		System.out.println("Stage F3 - FINISHED");
		System.out.println("Program finished");
		System.exit(0);
	}

}
