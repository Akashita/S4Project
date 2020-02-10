
import java.awt.Window;
import java.awt.event.*;

public class FermerFenetre extends WindowAdapter {
	private Window w;
	
	public FermerFenetre(Window w) {
		this.w = w;
	}

	public void windowClosing(WindowEvent arg0) {
		w.dispose();
	}

}
