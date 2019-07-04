package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import dataStruct.Link;
import dataStruct.Node;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class GraphLoader {
	
	
	public UndirectedSparseGraph<Node, Link> load(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		UndirectedSparseGraph<Node,Link> graph = new UndirectedSparseGraph<>();
		String line = br.readLine();
		HashMap<Integer,Node> added =  new HashMap<>();
		while(line!=null) {
			String[] tokens = line.split("\\s+");
			int id1 = Integer.parseInt(tokens[0]);
			int id2 = Integer.parseInt(tokens[1]);
			Node n1;
			Node n2;
			
			// Da li cvor sa id1 vec postoji u grafu?
			if(added.containsKey(id1)) {
				n1 = added.get(id1);
			} else {
				n1 = new Node(id1);
				added.put(n1.getId(), n1);
			}
			
			// Da li cvor sa id2 vec postoji u grafu?
			if(added.containsKey(id2)) {
				n2 = added.get(id2);
			} else {
				n2 = new Node(id2);
				added.put(n2.getId(), n2);
			}
			Link l = new Link();
			graph.addEdge(l, n1, n2);
			line = br.readLine();
		}
		if(br!=null) {
			br.close();
		}
		return graph;
	}
	
	// Average Node Degree
	public double averageNodeDegree(UndirectedSparseGraph<Node,Link> graph) {
		int sum = 0;
		Iterator<Node> it = graph.getVertices().iterator();
		while(it.hasNext()) {
			Node n = it.next();
			sum+= graph.degree(n);
		}
		return sum/graph.getVertexCount();
	}
	
	
	
}
