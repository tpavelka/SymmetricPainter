import java.util.ArrayList;
/**
 * Controller for calculating symmbrush movements.
 * @author Travis Pavelka
 *
 */
public class LogicThread extends Thread {
	private ArrayList<SymmBrush> symmbrushes;
	
	private long lasttime;
	private long diff;
	
	@Override
	public void run() {
		while(true) {
			this.diff = Math.abs(lasttime - System.nanoTime());
			
			for(SymmBrush brush: this.symmbrushes) {
				synchronized(brush) {
					brush.update(diff);
				}
			}
			
			this.lasttime = System.nanoTime();
		}
	}
	
	public LogicThread(ArrayList<SymmBrush> symmbrushes) {
		this.symmbrushes = symmbrushes;
		
		this.lasttime = System.nanoTime();
	}
}
