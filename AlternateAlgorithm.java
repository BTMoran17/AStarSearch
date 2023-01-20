import java.util.ArrayList;
import java.util.List;


public class AlternateAlgorithm {
	GridGenerator grid;
	DrawGrid scene;
	
	private boolean pathFound = false;
	private int length = 0;
	private int checks = 0;
	
	
	
	//Default Constructor
	//For testing and demonstrating purposes
	public AlternateAlgorithm() {
		grid = new GridGenerator();
		scene = new DrawGrid();
	}

	//Perform Algorithm on particular Grid
	public AlternateAlgorithm(GridGenerator a, DrawGrid b) {
		grid = a;
		scene = b;
		
	}
public void reset() {
	this.pathFound = false;
	this.length = 0;
	this.checks = 0;
	this.grid.uncheck();
	
	for(int x = 0; x < 160; x++) {	//PAINT EACH NODE IN THE GRID
		for(int y = 0; y < 120; y++) {
			grid.Grid[x][y].cost = -1;
			grid.Grid[x][y].jumps = -1;
		}
	}
}
public void AStar() {
		

		int[] start;
		int[] end;
		
		start = grid.start;
		end = grid.end;
		
		pathFound = true;
		
		ArrayList<Node> fringe = new ArrayList<Node>();
		fringe.add(grid.Grid[start[0]][start[1]]);
		
		
		while(pathFound) {
			
			if(fringe.size() <= 0) {
				pathFound = false;
				break;
			}
			
			//Cost allows the nodes to be compared with the different types and associated math
			double cost = fringe.get(0).cost+1;
			
			//used STRICKTLY backtracking in the AStar algorithm
			int jumps = fringe.get(0).jumps+1;
			
			//List of nodes that have already been checked by the explore()
			ArrayList<Node> explored = exploreNeighborsW(fringe.get(0),jumps,cost);
			
			//Pop() all items from the list and explore() all of their possible paths in order of cost
			if(explored.size() >  0) { 
				
				
				fringe.remove(0);
				fringe.addAll(explored);
				
				//LIVE UPDATE FOR MAP
				scene.updateMap();
				
				
			} else {
				fringe.remove(0);
			}
			sortQueW(fringe); //Sort the list in order by cost
		}
	}
	
	//Sort the array list by cost of the nodes
	public ArrayList<Node> sortQueW(ArrayList<Node> sort) {	
		
		//ending nodes of the map
		int[] end;
		
		//Set the end so to calculate distance
		end = grid.end;
		
		int cur = 0;
		while(cur < sort.size()) {
			int little = cur;
			for(int k = cur+1; k < sort.size(); k++) {
				
				if(sort.get(k).getEuclidDist(end[0],end[1])+sort.get(k).cost < sort.get(little).getEuclidDist(end[0],end[1])+sort.get(little).cost){
					little = k;
				}
			}
			if(cur != little) {
				Node temp = sort.get(cur);
				sort.set(cur, sort.get(little));
				sort.set(little, temp);
			}	
			cur++;
		}
		return sort;
	}
	
	//Explore the 8 surrounding blocks for possible canidates
	public ArrayList<Node> exploreNeighborsW(Node current, int jumps , double cost) {
		
		//list of all explored nighbors
		ArrayList<Node> explored = new ArrayList<Node>();
		
		for(int a = -1; a <= 1; a++) {
			//row
			for(int b = -1; b <= 1; b++) {
				//column
				
				int x = current.getX()+a;
				int y = current.getY()+b;
				if((x > -1 && x < 160) && (y > -1 && y < 120)) {	
					//Assures that the node is going to be inside of the Map()
					Node neighbor = grid.Grid[x][y];
					
					//if never been touched or if its number of jumps are more
					if((neighbor.jumps ==-1 || neighbor.jumps > jumps) && neighbor.getType()!= '0') {
						
						//Explore the node itself
						exploreW(neighbor, current.getX(), current.getY(), jumps , cost);
						
						//Add node to the explored list
						explored.add(neighbor);
					}
				}
			}
		}
		return explored;
	}
	
