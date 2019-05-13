import java.awt.Point;

/**
 * Has a direction and a magnitude. (degrees)
 * @author Travis Pavelka
 *
 */
public class Vector {
	private boolean isRNG;
	public boolean isRNG() {
		return this.isRNG;
	}
	public void setRNG(boolean b) {
		this.isRNG = b;
	}
	
	private double direction;
	public Double copyDirection() {
		return new Double(this.direction);
	}
	public void setDirection(double dir) {
		this.direction = dir;
		this.calcComps();
	}
	
	private double magnitude;
	public Double copyMagnitude() {
		return new Double(this.magnitude);
	}
	public void setMagnitude(double mag) {
		this.magnitude = mag;
		this.calcComps();
	}
	
	private Tuple components;
	public Double copyX() {
		return new Double(this.components.copyX());
	}
	public  void setX(double x) {
		this.components.setX(x);
		this.calcDirMag();
	}
	public Double copyY() {
		return new Double(this.components.copyY());
	}
	public void setY(double y) {
		this.components.setY(y);
		this.calcDirMag();
	}
	
	private void calcComps() {
		double x = this.magnitude * Math.cos(this.direction);
		double y = this.magnitude * Math.sin(this.direction);
		this.components.setX(x);
		this.components.setY(y);
	}
	private void calcDirMag() {
		// get req info
		Point p1 = new Point(0, 0);
		double x = this.copyX();
		double y = this.copyY();
		if(x == 0 && y == 0) {
			this.direction = 0;
			this.magnitude = 0;
		} else {
			// calc direction
			double dir = toDegreesSTD(Math.atan(x/y));
			this.setDirection(dir);
			// calc magnitude
			double mag = p1.distance(x, y);
			this.setMagnitude(mag);
		}
	}
	
	/**
	 * Adds vector v to this vector
	 */
	public void addVector(Vector v) {
		double x = this.copyX() + v.copyX();
		double y = this.copyY() + v.copyY();
		this.components.setX(x);
		this.components.setY(y);
		this.calcDirMag();
	}
	
	/**
	 * Returns radians as a double degree value between 0-359.999
	 */
	public static Double toDegreesSTD(double radians) {
		double degrees = (radians * 180) % 360;
		if(degrees < 0) {
			degrees = 360 + degrees;
		}
		return new Double(degrees);
	}
	
	@Override
	public Vector clone() {
		double direction = this.copyDirection();
		double magnitude = this.copyMagnitude();
		
		Vector copy = new Vector(direction, magnitude);
		copy.setRNG(this.isRNG);
		
		return copy;
	}
	
	/**
	 * Create a vector from vector components.
	 */
	public Vector(int xcomp, int ycomp) {
		this.components = new Tuple(0, 0);
		this.components.setX(xcomp);
		this.components.setY(ycomp);
		this.calcDirMag();
	}
	/**
	 * Create a vector from dir and mag.
	 */
	public Vector(double dir, double mag) {
		this.components = new Tuple(0, 0);
		this.direction = dir;
		this.magnitude = mag;
		this.calcComps();
	}
}
