package EcouteurEvenement;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Activite;
import Panel.PanelInfoProjet;
import Panel.PanelProjet;

/**
 * Cette classe permet l'interaction utilisateur machine avec la souris
 * pour pouvoir changer d'activité
 * 
 * @author damien planchamp
 *
 */
public class SourisActiviteListener implements MouseListener {
	PanelInfoProjet pip;
	Activite act;
	
	public SourisActiviteListener(PanelInfoProjet pip, Activite act) {
		this.pip = pip;
		this.act = act;
	}
	

	/**
	 * lorsqu'on clique sur un panel d'activité (barre du bas), 
	 * on informe au panel qui gere les d'activité qu'un autre projet à été selectionné
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) { 
		pip.activiteSelectionner(act);
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