	public void exploreW(Node current, int lastx, int lasty, int jumps , double cost) {	//EXPLORE A NODE
		
		if(current.getType()!= 's' && current.getType() != 'e') {	//CHECK THAT THE NODE IS NOT THE START OR FINISH
			current.setCheckedA();
		}
		//set the prevous x and y values for traceback purposes for final path
		current.setLastNode(lastx, lasty);
		
		//set jumps its taken scince the begining
		current.jumps = jumps;
		
		//add the cost from the total of the nodes before it as it builds towards the end
		current.cost += cost(grid.Grid[lastx][lasty],current);
		
		//iterator
		checks++;
		
		//If node is the final ending point then backtrace the shortest cost to find the finalPath
		if(current.getType() == 'e') {	
			//backtrack so the algortihm can print the path
			
			
			backtrackW(current.parent_x, current.parent_y, jumps);
		}
	}
	
	//Goes backwards through the prevous nodes until the start is found
	public void backtrackW(int lx, int ly, int count) {
		
		//iterator
		length = count;
		double cprint = 0;

		//while the number of blocks in the path is not 0 OR the start has been found
		while(count >= 1) {

			//cur itterator
			Node current = grid.Grid[lx][ly];
			
			cprint += current.cost;
			
			current.setFinalPathA();
			grid.Grid[current.x][current.y].setFinalPathA();
			
			//iterate backwards through the node path
			lx = current.parent_x;
			ly = current.parent_y;
			
			
			count--;
		}
		pathFound = false;
		
		System.out.println("A* jumps: " + length + "  A* cost: " + cprint);
	}
	
public void WAStar(double w) {
		
	
		int[] start;
		int[] end;
		
		start = grid.start;
		end = grid.end;
		
		pathFound = true;
		
		ArrayList<Node> fringe = new ArrayList<Node>();
		fringe.add(grid.Grid[start[0]][start[1]]);
		
		
		while(pathFound) {
			
			if(fringe.size() <= 0) {
				pathFound = false;
				break;
			}
			
			//Cost allows the nodes to be compared with the different types and associated math
			double cost = fringe.get(0).cost+1;
			
			//used STRICKTLY backtracking in the AStar algorithm
			int jumps = fringe.get(0).jumps+1;
			
			//List of nodes that have already been checked by the explore()
			ArrayList<Node> explored = exploreNeighborsWW(fringe.get(0),jumps,cost , w);
			
			//Pop() all items from the list and explore() all of their possible paths in order of cost
			if(explored.size() >  0) { 
				
				
				fringe.remove(0);
				fringe.addAll(explored);
				
				//LIVE UPDATE FOR MAP
				scene.updateMap();
				
				
			} else {
				fringe.remove(0);
			}
			sortQueWW(fringe , w); //Sort the list in order by cost
		}
	}
	
	//Sort the array list by cost of the nodes
	public ArrayList<Node> sortQueWW(ArrayList<Node> sort , double w) {	
		
		//ending nodes of the map
		int[] end;
		
		//Set the end so to calculate distance
		end = grid.end;
		
		int cur = 0;
		while(cur < sort.size()) {
			int little = cur;
			for(int k = cur+1; k < sort.size(); k++) {
				
				if((sort.get(k).getEuclidDist(end[0],end[1]) )+ (sort.get(k).cost * 1/w ) < (sort.get(little).getEuclidDist(end[0],end[1])  )+(sort.get(little).cost * 1/w )){
					little = k;
				}
			}
			if(cur != little) {
				Node temp = sort.get(cur);
				sort.set(cur, sort.get(little));
				sort.set(little, temp);
			}	
			cur++;
		}
		return sort;
	}
public ArrayList<Node> exploreNeighborsWW(Node current, int jumps , double cost , double w) {
		
		//list of all explored nighbors
		ArrayList<Node> explored = new ArrayList<Node>();
		
		for(int a = -1; a <= 1; a++) {
			//row
			for(int b = -1; b <= 1; b++) {
				//column
				
				int x = current.getX()+a;
				int y = current.getY()+b;
				if((x > -1 && x < 160) && (y > -1 && y < 120)) {	
					//Assures that the node is going to be inside of the Map()
					Node neighbor = grid.Grid[x][y];
					
					//if never been touched or if its number of jumps are more
					if((neighbor.jumps ==-1 || neighbor.jumps > jumps) && neighbor.getType()!= '0') {
						
						//Explore the node itself
						exploreWW(neighbor, current.getX(), current.getY(), jumps , cost , w);
						
						//Add node to the explored list
						explored.add(neighbor);
					}
				}
			}
		}
		return explored;
	}
	
