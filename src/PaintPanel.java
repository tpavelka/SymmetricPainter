import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JPanel;
/**
 * The JPanel that the symbrushes paint to.
 * @author Travis Pavelka
 *
 */
public class PaintPanel extends JPanel {
	private ArrayList<SymmBrush> symmbrushes;
	
	@Override
	public void paintComponent(Graphics g) {
		for(SymmBrush brush: this.symmbrushes) {
			synchronized(brush) {
				brush.paintComponent(g);
			}
		}
	}
	
	public PaintPanel(double width, double height, int numbrush) {
		this.setBackground(Color.BLACK);
		
		long seed = 19980528L;
		RandomVectorField rvf = new RandomVectorField(seed, width, height);
		
		this.symmbrushes = new ArrayList<SymmBrush>();
		for(int i = 0; i < numbrush; i++) {
			this.symmbrushes.add(new SymmBrush(width, height, rvf));
		}
		
		Thread logic = new LogicThread(this.symmbrushes);
		logic.start();
		
		Timer timer = new Timer();
		timer.schedule(new RepaintTask(this), 0, 10);
	}
}
