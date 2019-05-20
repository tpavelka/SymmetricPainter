
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VFDisplay extends JFrame {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VFDisplay();
			}
		});
	}
	
	public VFDisplay() {
		this.setTitle("VF Display");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(100, 0, 1200, 750);
		
		DisplayPanel display = new DisplayPanel();
		this.setContentPane(display);
		
		this.setVisible(true);
	}
	
	private class DisplayPanel extends JPanel {
		private RandomLinearVF rlvf;
		
		@Override
		public void paintComponent(Graphics g) {
			int spacing = 15;
			int x = 10;
			int y = 10;
			for(int vy = 0; vy < rlvf.getHeight(); vy++) {
				for(int vx = 0; vx < rlvf.getWidth(); vx++) {
					Vector v = rlvf.getVector(vx, vy);
					boolean rng = v.isRNG();
					if(rng) {
						g.setColor(Color.red);
					} else {
						g.setColor(Color.blue);
					}
					g.fillArc(x, y, 4, 4, 0, 360);
					g.drawLine(x, y, (int)(x + v.getX()), (int)(y + v.getY()));
					x += spacing;
				}
				x = 10;
				y += spacing;
			}
		}
		
		public DisplayPanel() {
			// 19980528L
			// 20011220L
			// 19720824L
			// 19750622L
			rlvf = new RandomLinearVF(20011220L, 79, 47);
		}
	}
}
