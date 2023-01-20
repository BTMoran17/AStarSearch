import java.io.*;
class Exev{
	public static void main(String[] args) {
    
		long startTime = System.nanoTime();
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		
		  int[] start = {0,0};
		  int[] end = {159,119};
		
		//GridGenerator test = new GridGenerator("test2.txt");
		//GridGenerator test = new GridGenerator(start,end);
		GridGenerator test = new GridGenerator();
		  
		test.writeToFile("test2.txt");
		
		DrawGrid b = new DrawGrid(test);
		
	    AlternateAlgorithm c  = new AlternateAlgorithm(test,b);
	    
	    
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		
	    
	    c.USearch();
	    
	    
	    c.reset();
	    
	    
	    c.AStar();
	    
	    
	    c.reset();
	    
	    
	    c.WAStar(.2);
	    
	    

	    //System.out.println("time A*: " +totalTime);

		
	}
}
