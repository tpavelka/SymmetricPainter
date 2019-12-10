
import java.util.TimerTask;

import javax.swing.JComponent;

public class Repainter extends TimerTask {
	private JComponent comp;
	
	@Override
	public void run() {
		comp.repaint();
	}
	
	public Repainter(JComponent comp) {
		this.comp = comp;
	}
}
