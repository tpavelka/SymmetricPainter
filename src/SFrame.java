import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
/**
 * The JFrame that contains the PaintPanel.
 * @author Travis Pavelka
 *
 */
public class SFrame extends JFrame {
	private PaintPanel paintpanel;
	
	private int numbrush;
	
	public void init() {
		double width = this.getContentPane().getWidth();
		double height = this.getContentPane().getHeight();
		this.paintpanel = new PaintPanel(width, height, this.numbrush);
		this.setContentPane(this.paintpanel);
	}
	
	public SFrame() {
		this.numbrush = 2;

		InitThread waiter = new InitThread(this);
		waiter.start();
		this.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if(e.getNewState() == JFrame.MAXIMIZED_BOTH) {
					waiter.notifyStateChange();
				}
			}
		});
		
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBounds(0, 0, 400, 400);
		this.setVisible(true);
	}
	public SFrame(int numbrush) {
		this.numbrush = numbrush;
		
		InitThread waiter = new InitThread(this);
		waiter.start();
		this.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if(e.getNewState() == JFrame.MAXIMIZED_BOTH) {
					waiter.notifyStateChange();
				}
			}
		});
		
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBounds(0, 0, 400, 400);
		this.setVisible(true);
	}
}
