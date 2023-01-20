import java.io.*;
import java.util.Scanner;
import java.util.stream.*;
class GridGenerator {
  //Grid class has a 2D array of Nodes, from NodeClass
  // An array of 2 integers that represents the start Nodes
  // An array of 2 integers representing the end nodes;
  //cells that are hard to traverse;
  Node[][] Grid;
  int[] start;
  int[] end;
  int hardTraverse;

// 5 Kinds of constructors:
// One Constructor that generates the complete grid with start and end points
  public GridGenerator(){
    Grid = new Node[160][120];
    start = new int[2];
    end = new int[2];
    //generate();
    blockedCells();
    hardCells();
    normalCells();
    highways();
    generate();
    stats();
  }
//the file name is the file to save the grid, rand is to separate the constructor from others
  public GridGenerator(String fileName, int rand){
    Grid = new Node[160][120];
    start = new int[2];
    end = new int[2];
    //generate();
    blockedCells();
    hardCells();
    normalCells();
    highways();
    writeToFile(fileName);
    //stats();
  }
  // One Constructor that takes in arrays with a start and end location;
  public GridGenerator(int[] start, int[] end){
    Grid = new Node[160][120];
    Grid[start[0]][start[1]] = new Node('s' , start[0] , start[1]);
    Grid[end[0]][end[1]] = new Node('e' , end[0] , end[1]);
    this.start = start;
    this.end = end;
    hardTraverse = 0;
  }
  //Reads in a files grid and creates a new start and stop location
  public GridGenerator(String fileName, int[] start, int[] end){
    this(fileName);
    Grid[start[0]][start[1]] = new Node('s' , start[0] , start[1]);
    Grid[end[0]][end[1]] = new Node('e' , end[0] , end[1]);
    this.start = start;
    this.end = end;

  }
  // One Constructor that takes in a string that is a file name to import the data;
  public GridGenerator(String fileName){
    Grid = new Node[160][120];
    hardTraverse = 0;
    try {
      File gridder = new File(fileName);
      Scanner scan = new Scanner(gridder);
      String starters = scan.nextLine();
      starters = starters.replaceAll("[^\\d,]", "");
      start = Stream.of(starters.split(",", 0)).mapToInt(Integer::parseInt).toArray();
      String enders = scan.nextLine();
      enders = enders.replaceAll("[^\\d,]", "");
      end = Stream.of(enders.split(",", 0)).mapToInt(Integer::parseInt).toArray();
      int k=0;
      while (scan.hasNextLine()) {
        String row = scan.nextLine();
        for(int i = 0; i<row.length(); i++ ){
          if(row.charAt(i) == '-'){
            Grid[k][i] = null;
          } else if(row.charAt(i) == '2'){
            Grid[k][i] = new Node(row.charAt(i) , k , i);
            hardTraverse++;
          } else {
            Grid[k][i] = new Node(row.charAt(i) , k , i);
          }
        }
        k++;
      }

      scan.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
      e.printStackTrace();
    }
  }


//Print to stream the values within the grid
// Warning! Huge output for the stream.
  public void traverse(){
    for(int i = 0; i<Grid.length; i++){
      for(int k =0; k<Grid[i].length; k++){
        if(Grid[i][k] == null){
          System.out.print("-");
        } else {
          System.out.print("" + Grid[i][k].type);
        }
      }
      System.out.println();
    }
  }

// Prints to the stream the grids designated start and end point;
  public void stats(){
    if(start!=null){
      System.out.println("Start = " + start[0]+", "+start[1]);
    }
    if(end!=null){
      System.out.println("End = " + end[0]+", "+end[1]);
    }
      System.out.println("Number of hardCells = " + hardTraverse);
  }
//Generates start and stop
  public void generate(){
    int x1,x2,y1,y2=0;
    do{
      start = genStart();
      end = genEnd();
      x1 = start[0];
      x2 = end[0];
      y1 = start[1];
      y2 = end[1];
    } while(Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))<100);
    Grid[start[0]][start[1]] = new Node('s' , start[0],start[1]);
    Grid[end[0]][end[1]] = new Node('e' , end[0] , end[1]);
    return;
  }
  //Generates start for the path process, in array format
  public static int[] genStart(){
    int[] starters = new int[2];
    if(Math.random()>.5){
      starters[0] = (int)(Math.random()*20);
    } else {
      starters[0] = ((int)(Math.random()*20))+139;
    }
    if(Math.random()>.5){
      starters[1] = ((int)(Math.random()*20))+99;
    } else {
      starters[1] = (int)(Math.random()*20);
    }

    return starters;
  }
  //Generates Goals for the path process, in array format
  public static int[] genEnd(){
    int[] end = new int[2];
    if(Math.random()>.5){
      end[0] = (int)(Math.random()*20);
    } else {
      end[0] = ((int)(Math.random()*20))+139;
    }
    if(Math.random()>.5){
      end[1] = ((int)(Math.random()*20))+99;
    } else {
      end[1] = (int)(Math.random()*20);
    }
    return end;
  }


