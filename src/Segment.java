import java.awt.Point;

public class Segment {
	private Point p1;
	public Point copyMainPoint() {
		return new Point(this.p1);
	}
	public void setMainPoint(Point p1) {
		this.p1 = p1;
		this.reset();
	}
	
	private Point p2;
	public Point copySecondaryPoint() {
		return new Point(this.p2);
	}
	public void setSecondaryPoint(Point p2) {
		this.p2 = p2;
		this.reset();
	}
	
	private double dist;
	private void calcDist() {
		this.dist = p1.distance(p2);
	}
	public Double copyDist() {
		return new Double(this.dist);
	}
	
	private Double slope;
	private void calcSlope() {
		double rise = p2.getY() - p1.getY();
		double run = p2.getX() - p1.getX();
		
		if(run == 0) {
			this.slope = null;
		} else {
			this.slope = rise/run;
		}
	}
	public Double copySlope() {
		return new Double(this.slope);
	}
	
	private void reset() {
		this.calcDist();
		this.calcSlope();
	}
	
	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		
		this.reset();
	}
}
