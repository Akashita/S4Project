package EcouteurEvenement;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Panel.PanelRessource;

/**
 * Cette classe permet l'interaction utilisateur machine avec la souris
 * pour pouvoir selectionner une ressource (presente dans la barre à gauche)
 * 
 * pour pouvoir afficher ses informations, la modifier ou la supprimer
 * 
 * @author damien planchamp
 *
 */
 public class SourisRessourceListener implements MouseListener {
	PanelRessource pr;
	JLabel label;
	
	public SourisRessourceListener(PanelRessource pr, JLabel label) {
		this.pr = pr;
		this.label = label;
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//pr.selectionnerRessource(label);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
