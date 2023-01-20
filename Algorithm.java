import java.util.*;

public class Algorithm {


	GridGenerator grid;

	//Default Constructor
	//For testing and demonstrating purposes
	public Algorithm() {
		grid = new GridGenerator();
	}

	//Perform Algorithm on particular Grid
	public Algorithm(GridGenerator a) {
		grid = a;
	}

	int[] start;
	int[] end;
	//Node end = new Node();
//List of current nodes to be expanded
	PriorityQueue<Node> fringe = new PriorityQueue<Node>(new Comparator<Node>() {
		@Override
		public int compare(Node node1, Node node2) {
			return Integer.compare(node1.getF(), node2.getF());
		}
	});
	Set<Node> closed = new HashSet<Node>();
	Node s = grid.Grid[start[0]][start[1]];
	Node e = grid.Grid[end[0]][end[1]];


	public void AStar() {

		Node s = grid.Grid[start[0]][start[1]];
		Node e = grid.Grid[end[0]][end[1]];

		fringe.add(s);


		while (!fringe.isEmpty()) {
			Node current = fringe.poll();

			if (current.equals(e)) {
				findPath(current, grid);
				break; //found the goal
			}

			fringe.remove(current);
			closed.add(current);

			for (Node neighbor : getNeighbors(current, grid.Grid)) {

				if (neighbor.getValue() == 1)
					continue;

				Double g = current.getG() + h(current, neighbor);

				//This is used for tie-breaking higher G values.
				if (closed.contains(neighbor))
					continue;

				if (!fringe.contains(neighbor) || g > neighbor.getG()) {
					neighbor.setG(g);
					neighbor.setH(h(neighbor, e));
					neighbor.setF(neighbor.getG() + neighbor.getH());
					neighbor.setNodeParent(current);
					//neighbor.updateToolTip();
					//neighbor.getRectangle().setFill(Color.CYAN);
					fringe.add(neighbor);
				}

			}
		}
	}



	////////////////////////////////////////////
	public List<Node> findPath(Node node, GridGenerator grid) {
		Node temp = grid.Grid[end[0]][end[1]];
		List<Node> path = new ArrayList<>();
		do {
			path.add(temp);
			temp = temp.getNodeParent();
		}
		while (temp != grid.Grid[start[0]][start[1]]);

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
////////////////////////////////////////////
/*
		while (!fringe.isEmpty()) {
			Node cur = fringe.remove();

			if (cur.x == test.end[0] && cur.y == test.end[1]) {
				//Path Found
			}


		}
	}
*/

	//FInd shortest path from start to current node
	public void findG(PriorityQueue<Node> fringe) {

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

	//Calculating the cost of the movement from one node to its neighbor based on its neighbors type and position
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
}
