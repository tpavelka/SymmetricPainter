import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;
/**
 * Two brushes that move symmetrically to eachother, only one having contact<br>
 * with a vector field.
 * @author Travis Pavelka
 *
 */
public class SymmBrush {
	// in degrees
	private static final double DOCAD =		30;
	private static final double HEXAD =		60;
	private static final double TRIAD =		120;
	private static final double SPECTRUM =	360;
	
	private static final double OFFSET = 	30; 
	private static final double RED =		(0 	 + OFFSET) % SPECTRUM;
	private static final double YELLOW =	(60	 + OFFSET) % SPECTRUM;
	private static final double GREEN =		(120 + OFFSET) % SPECTRUM;
	private static final double TEAL =		(180 + OFFSET) % SPECTRUM;
	private static final double BLUE =		(240 + OFFSET) % SPECTRUM;
	private static final double PURPLE =	(300 + OFFSET) % SPECTRUM;
	
	// how much of the previous vector carries into this tick
	private static final double INERTIA = 0.25;
	// colorizer: lower is less colory
	private static final int COLORIZER = 10;
	
	private RandomLinearVF rlvf;
	private Random rand;
	private Dimension screen;
	private Point mid;
	private Point2D.Double p1;
	private Point2D.Double p2;
	private Vector v1;
	private Vector v2;
	
	private Color color;
	private double size;
	private long iteration;
	
	private static Color calcBrushColor(double mag) {
		mag *= COLORIZER;
		
		// bounds checking
		mag %= SPECTRUM;
		
		mag += OFFSET;
		
		// get a color
		if(mag < RED) {
			double p = (mag/DOCAD)*128.0;
			return new Color	(255,			0,			128-(int)p	);
			
		} else if(mag < YELLOW) {
			double p = ((mag-RED)/HEXAD)*255.0;
			return new Color	(255,			(int)p,		0			);
			
		} else if(mag < GREEN) {
			double p = ((mag-YELLOW)/HEXAD)*255.0;
			return new Color	(255-(int)p,	255,		0			);
			
		} else if(mag < TEAL) {
			double p = ((mag-GREEN)/HEXAD)*255.0;
			return new Color	(0,				255,		(int)p		);
			
		} else if(mag < BLUE) {
			double p = ((mag-TEAL)/HEXAD)*255.0;
			return new Color	(0,				255-(int)p,	255			);
			
		} else if(mag < PURPLE) {
			double p = ((mag-BLUE)/HEXAD)*255.0;
			return new Color	((int)p,		0,			255			);
			
		} else {
			double p = ((mag-PURPLE)/DOCAD)*128.0;
			return new Color	(255,			0,			255-(int)(p));
			
		}
	}
	
	private static double calcBrushSize(double mag) {
		return mag * 10;
	}
	
	private void setDirs(double dir) {
		this.v1.setDirection(dir);
		this.v2.setDirection(Vector.invert(dir));
	}
	
	private void setMags(double mag) {
		this.v1.setMagnitude(mag);
		this.v2.setMagnitude(mag);
	}
	
	private void movePos(double dx, double dy) {
		this.p1.x += dx;
		this.p1.y += dy;
		this.p2.x -= dx;
		this.p2.y -= dy;
	}
	
	private void setPos(double posx, double posy) {
		this.p1.x = posx;
		this.p1.y = posy;
		this.p2.x = -(posx - mid.x) + mid.x;
		this.p2.y = -(posy - mid.y) + mid.y;
	}
	
	/**
	 * Set the brush at a random position with no velocity.
	 */
	private void reset() {
		setPos(rand.nextDouble() * screen.width, rand.nextDouble() * screen.height);
		
		v1.setX(0);
		v1.setY(0);
		v1.calcDirMag();
		
		v2.setX(0);
		v2.setY(0);
		v2.calcDirMag();
		
		this.color = Color.BLACK;
		this.size = 0;
		this.iteration = 0;
	}
	
	/**
	 * update this brush with the diff time<br>
	 * diff in nanoseconds (1e-9 second)
	 */
	public synchronized void update(long diff) {
		Vector inertia = v1;
		inertia.calcDirMag();
		inertia.scale(INERTIA);
		inertia.calcComps();
		int x = (int)Math.round(p1.x);
		int y = (int)Math.round(p1.y);
		Vector force = rlvf.getVector(x, y);
		Vector copy = new Vector(force);
		copy.calcDirMag();
		copy.scale(1.0-INERTIA);
		copy.calcComps();
		v1 = Vector.addVectors(inertia, copy);
		v1.calcDirMag();
		double dx = v1.getX();
		double dy = v1.getY();
		
		this.movePos(dx, dy);
		this.setDirs(v1.getDirection());
		this.setMags(v1.getMagnitude());
		
		iteration++;
		
		color = calcBrushColor(v1.getMagnitude());
		size = calcBrushSize(v1.getMagnitude());
		
		if(p1.x < 0 || screen.width < p1.x
			|| p1.y < 0 || screen.height < p1.y) {
			reset();
		}
	}
	
	/**
	 * Paint this brush to the screen
	 */
	public synchronized void paintBrush(Graphics g) {
		g.setColor(color);
		g.fillArc((int)(Math.round(p1.x)-(size/2)), (int)(Math.round(p1.y)-(size/2)), (int)Math.round(size) ,(int)Math.round(size), 0, 360);
		//g.fillArc((int)(Math.round(p2.x)-(size/2)), (int)(Math.round(p2.y)-(size/2)), (int)Math.round(size) ,(int)Math.round(size), 0, 360);
	}
	
	public SymmBrush(RandomLinearVF rlvf, int cwidth, int cheight) {
		// init
		this.rlvf = rlvf;
		this.rand = rlvf.getRandom();
		this.screen = new Dimension(cwidth, cheight);
		this.mid = new Point(cwidth/2, cheight/2);
		this.p1 = new Point2D.Double();
		this.p2 = new Point2D.Double();
		this.v1 = new Vector(-1, 0, Vector.DIR_MAG);
		this.v2 = new Vector(-1, 0, Vector.DIR_MAG);
		this.color = Color.BLACK;
		this.size = 0;
		this.iteration = 0;
		// set position
		reset();
	}
}
