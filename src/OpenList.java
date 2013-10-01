import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

/*
 * Abstraction of the open list used by the search. Different search algorithms require different
 * collection structures. By using this list, the search supports A*, Breadth-first and Depth-first search.
 */
public class OpenList {
	private int searchType;
	private AbstractCollection<StateNode> open;
	public static final int A_STAR_SEARCH = 0;
	public static final int DEPTH_FIRST_SEARCH = 1;
	public static final int BREDTH_FIRST_SEARCH = 2;
	
	
	public OpenList(int searchType) {
		this.searchType = searchType;
		if (searchType == A_STAR_SEARCH) {
			open = new PriorityQueue<StateNode>(10, new QueueComparator());
		}
		else if (searchType == DEPTH_FIRST_SEARCH) {
			open = new Stack<StateNode>();
		}
		else if (searchType == BREDTH_FIRST_SEARCH) {
			open = new LinkedList<StateNode>();
		}
		else {
			throw new IllegalArgumentException("Search type not supported");
		}
		
	}
	
	public void add(StateNode node) {
		if (searchType == A_STAR_SEARCH) {
			((PriorityQueue<StateNode>)open).offer(node);
		}
		else if (searchType == DEPTH_FIRST_SEARCH) {
			((Stack<StateNode>)open).push(node);
		}
		else if (searchType == BREDTH_FIRST_SEARCH) {
			((LinkedList<StateNode>)open).add(node);
		}
	}
	
	public boolean remove(StateNode node) {
		return open.remove(node);
	}
	
	public StateNode pop() {
		if (searchType == A_STAR_SEARCH) {
			return ((PriorityQueue<StateNode>)open).poll();
		}
		else if (searchType == DEPTH_FIRST_SEARCH) {
			return ((Stack<StateNode>)open).pop();
		}
		else if (searchType == BREDTH_FIRST_SEARCH) {
			return ((LinkedList<StateNode>)open).poll();
		}
		return null;
	}
	
	public int size() {
		return open.size();
	}
	
	public boolean contains(StateNode node) {
		return open.contains(node);
	}

}
