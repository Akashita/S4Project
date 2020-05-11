package EcouteurEvenement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Model.Activite;
import Model.Entreprise;
import Panel.PanelInfoActivite;

public class SourisActiviteListener implements MouseListener{

	private Entreprise entreprise;
	private Activite activite;
	
	public SourisActiviteListener(Entreprise entreprise, Activite activite) {
		this.entreprise = entreprise;
		this.activite = activite;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		/*if (e.getClickCount()==2) {
			entreprise.afficheEDTActivite(activite);
		}*/
		entreprise.selectionnerActivite(activite);
		System.out.println(entreprise.getProjetSelectionner().getNom()+" - "+entreprise.getActiviteSelectionner().getTitre());
	}

	@Override
	public void mousePressed(MouseEvent e) {
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
