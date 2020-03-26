package EcouteurEvenement;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Fenetre.FenetreEmploiDuTemps;
import Panel.PanelProjet;

/**
 * Cette classe permet l'interaction utilisateur machine avec la souris
 * pour pouvoir changer de semaine dans l'emploi du temps
 * 
 * @author damien planchamp
 *
 */
public class SourisSemaineListener implements MouseListener {
	FenetreEmploiDuTemps fenetreEDT;
	JLabel label;
	
	public SourisSemaineListener(FenetreEmploiDuTemps fenetreEDT, JLabel label) {
		this.fenetreEDT = fenetreEDT;
		this.label = label;
	}
	

	/**
	 * lorsqu'on clique sur un label de projet (barre du bas), 
	 * on informe au panel qui gere les projets qu'un autre projet à été selectionné
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) { 
		fenetreEDT.selectionnerProjet(label);
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
