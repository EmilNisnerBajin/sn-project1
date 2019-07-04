package dataStruct;

public class Node {

	int id;
	
	public Node (int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o==null || o.getClass() != this.getClass()) {
			return false;
		}
		Node n2 = (Node) o;
		
		return this.id == n2.id;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return ""+this.getId();
	}
	
}
