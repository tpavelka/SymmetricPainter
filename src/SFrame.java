
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SFrame extends JFrame {
	
	/**
	 * Create the JFrame to contain the paintings.
	 */
	public SFrame() {
		// get the screen size
		int width = getToolkit().getScreenSize().width;
		int height = getToolkit().getScreenSize().height;
		
		// create a paintpanel in the frame
		PaintPanel paintpanel = new PaintPanel(this);
		
		// add that panel to the content pane
		this.setContentPane(paintpanel);
		
		// set the settings of the JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, width, height);
		this.setUndecorated(true);
		
		// make the JFrame visible
		this.setVisible(true);
	}
	
	/**
	 * The main method.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					new SFrame();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
