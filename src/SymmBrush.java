import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
/**
 * Two brushes that move symmetrically to eachother, only one having contact<br>
 * with a vector field.
 * @author Travis Pavelka
 *
 */
public class SymmBrush {
	private RandomVectorField rvf;
	private Random rand;
	
	private double width;
	private double height;
	
	private double x1;
	private double x2;
	
	private double y1;
	private double y2;
	
	private Vector v1;
	private Vector v2;
	
	private Color c1;
	private Color c2;
	
	private double brushSize() {
		return Math.sqrt(v1.copyMagnitude());
	}
	
	private void setDirs(double dir) {
		this.v1.setDirection(dir);
		this.v2.setDirection(Math.abs(dir - 180));
	}
	
	private void setMags(double mag) {
		this.v1.setMagnitude(mag);
		this.v2.setMagnitude(mag);
	}
	
	public void update(long diff) {
		
	}
	
	public void paintComponent(Graphics g) {
		
	}
	
	public SymmBrush(double width, double height, RandomVectorField rvf) {
		// the vector field we will paint in the presence of
		this.rvf = rvf;
		
		// the rand from the vf so brushes are sync with seed
		this.rand = rvf.getRandom();
		
		 // the size of the canvas
		this.width = width;
		this.height = height;
		
		// start in the middle
		this.x1 = width/2;
		this.x2 = width/2;
		this.y1 = height/2;
		this.y2 = height/2;
		
		// random dir, no velocity
		this.setDirs(rand.nextDouble() * 360);
		this.setMags(0);
		
		// start off the bg color, black
		this.c1 = Color.BLACK;
		this.c2 = Color.BLACK;
	}
}
