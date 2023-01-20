class Node{
  double cost = -1;
  boolean wasChecked;
  boolean wasCheckedA = false;
  boolean wasCheckedW = false;
  
  boolean isFinalPath;
  boolean isFinalPathA = false;
  boolean isFinalPathW = false;
  char type;
  int x;
  int y;
  int parent_x;
  int parent_y;
  double f,h,g = Double.POSITIVE_INFINITY;
  Node parent;
  private int value;
  int jumps;


  // For the function that is only going to pass in a character, to find weight
  //HashMap<Character, Double> quick = new HashMap<Character, Double>();
  public Node(){
    cost = 0;
    type = '1';
    wasChecked = false;
    this.isFinalPath = false;
    jumps = -1;
  }
  public Node(int c, char type){
    this.cost = c;
    this.type = type;
    this.wasChecked = false;
    this.isFinalPath = false;
    jumps = -1;
  }

  //Need to decide on Weighted Values for the spaces first
  public Node(char type){
    this.type = type;
    this.cost = 0.0;
    this.wasChecked = false;
    this.isFinalPath = false;
    jumps = -1;
  }
  
  //*/
  public Node(char type, int x, int y) {
	  this.type = type;
	  this.x = x;
	  this.y = y;
	  this.wasChecked = false;
	  this.isFinalPath = false;
	  jumps = -1;
	  /*
    if(type == 's'){
      this.f = 0.0;
      this.g = 0.0;
      this.h = 0.0;
      this.parent_x = x;
      this.parent_y = y;
    } else {
      this.cost = 0;
      this.type = type;
      this.x = x;
      this.y = y;
      this.wasChecked = false;
      this.isFinalPath = false;
    }
    */
  }

  public void setH(double h){
    this.h = h;
  }
  public void setG(double g){
    this.g = g;
  }
  public void setF(double f){
    this.f = f;
  }

  public double getH(){
    return this.h;
  }
  public double getG(){
    return this.g;
  }
  public int getF(){
    return (int)this.f;
  }


  public char getType() { return this.type; }
  public int getX() { return this.x; }
  public int getY() { return this.y; }
  public boolean wasChecked() { return this.wasChecked;}
  public double getCost() { return this.cost; }
  public void setLastNode(int x, int y) {parent_x = x; parent_y = y;}
  
  
  public void setNodeParent(Node parent) {
    this.parent = parent;
  }

  public Node getNodeParent() {
    return parent;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getValue(){
    return this.value;
  }
  public void setChecked() {
	  this.wasChecked = true;
  }
  public void setCheckedA() {
	  this.wasCheckedA = true;
  }
  public void setCheckedW() {
	  this.wasCheckedW = true;
  }
  public void uncheck() {
	  this.wasChecked = false;
  }
  public void setFinalPath() {
	  this.isFinalPath = true;
  }
  public void setFinalPathA() {
	  this.isFinalPathA = true;
  }
  public void setFinalPathW() {
	  this.isFinalPathW = true;
  }
  public void reset() {
	  this.jumps = -1;
	  this.cost = -1;
	 // this.wasChecked = false;
  }
  
	public double getEuclidDist(int end1 , int end2) {		//CALCULATES THE EUCLIDIAN DISTANCE TO THE FINISH NODE
		int xdif = Math.abs(x-end1);
		int ydif = Math.abs(y-end2);
		h = Math.sqrt((xdif*xdif)+(ydif*ydif));
		return h;
	}


}
