package tddc17;


import aima.core.environment.liuvacuum.*;
import aima.core.agent.Action;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List; 
import java.util.Queue;
import java.util.Vector; 
import java.util.Iterator; 

import java.util.Random;



enum Direction {
    EAST {
	@Override
	    public Direction dec() {
	    return values()[3]; 
	}
    }, 

    SOUTH, 

    WEST, 

    NORTH {
	@Override
	    public Direction inc() {
	    return values()[0]; // see below for options for this line
	}
    };

    public Direction inc() {
	return values()[ordinal() + 1];
    }

    public Direction dec() {

	return values()[ordinal() - 1];
    }
}


class MyAgentState
{
	public int[][] world = new int[30][30];
	public int initialized = 0;
	final int UNKNOWN 	= 0;
	final int WALL 		= 1;
	final int CLEAR 	= 2;
	final int DIRT		= 3;
	final int HOME		= 4;
	final int ACTION_NONE 			= 0;
	final int ACTION_MOVE_FORWARD 	= 1;
	final int ACTION_TURN_RIGHT 	= 2;
	final int ACTION_TURN_LEFT 		= 3;
	final int ACTION_SUCK	 		= 4;
	
	public int agent_x_position = 1;
	public int agent_y_position = 1;
	public int agent_last_action = ACTION_NONE;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public int agent_direction = EAST;
	
	MyAgentState()
	{
		for (int i=0; i < world.length; i++)
			for (int j=0; j < world[i].length ; j++)
				world[i][j] = UNKNOWN;
		world[1][1] = HOME;
		agent_last_action = ACTION_NONE;
	}
	// Based on the last action and the received percept updates the x & y agent position
	public void updatePosition(DynamicPercept p)
	{
		Boolean bump = (Boolean)p.getAttribute("bump");

		if (agent_last_action==ACTION_MOVE_FORWARD && !bump)
	    {
			switch (agent_direction) {
			case MyAgentState.NORTH:
				agent_y_position--;
				break;
			case MyAgentState.EAST:
				agent_x_position++;
				break;
			case MyAgentState.SOUTH:
				agent_y_position++;
				break;
			case MyAgentState.WEST:
				agent_x_position--;
				break;
			}
	    }
		
	}

  
	
	public void updateWorld(int x_position, int y_position, int info)
	{
		world[x_position][y_position] = info;
	}
	
	public void printWorldDebug()
	{
		for (int i=0; i < world.length; i++)
		{
			for (int j=0; j < world[i].length ; j++)
			{
				if (world[j][i]==UNKNOWN)
					System.out.print(" ? ");
				if (world[j][i]==WALL)
					System.out.print(" # ");
				if (world[j][i]==CLEAR)
					System.out.print(" . ");
				if (world[j][i]==DIRT)
					System.out.print(" D ");
				if (world[j][i]==HOME)
					System.out.print(" H ");
			}
			System.out.println("");
		}
	}
}

class MyAgentProgram implements AgentProgram {

    private int initnialRandomActions = 10;
    private Random random_generator = new Random();
	
    // Here you can define your variables!
    public int iterationCounter = 450;
    public MyAgentState state = new MyAgentState();

    public int phase = 0; 

    public int[] cl = {0,0};
    public int[] cp = {0,0};  
    public List<Node> nodelist = new Vector<Node>();
    public List<Node> goal; 



    public class Node {
	Node(int x, int y, Node parent) {
	    x_ = x;
	    y_ = y; 
	    pathParent = parent; 

	    nodelist.add(this);  
	    int[] co = {x,y}; 


	}


