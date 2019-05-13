
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
public class RandomVectorField {
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
	private int width;
	public Integer copyWidth() {
		return new Integer(this.width);
	}
	private int height;
	public Integer copyHeight() {
		return new Integer(this.height);
	}
	
	/**
	 * A 2-D Array of the vectors
	 */
	private Vector[][] vectors;
	public Vector copyVector(int x, int y) {
		if(this.vectors[y][x] != null) {
			return vectors[y][x].clone();
		} else {
			return null;
		}
	}
	private void setVector(int x, int y, Vector v) {
		this.vectors[y][x] = v;
	}
	
	/**
	 * @param seed used by random to get a seeded vf
	 * @param width the width of the canvas
	 * @param height the height of the canvas
	 */
	public RandomVectorField(long seed, int width, int height) {
		this.rand = new Random(seed); 
		this.width = width;
		this.height = height;
		this.vectors = new Vector[height][width];
		
		// iterate through rng positions
		for(int y = 0; y < height; y += RNG_SPACING) {
			for(int x = 0; x < width; x += RNG_SPACING) {
				// add rng vector
				double direction = rand.nextDouble() * 360;
				double magnitude = rand.nextDouble() * MAG_MAX;
				Vector add = new Vector(direction, magnitude);
				add.setRNG(true);
				this.setVector(x, y, add);
			}
		}
		// rng-vectors added
		
		// iterate through all positions
		for(int y1 = 0; y1 < height; y1++) {
			for(int x1 = 0; x1 < width; x1++) {
				Point p1 = new Point(x1, y1);
				// see if this position is a rng-vector
				if(copyVector(x1, y1) == null) {
					// create a non-rng vector
					// arraylist of segments for use with calculating this vector
					ArrayList<Segment> segs = new ArrayList<Segment>();
					// get all rng-vectors within RNG_SPACING
					// iterate through all rng positions
					for(int y2 = 0; y2 < height; y2 += RNG_SPACING) {
						for(int x2 = 0; x2 < width; x2 += RNG_SPACING) {
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
