package EcouteurEvenement;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Panel.PanelInfoProjet;
import Panel.PanelRessource;
import Ressource.Ressource;

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
	PanelInfoProjet pip;
	Ressource res;
	
	public SourisRessourceListener(PanelInfoProjet pip, Ressource res) {
		this.pip = pip;
		this.res = res;
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		pip.afficheInfoRessource(res);
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