//Generates the Harder to traverse cells
  public int hardCells(){
    int hardsCells = 0;
    int[][] centers = new int[8][2];
    for(int i = 0; i<8; i++){
      centers[i][0] = (int)(Math.random()*160);
      centers[i][1] = (int)(Math.random()*120);
    }
    for(int i = 0; i<8; i++){
      int a = centers[i][0];
      int b = centers[i][1];
      int lowx, lowy, highx, highy= 0;
      lowx=Math.max(a-16, 0);
      lowy=Math.max(b-16, 0);
      highx=Math.min(a+16, 160);
      highy=Math.min(b+16, 120);
      for(int k= lowx; k<highx;k++){
        for(int j= lowy; j<highy;j++){

          //  System.out.println(""+ k +" "+j+" " +highx +" "+highy+ " " +lowx +" "+lowy);

          if((k==start[0] && j == start[1]) || (k==end[0]&&j==end[1])){
            break;
          }
          double fifty = Math.random();
          if(fifty>.5){
            Grid[k][j] = new Node('2' , k , j);
            hardsCells++;
          }

        }
      }
    }
    hardTraverse = hardsCells;
    return hardsCells;
  }
//Generates the blocked cells that you cannot traverse
  public void blockedCells(){
    for (int i=0;i<3840 ;i++) {
      int x = (int)(Math.random()*160);
      int y = (int)(Math.random()*120);
      if((x!=start[0] && y != start[1]) || (x!=end[0]&&y!=end[1])){
        Grid[x][y] = new Node('0' , x ,y);
        continue;
      }
      i--;
    }
    return;
  }
//Generates the Normal cells within the grid
  public void normalCells(){
    for(int i = 0; i<Grid.length; i++){
      for(int k =0; k<Grid[i].length; k++){
        if(Grid[i][k] == null){
          Grid[i][k] =  new Node('1', i , k);
        }
      }
    }
    return;
  }
//Generates 4 highways within the map
  public void highways(){
    System.out.println("x: "+Grid.length + ", y: "+ Grid[0].length);
    for (int i = 0;i<4; i++ ) {
      int x =0;
      int y =0;
      char prev ='a';
      if(Math.random()>.5){
        x = (int)(Math.random()*140);
      } else {
        y = (int)(Math.random()*100);
      }

      if(Math.random()>.5){
        prev = 'x';
        for (int k = 0; k<20;k++ ) {
            if(Grid[x+k][y].type == 's'|| Grid[x+k][y].type == 'e' || Grid[x+k][y].type == 'a' || Grid[x+k][y].type == 'b'){
              break;
            } else if(Grid[x+k][y].type == '1'){
              Grid[x+k][y].type = 'a';
            } else {
              Grid[x+k][y].type = 'b';
            }
        }
        x+=20;
      } else {
        prev = 'y';
        for (int k = 0; k<20;k++ ) {

            if(Grid[x][y+k].type == 's'|| Grid[x][y+k].type == 'e' || Grid[x][y+k].type == 'a' || Grid[x][y+k].type == 'b'){
              break;
            } else if(Grid[x][y+k].type == '1'){
              Grid[x][y+k].type = 'a';
            } else {
              Grid[x][y+k].type = 'b';
            }

        }
        y+=20;
      }

      while(x<160 && y<120 && x>=0 && y>=0){
        double randi=Math.random();
      //  System.out.println("" + x + ", "+y);
        if( (prev=='y' && randi>.6) || (prev=='x' && randi<.6) ){
          if(Grid[x][y].type == 's'|| Grid[x][y].type == 'e' || Grid[x][y].type == 'a' || Grid[x][y].type == 'b'){
            break;
          } else if(Grid[x][y].type == '1'){
            Grid[x][y].type = 'a';
          } else {
            Grid[x][y].type = 'b';
          }
            y+=1;
        } else {
          if(Grid[x][y].type == 's'|| Grid[x][y].type == 'e' || Grid[x][y].type == 'a' || Grid[x][y].type == 'b'){
            break;
          } else if(Grid[x][y].type == '1'){
            Grid[x][y].type = 'a';
          } else {
            Grid[x][y].type = 'b';
          }
          x+=1;
        }

      }

    }
  }

// Creates a new file with the first two lines being the start and end points
// then 160 rows of data;
  public void writeToFile(String fileName){
    try {
     File myObj = new File(fileName);
     if (myObj.createNewFile()) {
       System.out.println("File created: " + myObj.getName());
       FileWriter fileWriter = new FileWriter(fileName);
       PrintWriter writing = new PrintWriter(fileWriter);
       writing.printf("Start: %d, %d ", start[0], start[1]);
       writing.printf("%n");
       writing.printf("End: %d, %d ", end[0], end[1]);
       writing.printf("%n");
       for(int i = 0; i<Grid.length; i++){
         for(int k =0; k<Grid[i].length; k++){
           if(Grid[i][k] == null){
             writing.printf("-");
           } else {
             writing.printf("%c", Grid[i][k].type);
           }
         }
         writing.printf("%n");
       }
       writing.close();
     } else {
       System.out.println("File already exists.");
       FileWriter fileWriter = new FileWriter(fileName);
       PrintWriter writing = new PrintWriter(fileWriter);
       writing.printf("Start: %d, %d%n", start[0], start[1]);
       writing.printf("End: %d, %d%n", end[0], end[1]);
       for(int i = 0; i<Grid.length; i++){
         for(int k =0; k<Grid[i].length; k++){
           if(Grid[i][k] == null){
             writing.printf("-");
           } else {
             writing.printf("%c", Grid[i][k].type);
           }
         }
         writing.printf("%n");
       }
       writing.close();

     }
   } catch (IOException e) {
     System.out.println("An error occurred.");
     e.printStackTrace();
   }
   return;
  }

  public void uncheck() {
	  for(int x = 0; x < 160; x++) {	//PAINT EACH NODE IN THE GRID
			for(int y = 0; y < 120; y++) {
				this.Grid[x][y].reset();
			}
	  }
  }



















}