	public void generateChildren()
	{
	    
	    if(pathParent != null) {
		if(state.world[x_][y_-1] != 1 
		   && !existsC(x_, y_-1, nodelist)){
		    		    
		    neighbors.add(new Node(x_, y_-1, this)); 
		}
	
		if(state.world[x_][y_+1] != 1 
		   && !existsC(x_, y_+1, nodelist)){
		    
		    neighbors.add(new Node(x_, y_+1, this)); 
		}
	
		if(state.world[x_-1][y_] != 1 
		   && !existsC(x_-1, y_, nodelist)){

		    neighbors.add(new Node(x_-1, y_, this)); 
		}
	
		if(state.world[x_+1][y_] != 1 
		   && !existsC(x_+1, y_, nodelist)){

		    neighbors.add(new Node(x_+1, y_, this)); 
		}
	    }
	    else{
		if(state.world[x_][y_-1] != 1){

		    neighbors.add(new Node(x_, y_-1, this)); 
		}
		if(state.world[x_][y_+1] != 1){

		    neighbors.add(new Node(x_, y_+1, this));
		}
		if(state.world[x_-1][y_] != 1){

		    neighbors.add(new Node(x_-1, y_, this)); 
		}
		if(state.world[x_+1][y_] != 1){

		    neighbors.add(new Node(x_+1, y_, this)); 
		}
	    }

	}
  
	int x_, y_; 
	Vector neighbors = new Vector(4);
	Node pathParent;

    };



    public boolean nodeEqual(Node a, Node b){
	if(a.x_ == b.x_ && a.y_ == b.y_)
	    return true;
	return false; 
    }
    
    public boolean cordEqual(Node a, int x, int y){
	if(a.x_ == x && a.y_ == y)
	    return true;
	return false; 
    }

    // this function takes a list to see if there is a node in it with the same coordinates as the parameter node a, 
    // treating nodes with same coordinates but different parent pointers as equal
    public boolean exists(Node a, List<Node> list){
	for(Node i : list)
	    {
		if ( nodeEqual(a, i) )
		    return true; 
	    }
	return false; 
    }

    //checks a list for nodes which has the coordinates x y
    public boolean existsC(int x, int y, List<Node> list){
	for(Node i : list)
	    {
		if ( cordEqual(i, x, y) )
		    return true; 
	    }
	return false; 
    }

    public Boolean[] bumpseq = new Boolean[4];
    public int[] actions = new int[10]; // 1 - move, 2 - left, 3 - right, 4 - succ


    public List constructPath(Node node) {
	LinkedList path = new LinkedList();
	while (node.pathParent != null) {
	    path.addFirst(node);
	    node = node.pathParent;
	}
	return path;
    }

  
    public int directionToFrom(int x, int y, int x2, int y2)
    {
	if(x2<x)
	    return 1;
	if(x2>x)
	    return 3;
	if(y2>y)
	    return 0;
	if(y2<y)
	    return 2;
	return -1; 
    }


    // depending on the first node of l, determines the correct action to take 
    public int pathToDestiny(List<Node> l){

	Iterator i = l.iterator();

	Node n;
	if(i.hasNext())
	    n = (Node)i.next(); 
	else 
	    return 0; 


	int dir = directionToFrom(n.x_, n.y_, state.agent_x_position, state.agent_y_position);
	
	// do we need to turn? 
	if ( state.agent_direction != dir ){
	    // never rotate in a quarter circle
	    if(dir - state.agent_direction == 1 || dir - state.agent_direction == -3) 
		return 2;
	    else
		return 3;
	}
	else {
	    // we don't need to turn, so move forward
	    return 1; 
	}

    }





    private void updateAll(DynamicPercept percept, int action){
	//state.updatePosition(percept);
	if(action==0) {
	    state.agent_direction = ((state.agent_direction-1) % 4);
	    if (state.agent_direction<0) 
		state.agent_direction +=4;
	    state.agent_last_action = state.ACTION_TURN_LEFT;
	    return;// LIUVacuumEnvironment.ACTION_TURN_LEFT;
	} else if (action==1) {
	    state.agent_direction = ((state.agent_direction+1) % 4);
	    state.agent_last_action = state.ACTION_TURN_RIGHT;
	    return;// LIUVacuumEnvironment.ACTION_TURN_RIGHT;
	} 
	state.agent_last_action=state.ACTION_MOVE_FORWARD;
	return;// LIUVacuumEnvironment.ACTION_MOVE_FORWARD;

    }

    public int findDirection(int[] parent, int[] child){
	if(parent[1]-child[1]>0){ //norr
	    return 0; 
	}
	if(parent[1]-child[1]<0){ //syd
	    return 2; 
	}
	if(parent[0]-child[0]<0){
	    return 1; 
	}
	if(parent[0]-child[0]>0){
	    return 3; 
	}

	return -1; 
    }




