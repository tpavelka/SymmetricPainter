import java.awt.Point;

public class VFController {
	/**
	 * How large to up_scale the vf so that the brushes never<br>
	 * fall off the vf. Odd is better.<br>
	 */
	public static final int UP_SCALE = 5;
	/**
	 * The size of the canvas.<br>
	 */
	private double cwidth;
	private double cheight;

	/**
	 * Input screen coords, output multi-array coords.
	 */
	private Point calcOffset1(double x, double y) {
		// get the offset
		double offsetx = -(this.cwidth * UP_SCALE);
		double offsety = -(this.cheight * UP_SCALE);
		
		// get the vector x and y
		int vx = (int) (x + offsetx);
		int vy = (int) (y + offsety);
		
		return new Point(vx, vy);
	}
	/**
	 * Input multi-array coords, output screen coords.
	 */
	private Point calcOffset2(double x, double y) {
		// get the offset
		double offsetx = this.cwidth * UP_SCALE;
		double offsety = this.cheight * UP_SCALE;
		
		// get the vector x and y
		int vx = (int) (x + offsetx);
		int vy = (int) (y + offsety);
		
		return new Point(vx, vy);
	}
}
