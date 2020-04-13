package EcouteurEvenement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Activite;
import Model.Entreprise;

public class KeyActiviteListener implements KeyListener {

	private Entreprise entreprise;

	public KeyActiviteListener(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Activite activite = entreprise.getActiviteSelectionner();
		if (!activite.getChangeSens()) {
			activite.setChangeSens(true);
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				entreprise.modifieListeActivite(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				entreprise.modifieListeActivite(false);
			}			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Activite activite = entreprise.getActiviteSelectionner();
		activite.setChangeSens(false);
	}

}