    public List findClosestUnknown(Node startNode){
	
	Queue frontier = new LinkedList();
	LinkedList explored = new LinkedList();
	nodelist.clear(); 

	int[] temp = {startNode.x_, startNode.y_}; 

	frontier.add(startNode);



	

	while (!frontier.isEmpty()){
	    Node node = (Node)frontier.remove(); 


	    if (state.world[node.x_][node.y_] == 0) {
		// path found!

		return constructPath(node);

	    }
	    else {
		explored.add(node);
      
		// add neighbors to the open list
		 node.generateChildren(); 

		if ( !node.neighbors.isEmpty() ) {

		    Iterator i = node.neighbors.iterator();
		    while (i.hasNext()) {
			Node neighborNode = (Node)i.next();
			if (!explored.contains(neighborNode) &&
			    !frontier.contains(neighborNode)) 
			    {
				neighborNode.pathParent = node;
				frontier.add(neighborNode);
			    }
		    }
		}
	    }
	}
  
	// no path found
	return null;
    }


   
   

	
    // moves the Agent to a random start position
    // uses percepts to update the Agent position - only the position, other percepts are ignored
	// returns a random action
	private Action moveToRandomStartPosition(DynamicPercept percept) {
		int action = random_generator.nextInt(6);
		initnialRandomActions--;
		state.updatePosition(percept);
		if(action==0) {
		    state.agent_direction = ((state.agent_direction-1) % 4);
		    if (state.agent_direction<0) 
		    	state.agent_direction +=4;
		    state.agent_last_action = state.ACTION_TURN_LEFT;
			return LIUVacuumEnvironment.ACTION_TURN_LEFT;
		} else if (action==1) {
			state.agent_direction = ((state.agent_direction+1) % 4);
		    state.agent_last_action = state.ACTION_TURN_RIGHT;
		    return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
		} 
		state.agent_last_action=state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}
	
	
	@Override
	public Action execute(Percept percept) {
		
		// DO NOT REMOVE this if condition!!!
    	if (initnialRandomActions>0) {
    		return moveToRandomStartPosition((DynamicPercept) percept);
    	} else if (initnialRandomActions==0) {
    		// process percept for the last step of the initial random actions
    		initnialRandomActions--;
    		state.updatePosition((DynamicPercept) percept);
			System.out.println("Processing percepts after the last execution of moveToRandomStartPosition()");
			state.agent_last_action=state.ACTION_SUCK;
	    	return LIUVacuumEnvironment.ACTION_SUCK;
    	}
		
    	// This example agent program will update the internal agent state while only moving forward.
    	// START HERE - code below should be modified! 	    	
    	System.out.println("x=" + state.agent_x_position);
    	System.out.println("y=" + state.agent_y_position);
    	System.out.println("dir=" + state.agent_direction);
    	
		
	    iterationCounter--;
	    
	    if (iterationCounter==0)
	    	return NoOpAction.NO_OP;

	    DynamicPercept p = (DynamicPercept) percept;

	

		if(actions[0] != 4){ //the succ doesn't affect pathfinding
		    for( int i = 3; i >= 0; --i )
			{
			    if ( i > 0 ){
				bumpseq[i] = bumpseq[i-1]; 
			    }
			}
		    for( int i = 9; i >= 0; --i )
			{
			    if ( i > 0 ){
				actions[i] = actions[i-1]; 
			    }
			}
		}

		
		bumpseq[0] = (Boolean)p.getAttribute("bump");
		Boolean dirt = (Boolean)p.getAttribute("dirt");
		Boolean home = (Boolean)p.getAttribute("home");



		// State update based on the percept value and the last action
		state.updatePosition((DynamicPercept)percept);
		if (bumpseq[0]) {
		    switch (state.agent_direction) {
		    case MyAgentState.NORTH:
			state.updateWorld(state.agent_x_position,state.agent_y_position-1,state.WALL);
			break;
		    case MyAgentState.EAST:
			state.updateWorld(state.agent_x_position+1,state.agent_y_position,state.WALL);
			break;
		    case MyAgentState.SOUTH:
			state.updateWorld(state.agent_x_position,state.agent_y_position+1,state.WALL);
			break;
		    case MyAgentState.WEST:
			state.updateWorld(state.agent_x_position-1,state.agent_y_position,state.WALL);
			break;
		    }
		}
		if (dirt)
		    state.updateWorld(state.agent_x_position,state.agent_y_position,state.DIRT);
		else
		    state.updateWorld(state.agent_x_position,state.agent_y_position,state.CLEAR);
	    
		state.printWorldDebug();
	    
	    
		// Next action selection based on the percept value
		if (dirt.booleanValue()) {
		    //there is no reason to not suck dirt if it's possible
		    System.out.println("DIRT -> choosing SUCK action!");
		    state.agent_last_action=state.ACTION_SUCK;
		    return LIUVacuumEnvironment.ACTION_SUCK;
		}else{

		    switch (phase) {
		    case 0: if(!bumpseq[0]){ //find a wall and turn left
			    actions[0] = 1; 
			    updateAll(p, 2); 
			    return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
			}
			else{
			    System.out.println("turn left");
			    actions[0] = 2; 
			    phase++; 
			    //we hit a wall, so next step is to trace along the wall for 1 lap
			    //phase denotes a change in strategy. 

			    //remember these to know where we begun tracing (because this is where we stop)
			    cp[0] = state.agent_x_position; 
			    cp[1] = state.agent_y_position; 

			    updateAll(p, 0); 
			    return LIUVacuumEnvironment.ACTION_TURN_LEFT;
			}




		    case 1: 
			//this if statements is true if we moved into our checkpoint, so next phase
			if(cp[0] == state.agent_x_position && cp[1] == state.agent_y_position && actions[1] == 1 && !bumpseq[0]){
			    phase = 2; 
			    updateAll(p, 0); 
			    return LIUVacuumEnvironment.ACTION_TURN_LEFT;
			}

			//we bumped, then turned away from the wall, so move
			if(!bumpseq[0] && bumpseq[1]){ 
			    actions[0] = 1; 
			    updateAll(p, 2); 
			    return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
			}

			//we moved and did not bump, so reface the wall, to make sure the wall is not curving away from us
			if(!bumpseq[0] && actions[1] == 1){
			    actions[0] = 3; 
			    updateAll(p, 1); 
			    return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
			}

			//we bumped, so turn away from the wall
			if(bumpseq[0]){

			    actions[0] = 2;  
			    updateAll(p, 0); 
			    return LIUVacuumEnvironment.ACTION_TURN_LEFT;
			}

			actions[0] = 1; 
			updateAll(p, 2); 
			return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
		    case 2:


			Node here = new Node(state.agent_x_position, state.agent_y_position, null); 
			
			//findClosestUnknown will return null if no unknown is found, otherwise it will return a list of nodes, 
			//where the bottom of that list is an unknown node, and every list is pointing towards a walkable 
			//neighbour, up to the node we're on
			goal = findClosestUnknown(here); 
			if(goal == null){
			    System.out.println("apartment has been cleaned up, my dudes");
			    break; 
			    
			}
			else {

			    //phase 3 is about converting a list of walkable nodes, to the actions required to walk along those
			    phase = 3;
			}

		    case 3:
			if(goal != null)
			    {
	
				int action = pathToDestiny(goal); 
				switch (action){
				case 1:
				    // if we move forward, remove the first node of the goal list (since it's traversed) 
				    goal.remove(0); 
				    updateAll(p, 2); 
				    if(goal.isEmpty())
					phase = 2; 
				    return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
				case 2:
				    updateAll(p, 1); 
				    return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
				case 3: 
				    updateAll(p, 0); 
				    return LIUVacuumEnvironment.ACTION_TURN_LEFT;
				}
			
			    }
			else 
			    {
				phase = 2; 
				updateAll(p, 0);
				return LIUVacuumEnvironment.ACTION_TURN_LEFT;
			    }


			break; 
		    }
		    return NoOpAction.NO_OP;
		}

		
	};
}



public class MyVacuumAgent extends AbstractAgent {
    public MyVacuumAgent() {
    	super(new MyAgentProgram());{
	}
    }
}

