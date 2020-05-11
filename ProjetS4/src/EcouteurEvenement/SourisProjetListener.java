package EcouteurEvenement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Model.Entreprise;
import Model.Projet;

public class SourisProjetListener implements MouseListener {

	private Entreprise entreprise;
	private Projet projet;
	
	public SourisProjetListener(Entreprise entreprise, Projet projet) {
		this.entreprise = entreprise;
		this.projet = projet;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		entreprise.selectionnerProjet(projet);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
