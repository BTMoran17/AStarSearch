import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


import javax.swing.JFrame;
import javax.swing.JPanel;


//In the Algorithm you must update the grid regularly to maintain an accurate grid
public class DrawGrid {

	GridGenerator grid;
	JFrame frame;
	Map canvas;
	
	//Default Constructor
	public DrawGrid() {
		init();
		grid  = new GridGenerator();

	}
	
	//Constructor to Create the image and structure based off grid
	public DrawGrid(GridGenerator g) {
		init();
		grid = g;
	}
	
	
	
	private final int WIDTH = 820;
	private final int HEIGHT = 640;
	private final int XSIZE = 800;
	private final int YSIZE = 600;
	
	private final int buckX = 160;
	private final int buckY = 120;
	
	private int CSIZE = XSIZE / buckX;



	
	public void init() {	
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		initMap();
		
		frame.getContentPane().add(canvas);
		
	}
	
	public void initMap() {
		
		canvas = new Map();
		canvas.setBounds(10, 10, XSIZE+1, YSIZE+1);

	}
	
	//adds delay for live solving and updates the map
	public void updateMap() {
		
		try {
			Thread.sleep(15);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		canvas.repaint();
	}
	
	public void updateGrid(GridGenerator g) {
		this.grid = g;
	}
	
	class Map extends JPanel {	//MAP CLASS
		public void paintComponent(Graphics g) {	//REPAINT
			//super.paintComponent(g);
			g.setColor(Color.RED);
			for(int x = 0; x < buckX; x++) {	//PAINT EACH NODE IN THE GRID
				for(int y = 0; y < buckY; y++) {

					switch(grid.Grid[x][y].type) {
						case '0':
							g.setColor(Color.BLACK);
							g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							g.setColor(Color.BLACK);
							g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							break;
						case '1':
							g.setColor(Color.WHITE);
							g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							g.setColor(Color.BLACK);
							g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							break;
						case '2':
							g.setColor(new Color(170,170,170)); //Light Gray
							g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							g.setColor(Color.BLACK);
							g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							break;
						case 's':
							g.setColor(Color.GREEN);
							g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							break;
						case 'e':
							g.setColor(Color.RED);
							g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							break;
							

						case 'a':
							g.setColor(new Color(204,212,255)); //Light Blue
							g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							g.setColor(Color.BLACK);
							g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							break;
						case 'b':
							g.setColor(new Color(119,133,199)); //Dark Blue
							g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							g.setColor(Color.BLACK);
							g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
							break;
						
					}
					
					//Highlights for grids that were checked
					if(grid.Grid[x][y].wasChecked) {
						g.setColor(new Color(255,255,179)); //Light Yellow
						g.drawOval(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					}
					if(grid.Grid[x][y].wasCheckedA) {
						g.setColor(new Color(255,179,25)); //Light Orange
						g.drawOval(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					}
					
					if(grid.Grid[x][y].wasCheckedW) {
						g.setColor(new Color(100,255,100)); //Light Green
						g.drawOval(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					}
					
					//Bright pink indicattor over the final path squares
					if(grid.Grid[x][y].isFinalPath) {
						 g.setColor(new Color(255,0,128)); //Bright Pink
						 g.setFont(new Font("Arial",Font.BOLD,8));
						 //Draw String in JPanel
						 g.drawString("+",x*CSIZE,y*CSIZE+5);
						 g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					}
					
					if(grid.Grid[x][y].isFinalPathA) {
						 g.setColor(new Color(153,0,0)); //maroon
						 g.setFont(new Font("Arial",Font.BOLD,8));
						 //Draw String in JPanel
						 g.drawString("+",x*CSIZE,y*CSIZE+5);
						 g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					}
					
					if(grid.Grid[x][y].isFinalPathW) {
						 g.setColor(new Color(0,60,0)); //Dark Green
						 g.setFont(new Font("Arial",Font.BOLD,8));
						 //Draw String in JPanel
						 g.drawString("+",x*CSIZE,y*CSIZE+5);
						 g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					}
				}
			}
		}
	}
}