	public void exploreWW(Node current, int lastx, int lasty, int jumps , double cost , double w) {	//EXPLORE A NODE
		
		if(current.getType()!= 's' && current.getType() != 'e') {	//CHECK THAT THE NODE IS NOT THE START OR FINISH
			current.setCheckedW();
		}
		//set the prevous x and y values for traceback purposes for final path
		current.setLastNode(lastx, lasty);
		
		//set jumps its taken scince the begining
		current.jumps = jumps;
		
		//add the cost from the total of the nodes before it as it builds towards the end
		current.cost += cost(grid.Grid[lastx][lasty],current);
		
		//iterator
		checks++;
		
		//If node is the final ending point then backtrace the shortest cost to find the finalPath
		if(current.getType() == 'e') {	
			
			
			//backtrack so the algortihm can print the path
			backtrackWW(current.parent_x, current.parent_y, jumps);
		}
	}
	public void backtrackWW(int lx, int ly, int count) {
		
		//iterator
		length = count;
		double cprint = 0;
		//System.out.println("Weighted A* length" + grid.Grid[lx][ly].cost);
		
		//while the number of blocks in the path is not 0 OR the start has been found
		while(count >= 1) {

			//cur itterator
			Node current = grid.Grid[lx][ly];
			
			cprint += current.cost;
			
			current.setFinalPathA();
			grid.Grid[current.x][current.y].setFinalPathW();
			
			//iterate backwards through the node path
			lx = current.parent_x;
			ly = current.parent_y;
			
			
			count--;
		}
		pathFound = false;
		
		System.out.println("Weighted A* jumps: " + length + "  Weighted A* cost: " + cprint);
	}
	
	public void USearch() {
		

		int[] start;
		int[] end;
		
		start = grid.start;
		end = grid.end;
		
		pathFound = true;
		
		ArrayList<Node> fringe = new ArrayList<Node>();
		fringe.add(grid.Grid[start[0]][start[1]]);
		
		
		while(pathFound) {
			
			
			if(fringe.size() <= 0) {
				pathFound = false;
				break;
			}
			
			
			int jumps = fringe.get(0).jumps+1;
			
			
			ArrayList<Node> explored = exploreNeighbors(fringe.get(0),jumps);
			if(explored.size() > 0) {
				
				fringe.remove(0);
				fringe.addAll(explored);
				
				scene.updateMap();
				
				
			} else {
				fringe.remove(0);
			}
			sortQue(fringe);
		}
	}
	
	public ArrayList<Node> sortQue(ArrayList<Node> sort) {
		
		int[] start;
		int[] end;
		
		start = grid.start;
		end = grid.end;
		
		int c = 0;
		while(c < sort.size()) {
			int sm = c;
			for(int i = c+1; i < sort.size(); i++) {
				
				if(sort.get(i).getEuclidDist(end[0],end[1])/*+sort.get(i).jumps */ < sort.get(sm).getEuclidDist(end[0],end[1])/*+sort.get(sm).jumps */){
					sm = i;
				}
			}
			if(c != sm) {
				Node temp = sort.get(c);
				sort.set(c, sort.get(sm));
				sort.set(sm, temp);
			}	
			c++;
		}
		return sort;
	}
	
