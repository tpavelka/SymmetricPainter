
import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PaintPanel extends JPanel {
	/**
	 * The distance between random vectors in the
	 * generated vector feild. Higher numbers mean
	 * further distance.
	 */
	private final int RNG_SPACING = 500;
	private final int NUMBRUSH = 4000;
	
	private SFrame sframe;
	private ArrayList<SymmBrush> symmbrushes;
	private LogicThread logic;
	private Timer paint;
	
	@Override
	public void paintComponent(Graphics g) {
		// remove the lower line for a cool painting effect
		super.paintComponent(g);
		
		// paint each brush
		for(SymmBrush brush: this.symmbrushes) {
			brush.paintBrush(g);
		}
	}
	
	/**
	 * Stops the repainter thread and the logic thread
	 */
	public void stop() {
		try {
			SwingUtilities.invokeAndWait(
					new Runnable() {
						@Override
						public void run() {
							paint.cancel();
							PaintPanel panelnew = new PaintPanel(sframe);
							sframe.setContentPane(panelnew);
						}
					});
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public PaintPanel(SFrame sframe) {
		// catch the sframe reference
		this.sframe = sframe;
		
		// get the screen size
		int width = getToolkit().getScreenSize().width;
		int height = getToolkit().getScreenSize().height;
		
		// JPanel settings
		this.setSize(width, height);
		this.setBackground(Color.black);
		
		// Vector Field settings
		Random rand = new Random();
		long seed = rand.nextInt();
		
		// generate the vector field
		RandomLinearVF rlvf = new RandomLinearVF(seed, RNG_SPACING, width, height);
		
		// create the brushes
		this.symmbrushes = new ArrayList<SymmBrush>();
		for(int i = 0; i < NUMBRUSH; i++) {
			this.symmbrushes.add(new SymmBrush(rlvf, width, height));
		}
		
		// create the logic thread for brush positions and colors
		logic = new LogicThread(this, this.symmbrushes);
		
		// start the logic thread
		logic.start();
		
		// 62.5 Hz = 16
		// schedule repainting
		paint = new Timer();
		paint.schedule(new Repainter(this), 0, 1);
	}
}
