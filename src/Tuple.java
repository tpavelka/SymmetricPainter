/**
 * @author Travis Pavelka
**/
public class Tuple {
	Double x;
	public Double copyX() {
		return new Double(this.x);
	}
	public void setX(double x) {
		this.x = x;
	}
	
	Double y;
	public Double copyY() {
		return new Double(this.y);
	}
	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public Tuple clone() {
		Double cpyx = this.copyX();
		Double cpyy = this.copyY();
		
		Tuple copy = new Tuple(cpyx, cpyy);
		return copy;
	}
	
	@Override
	public String toString() {
		return "Tuple["+x.toString()+", "+y.toString()+"]";
	}
	
	public Tuple(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