	public ArrayList<Node> exploreNeighbors(Node current, int jumps) {	
		ArrayList<Node> explored = new ArrayList<Node>();
		for(int a = -1; a <= 1; a++) {
			for(int b = -1; b <= 1; b++) {
				int xbound = current.getX()+a;
				int ybound = current.getY()+b;
				if((xbound > -1 && xbound < 160) && (ybound > -1 && ybound < 120)) {	

					Node neighbor = grid.Grid[xbound][ybound];
					if((neighbor.jumps ==-1 || neighbor.jumps > jumps) && neighbor.getType()!= '0') {
						

						
						explore(neighbor, current.getX(), current.getY(), jumps);	
						explored.add(neighbor);
					}
				}
			}
		}
		return explored;
	}
	
	public void explore(Node current, int lastx, int lasty, int jumps) {
		if(current.getType()!= 's' && current.getType() != 'e') {	
			current.setChecked();
		}
		if(current.x == lastx && current.y == lasty) {} else {
			current.setLastNode(lastx, lasty);
		}
		current.jumps = jumps;
		checks++;
		if(current.getType() == 'e') {
			backtrack(current.parent_x, current.parent_y, jumps);
		}
	}
	
	public void backtrack(int lx, int ly, int count) {	
		length = count;
		while(count >= 1) {	
	
			Node current = grid.Grid[lx][ly];
			
			current.setFinalPath();
			grid.Grid[current.x][current.y].setFinalPath();

			lx = current.parent_x;
			ly = current.parent_y;
			count--;
		}
		pathFound = false;
	}
	
