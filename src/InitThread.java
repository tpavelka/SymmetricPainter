/**
 * Waits to get the size of the content pane when maximized.
 * @author Travis Pavelka
 *
 */
public class InitThread extends Thread {
	private SFrame sframe;
	
	private boolean can_init;
	public void notifyStateChange() {
		this.can_init = true;
	}
	
	@Override
	public void run() {
		while(true) {
			if(this.can_init && this.sframe.isVisible() == true) {
				this.sframe.init();
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public InitThread(SFrame sframe) {
		this.sframe = sframe;
		this.can_init = false;
	}
}
