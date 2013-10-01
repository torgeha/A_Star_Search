import java.util.ArrayList;

/*
 * Unspecialized class used by search
 */
public class StateNode {
	
	public static final int INITIAL_ESTIMATE = -1;
	
	private String id;
	private State state;
	private StateNode parent;
	private ArrayList<StateNode> children = new ArrayList<StateNode>();
	private int h;
	private int g;
	private int f;
	
	private boolean status;
	
	//All StateNodes in the graph must have a state and a parent.
	//Heuristic and ID is calculated.
	public StateNode(State state, StateNode parent) {
		this.state = state;
		this.parent = parent;
		this.h = state.calculateHeuristic();
		this.g = INITIAL_ESTIMATE;
		f = Integer.MAX_VALUE;
		this.id = state.calculateId();
	}
	
	public boolean isSolution() {
		return state.isSolution();
	}
	
	public ArrayList<StateNode> getSuccessors() {
		return state.getSuccessors(this);
	}
	
	public void setParent(StateNode parent) {
		this.parent = parent;
	}

	public ArrayList<StateNode> getChildren() {
		return this.children;
	}

	public void addChild(StateNode child) {
		this.children.add(child);
	}
	
	public int getNrOfChildren() {
		return this.children.size();
	}

	//Heuristic function
	public int getH() {
		return h;
	}
	
	//Steps used
	public int getG() {
		return g;
	}
	
	//Every time g is set, f is recalculated
	public void setG(int g) {
		this.g = g;
		this.f = this.g + this.h;
	}

	public State getState() {
		return this.state;
	}

	public StateNode getParent() {
		return parent;
	}

	//Evaluation function
	public int getF() {
		return f;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int getPathCost() {
		return this.state.getPathCost();
	}
	
	public String toString() {
		String returnString = "";
		
		returnString = state.toString();
		
		return returnString;
	}
	

}
