import java.util.ArrayList;

/*
 * Other puzzles needs to implement this interface for the search to work correctly
 */
public interface State {
	public ArrayList<StateNode> getSuccessors(StateNode parent);
	public int calculateHeuristic();
	public String calculateId();
	public boolean isSolution();
	public String toString();
	public int getPathCost();
}