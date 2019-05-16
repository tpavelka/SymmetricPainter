
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * 1) RNG-Vectors are assigned a random magnitude between 0 and MAG_MAX.
 * <br>
 * 2) The creation algorithm starts off by checkering the vector field with vectors of random direction and<br>
 * magnitude every RNG_SPACING across the vector feild in both x and y directions.<br>
 * <br>
 * 3) The creation algorithm then smooths the vf by using the rng vectors to fill in the rest of the vf.<br>
 * In other words, the non-rng vectors do not affect eachother.
 * <br>
 * @author Travis Pavelka
 *
 */
public class RandomLinearVF {
	public static final int MAG_MAX = 30;
	public static final int RNG_SPACING = 19;
	
	/**
	 * The refrence to the random this vf uses
	 */
	private Random rand;
	public Random getRandom() {
		return this.rand;
	}
	
	/**
	 * The size of this randomized vf
	 */
	private int reqwidth;
	public Integer copyWidth() {
		return new Integer(this.reqwidth);
	}
	private int actwidth;
	
	private int reqheight;
	public Integer copyHeight() {
		return new Integer(this.reqheight);
	}
	private int actheight;
	
	/**
	 * A 2-D Array of the vectors, might be larger than width and height so that the
	 * generation algorithm can go all the way to the edges of the VF
	 */
	private Vector[][] vectors;
	public Vector copyVector(int x, int y) {
		if(this.vectors[y][x] != null) {
			return this.vectors[y][x].clone();
		} else {
			return null;
		}
	}
	private void setVector(int x, int y, Vector v) {
		this.vectors[y][x] = v;
	}
	
	private boolean withinBoundsX(int x) {
		if( 0 <= x && x < this.actwidth ) {
			return true;
		} else {
			return false;
		}
	}
	private boolean withinBoundsY(int y) {
		if( 0 <= y && y < this.actwidth ) {
			return true;
		} else {
			return false;
		}
	}
	private Point getPrevUp(Point cur) {
		if(cur.y % RNG_SPACING == 0) {
			// this point is on a divisibility line
			// make sure this point isnt the top
			if(0 < cur.y) {
				
			}
			// get the previous rng
			
			
		} else {
			// this point is between divisibility lines
			// get the closest rng
			
			
		}
		
		int newy = cur.y - RNG_SPACING;
		if(newy > 0) {
			return new Point(cur.x, newy);
		} else {
			return null;
		}
	}
	private Point getPrevLeft(Point cur) {
		int newx = cur.x - RNG_SPACING;
		if(newx > 0) {
			return new Point(newx, cur.y);
		} else {
			return null;
		}
	}
	private Point getNextDown(Point cur) {
		int newy = cur.y + RNG_SPACING;
		if(newy < this.actheight) {
			return new Point(cur.x, newy);
		} else {
			return null;
		}
	}
	private Point getNextRight(Point cur) {
		int newx = cur.x + RNG_SPACING;
		if(newx < this.actwidth) {
			return new Point(newx, cur.y);
		} else {
			return null;
		}
	}
	
	/**
	 * The generation algorithm may make the VF larger than requested in order to<br>
	 * properly generate the VF. The user will not have to worry about this though<br>
	 * as the VF will seem like it is the reqested size.<br>
	 * @param seed used by random to get a seeded vf
	 * @param width the requested width of the canvas
	 * @param height the requested height of the canvas
	 */
	public RandomLinearVF(long seed, int width, int height) {
		// initialization
		this.rand = new Random(seed);
		this.reqwidth = width;
		this.reqheight = height;
		
		// the vector field's width and height has to be divisible by the rng spacing,
		// so the array is set to the next highest divisible int
		
		// get the space between the end of the requested size and the next divisible int
		int addtowidth = 0;
		int addtoheight = 0;
		if(this.reqwidth % RNG_SPACING != 0) {
			addtowidth = RNG_SPACING - (this.reqwidth % RNG_SPACING);
			
		}
		if(this.reqheight % RNG_SPACING != 0) {
			addtoheight = RNG_SPACING - (this.reqheight % RNG_SPACING);
		}
		
		// calculate the actual width and height
		this.actwidth = this.reqwidth + addtowidth;
		this.actheight = this.reqheight + addtoheight;
		
		// initialize the vector array
		this.vectors = new Vector[this.actheight][this.actwidth];
		
		// iterate through rng positions
		// and set rng vectors
		for(int y = 0; y < this.reqheight; y += RNG_SPACING) {
			for(int x = 0; x < this.reqwidth; x += RNG_SPACING) {
				// add rng vector
				double direction = this.rand.nextDouble() * 360;
				double magnitude = this.rand.nextDouble() * MAG_MAX;
				Vector add = new Vector(direction, magnitude);
				add.setRNG(true);
				this.setVector(x, y, add);
			}
		}
		// rng-vectors added
		
		// iterate through all positions
		for(int y1 = 0; y1 < this.reqheight; y1++) {
			for(int x1 = 0; x1 < this.reqwidth; x1++) {
				Point p1 = new Point(x1, y1);
				// see if this position is a rng-vector
				if(copyVector(x1, y1) == null) {
					// its not, create a non-rng vector
					// arraylist of segments for use with calculating this vector
					ArrayList<Segment> segs = new ArrayList<Segment>();
					// get all rng-vectors within RNG_SPACING
					// iterate through all rng positions
					for(int y2 = 0; y2 < this.reqheight; y2 += RNG_SPACING) {
						for(int x2 = 0; x2 < this.reqwidth; x2 += RNG_SPACING) {
							Point p2 = new Point(x2, y2);
							// within distance, add segment
							segs.add(new Segment(p1, p2));
						}
					}
					// segs is built
					// get the total distance
					double dtotal = 0;
					for(Segment s: segs) {
						dtotal += s.copyDist();
					}
					// calculate non-rng vector
					Vector add = new Vector(0, 0);
					for(Segment s: segs) {
						// get the rng-vector for this segment
						Vector rng = copyVector(s.copySecondaryPoint().x, s.copySecondaryPoint().y);
						double dist = s.copyDist();
						double mag = rng.copyMagnitude() * (dist/dtotal);
						double dir = rng.copyDirection();
						Vector nonrng = new Vector(dir, mag);
						add.addVector(nonrng);
					}
					// all non-rng components have been added, do average
					double mag = add.copyMagnitude();
					add.setMagnitude(mag);
					add.setRNG(false);
					this.setVector(x1, y1, add);
				}
			}
		}
		
	}
}