	public double cost(Node parent, Node child) {
		//moving between two normal blocks vertically or horizontally
		if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r") || cellPos(parent, child).equals("l")) && parent.getType() == '1' && child.getType() == '1') {
			return 1.0;
		}
		//moving between 2 normal HIGHWAY blocks vertically or horizontally
		else if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r")|| cellPos(parent, child).equals("l")) && parent.getType() == 'a' && child.getType() == 'a') {
			return 1.0/4;
		}
		//moving between a normal and a hard block vertically or horizontally
		else if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r")|| cellPos(parent, child).equals("l")) && parent.getType() == '1' && child.getType() == '2') {
			return 1.5;
		}
		//moving between a normal and a hard block vertically or horizontally w HIGHWAY
		else if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r") || cellPos(parent, child).equals("l")) && parent.getType() == 'a' && child.getType() == 'b') {
			return 1.5/4;
		}
		//moving between a normal and a hard block vertically or horizontally
		else if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r") || cellPos(parent, child).equals("l")) && parent.getType() == '2' && child.getType() == '1') {
			return 1.5;
		}
		//moving between a normal and a hard block vertically or horizontally w HIGHWAY
		else if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r") || cellPos(parent, child).equals("l")) && parent.getType() == 'b' && child.getType() == 'a') {
			return 1.5/4;
		}
		//moving between two hard to traverse blocks vertically or horizontally
		else if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r") || cellPos(parent, child).equals("l")) && parent.getType() == '2' && child.getType() == '2') {
			return 2.0;
		}
		//moving between two hard to traverse HIGHWAY blocks vertically or horizontally
		else if ((cellPos(parent, child).equals("b") || cellPos(parent, child).equals("t") || cellPos(parent, child).equals("r")|| cellPos(parent, child).equals("l")) && parent.getType() == 'b' && child.getType() == 'b') {
			return 2.0/4;
		}
		//moving between two normal blocks diagonally
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == '1' && child.getType() == '1') {
			return (Math.sqrt(2.0));
		}
		//moving between two normal HIGHWAY blocks diagonally
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == 'a' && child.getType() == 'a') {
			return (Math.sqrt(2.0))/4;
		}
		//moving between a normal and a hard block diagonally
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == '1' && child.getType() == '2') {
			return ((Math.sqrt(2.0) + Math.sqrt(8)) / 2);
		}
		//moving between a normal and a hard block diagonally w HIGHWAY
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == 'a' && child.getType() == 'b') {
			return ((Math.sqrt(2.0) + Math.sqrt(8)) / 2)/4;
		}
		//moving between a normal and a hard block diagonally
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == '2' && child.getType() == '1') {
			return ((Math.sqrt(2.0) + Math.sqrt(8)) / 2);
		}
		//moving between a normal and a hard block diagonally w HIGHWAY
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == 'b' && child.getType() == 'a') {
			return ((Math.sqrt(2.0) + Math.sqrt(8)) / 2)/4;
		}
		//moving between two hard HIGHWAY blocks diagonally
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == 'b' && child.getType() == 'b') {
			return Math.sqrt(8)/ 4;
		}
		//moving between two hard cells diagonally
		else if ((cellPos(parent, child).equals("br") || cellPos(parent, child).equals("tr") || cellPos(parent, child).equals("bl") || cellPos(parent, child).equals("tl")) && parent.getType() == '2' && child.getType() == '2') {
			return (Math.sqrt(8));
		}
		//Moving in and out of a highway
		else{
			return 1.0;
		}


	}
	
	public List<Node> findPath(Node node, GridGenerator grid) {
		Node temp = grid.Grid[grid.end[0]][grid.end[1]];
		List<Node> path = new ArrayList<>();
		do {
			path.add(temp);
			temp = temp.getNodeParent();
		}
		while (temp != grid.Grid[grid.start[0]][grid.start[1]]);

		//for (Node n : path) {
		//	n.getRectangle().setFill(Color.GREEN);
		//}
		return path;
	}

	public List<Node> getNeighbors(Node node, Node[][] grid) {
		int[][] NEIGHBOR_POINTS = {
						{-1, 0},
						{ 0,-1},
						{0, 1},
						{ 1, 0},
						{-1,-1},
						{-1,1},
						{1,1},
						{1,-1}
				};

				List<Node> neighbors = new ArrayList<>();

				for (int[] neighborPositions : NEIGHBOR_POINTS) {
					int nrow = node.getX() + neighborPositions[0];
					int ncol = node.getY() + neighborPositions[1];
					if (nrow >= 0 && nrow < 120 && ncol >= 0 && ncol < 160) {
						neighbors.add(grid[nrow][ncol]);
					}
				}
				return neighbors;
			}

	//FInd shortest path from start to current node
	public double findG(Node cur, Node start) {
		
		double dist = Math.sqrt((( start.x - cur.x )*2) + (( start.y - cur.y)*2));
		return dist;
		
	}

	//Finding the euclidean distance between the current node and the end node
	public double h(Node n, Node end) {
		double dist = Math.sqrt(((n.x - end.x)*2) + ((n.y - end.y)*2));
		return dist;
	}

	//Return the position of the next node as a string to assist with the movement cost calculations
	public String cellPos(Node parent, Node child) {

		if ((parent.x == child.x + 1) && (parent.y == child.y - 1)) { //top right
			return ("tr");
		} else if ((parent.x == child.x + 1) && (parent.y == child.y + 1)) { //top left
			return ("tl");
		} else if ((parent.x == child.x - 1) && (parent.y == child.y + 1)) { //bottom left
			return ("bl");
		} else if ((parent.x == child.x - 1) && (parent.y == child.y - 1)) { //bottom right
			return ("tl");
		} else if ((parent.x == child.x - 1) && (parent.y == child.y)) { //bottom
			return ("b");
		} else if ((parent.x == child.x + 1) && (parent.y == child.y)) { //top
			return ("t");
		} else if ((parent.x == child.x) && (parent.y == child.y - 1)) { //right
			return ("r");
		} else return ("l");
	}
}
