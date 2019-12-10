import java.util.ArrayList;

public class LogicThread extends Thread {
	/**
	 * The max iteration to switch to a new PaintPanel
	 * with a new vector field.
	 */
	private final long RESET_ITERATION = 4000;
	
	
	
	/**
	 * A reference to the paintpanel.
	 */
	private PaintPanel panel;
	/**
	 * A reference to the arraylist of symmbrushes.
	 */
	private ArrayList<SymmBrush> symmbrushes;
	/**
	 * The last iteration's system time.
	 */
	private long lasttime;
	
	/**
	 * Calculate the new positions and color for all brushes
	 */
	@Override
	public void run() {
		// to exit the while loop
		boolean loop = true;
		
		// start lasttime
		this.lasttime = System.nanoTime();
		
		// lasttime - current time
		long diff;
		
		// the current iteration
		long iteration = 0;
		
		// logic until exit
		while(loop) {
			// calc diff
			diff = Math.abs(lasttime - System.nanoTime());
			
			// reset lasttime to now
			this.lasttime = System.nanoTime();
			
			// use diff to calc brush logic
			for(SymmBrush brush: this.symmbrushes) {
				brush.update(diff);
			}
			
			// ice the processor
			try {
				this.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// test if ready to reset paintpanel
			if(iteration >= this.RESET_ITERATION) {
				panel.stop();
				loop = false;
			}
			
			// increment iteration
			iteration++;
		}
	}
	
	/**
	 * LogicThread Constructor
	 * @param symmbrushes
	 */
	public LogicThread(PaintPanel panel, ArrayList<SymmBrush> symmbrushes) {
		// name the thread
		this.setName("Logic Thread");
		
		// catch the paint panel reference
		this.panel = panel;
		
		// catch the symmbrushes reference
		this.symmbrushes = symmbrushes;
	}
}
