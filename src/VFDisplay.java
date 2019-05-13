
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
	
	private DisplayPanel display;
	private RandomVectorField rvf;
	
	public VFDisplay() {
		this.setTitle("VF Display");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(0, 0, 800, 800);
		
		this.rvf = new RandomVectorField(19980528L, 20, 20);
		this.display = new DisplayPanel(this.rvf);
		this.setContentPane(this.display);
		
		this.setVisible(true);
	}
	
	private class DisplayPanel extends JPanel {
		private RandomVectorField rvf;
		@Override
		public void paintComponent(Graphics g) {
			int spacing = 35;
			int x = 35;
			int y = 35;
			for(int vy = 0; vy < rvf.copyHeight(); vy++) {
				for(int vx = 0; vx < rvf.copyWidth(); vx++) {
					Vector v = rvf.copyVector(vx, vy);
					boolean rng = v.isRNG();
					if(rng) {
						g.setColor(Color.green);
					} else {
						g.setColor(Color.black);
					}
					double mag = v.copyMagnitude();
					double dir = v.copyDirection();
					Vector calc = new Vector(dir, mag);
					g.fillArc(x, y, 4, 4, 0, 360);
					g.drawLine(x, y, (int)(x + calc.copyX()), (int)(y + calc.copyY()));
					x += spacing;
				}
				x = 35;
				y += spacing;
			}
		}
		public DisplayPanel(RandomVectorField rvf) {
			this.setBackground(Color.red);
			this.rvf = rvf;
		}
	}
}
