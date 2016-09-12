package searchCustom;

import java.util.ArrayList;
import java.util.HashSet;

import searchShared.NodeQueue;
import searchShared.Problem;
import searchShared.SearchObject;
import searchShared.SearchNode;

import world.GridPos;

public class CustomGraphSearch implements SearchObject {

	private HashSet<SearchNode> explored;
	private NodeQueue frontier;
	protected ArrayList<SearchNode> path;
	private boolean insertFront;

	/**
	 * The constructor tells graph search whether it should insert nodes to front or back of the frontier 
	 */
    public CustomGraphSearch(boolean bInsertFront) {
		insertFront = bInsertFront;
    }

	/**
	 * Implements "graph search", which is the foundation of many search algorithms
	 */
	public ArrayList<SearchNode> search(Problem p) {
		// The frontier is a queue of expanded SearchNodes not processed yet
		frontier = new NodeQueue();
		/// The explored set is a set of nodes that have been processed 
		explored = new HashSet<SearchNode>();
		// The start state is given
		GridPos startState = (GridPos) p.getInitialState();
		// Initialize the frontier with the start state  
		frontier.addNodeToFront(new SearchNode(startState));

		// Path will be empty until we find the goal.
		path = new ArrayList<SearchNode>();
		
		// Implement this!
		//System.out.println("Implement CustomGraphSearch.java!");

		NodeQueue childQueue = new NodeQueue();

		if (frontier.isEmpty())
		    return path;
		
		while(!frontier.isEmpty()) {
		    SearchNode s = frontier.removeFirst();
		    GridPos g = s.getState();
		    
		    if (p.isGoalState(g)) {
			path = s.getPathFromRoot();
			return path;
		    }

		    explored.add(s);
		    ArrayList<GridPos> childStates = p.getReachableStatesFrom(g);

		    while (!childStates.isEmpty()) {
			SearchNode child = new SearchNode(childStates.get(0),s);
			
			if (!explored.contains(child) && !frontier.contains(child)) {

			    if (p.isGoalState(child.getState())){
				    path = child.getPathFromRoot();
				    return path;
			    }

			    if (insertFront)
				frontier.addNodeToFront(child);
			    else
				frontier.addNodeToBack(child);
			}
			childStates.remove(0);
			
		    }
		}
		return path;
	}

	/*
	 * Functions below are just getters used externally by the program 
	 */
	public ArrayList<SearchNode> getPath() {
		return path;
	}

	public ArrayList<SearchNode> getFrontierNodes() {
		return new ArrayList<SearchNode>(frontier.toList());
	}
	public ArrayList<SearchNode> getExploredNodes() {
		return new ArrayList<SearchNode>(explored);
	}
	public ArrayList<SearchNode> getAllExpandedNodes() {
		ArrayList<SearchNode> allNodes = new ArrayList<SearchNode>();
		allNodes.addAll(getFrontierNodes());
		allNodes.addAll(getExploredNodes());
		return allNodes;
	}

}