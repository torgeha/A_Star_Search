import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;


public class Search {
	
	public static final StateNode FAIL = null;
	public static int expandedNodes = 0;
	
	//Can perform A*, depth-first and breadth-first search
	public static StateNode search(StateNode initialNode, int searchType) {
		//Creates a priorityQueue/Stack/Queue based on which search algorithm used
		//open: list of unexpanded nodes
		OpenList open = new OpenList(searchType);
		
		//All expanded nodes needs to be held track of in case a cheaper path is found
		HashMap<String, StateNode> closed = new HashMap<String, StateNode>();
		
		//Map to prevent duplicate states in the search tree
		HashMap<String, StateNode> allNodes = new HashMap<String, StateNode>();
		allNodes.put(initialNode.getId(), initialNode);
		
		initialNode.setG(0);
		open.add(initialNode);
		
		boolean finished = false;
		
		//If finished is true, the open list is empty and no solution is found.
		while(!finished) {
			if (open.size() == 0) {
				finished = true;
			}
			else {
				//The node with least f value is popped and ready for expansion
				StateNode currentNode = open.pop();
				closed.put(currentNode.getId(), currentNode);
				
				//check if currentNode is the solution before expanding
				if (currentNode.isSolution()) {
					expandedNodes = allNodes.size();
					return currentNode;
				}
				
				ArrayList<StateNode> successors = currentNode.getSuccessors();
				for (StateNode succ : successors) {
					//if the successor id is not unique, it has been generated before and 
					//succ shall point to the earlier generated state.
					//If unique, put in allNodes for future comparisons.
					if (allNodes.containsKey(succ.getId())) {
						succ = allNodes.get(succ.getId());
					}
					else {
						allNodes.put(succ.getId(), succ);
					}
					//The node is unique and can be added as a child
					currentNode.addChild(succ);
					
					//If not in closed or open, the successor is added to open list
					if (!closed.containsKey(succ.getId()) && !open.contains(succ)) {
						attachAndEvaluate(succ, currentNode, open);
						open.add(succ);
					}
					
					//If in either closed or open and there is a cheaper path from start to successor.
					//If the successor is in closed list, it probably has children, who needs to update their path cost
					else if ((currentNode.getG() + currentNode.getPathCost()) < succ.getG()) {
						attachAndEvaluate(succ, currentNode, open);
						if (closed.containsKey(succ.getId())) {
							propagatePathImprovements(succ, open);
						}
					}
				}
			}
		}
		return FAIL;
	}
	//If a node in closed list has been assigned a cheaper path, all children is recursively updated.
	private static void propagatePathImprovements(StateNode parent, OpenList open) {
		for (StateNode child : parent.getChildren()) {
			if ((parent.getG() + parent.getPathCost()) < child.getG()) {
				child.setParent(parent);
				child.setG(parent.getG() + parent.getPathCost());
				//update open list
				if (open.remove(child)) {
					open.add(child);
				}
				propagatePathImprovements(child, open);
			}
		}
	}
	//The child is assign a parent and updates path cost for the child.  
	private static void attachAndEvaluate(StateNode child, StateNode parent, OpenList open) {
		child.setParent(parent);
		child.setG(parent.getG() + parent.getPathCost());
		//re-heap open list
		if (open.remove(child)) {
			open.add(child);
		}
		//h is calculated when StateNode is initialized
	}
}
