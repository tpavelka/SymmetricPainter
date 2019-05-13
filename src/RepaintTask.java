import java.util.TimerTask;

import javax.swing.JComponent;
/**
 * Controller for painting to the screen.
 * @author Travis Pavelka
 *
 */
public class RepaintTask extends TimerTask {
	JComponent comp;
	
	@Override
	public void run() {
		this.comp.repaint();
	}
	
	public RepaintTask(JComponent comp) {
		this.comp = comp;
	}
}
