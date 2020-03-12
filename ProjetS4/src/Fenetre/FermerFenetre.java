package Fenetre;

import java.awt.Window;
import java.awt.event.*;

/**
 * permet la fermeture d'une fenetre via le bouton fermer de la fenetre
 * @author damien planchamp
 *
 */
public class FermerFenetre extends WindowAdapter {
	private Window w;
	
	public FermerFenetre(Window w) {
		this.w = w;
	}

	public void windowClosing(WindowEvent arg0) {
		w.dispose();
	}

}
