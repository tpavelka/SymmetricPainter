import javax.swing.SwingUtilities;
/**
 * The args specify the number of symmbrushes.
 * @author Travis Pavelka
 *
 */
public class Main {
	public static void main(String[] args) {
		if(args.length == 1) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new SFrame(Integer.parseInt(args[0]));
				}
			});
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new SFrame();
				}
			});
		}
	}
}
