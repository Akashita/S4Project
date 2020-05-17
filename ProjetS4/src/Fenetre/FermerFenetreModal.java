package Fenetre;

import java.awt.Window;
import java.awt.event.*;


/**
 * permet la fermeture d'une fenetre via le bouton fermer de la fenetre modal
 * @author damien planchamp
 *
 */
public class FermerFenetreModal extends WindowAdapter {
	private Window w;
	
	public FermerFenetreModal(Window w) {
		this.w = w;
	}

	public void windowClosing(WindowEvent arg0) {


		w.dispose();
	}

}
