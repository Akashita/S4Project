package EcouteurEvenement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

import Fenetre.FenetreEmploiDuTemps;
import Ressource.Plage;
import Ressource.Ressource;

/**
 * Cette classe permet l'interaction utilisateur machine avec l'emploi du temps
 * pour pouvoir generer une nouvelle plage horaire pour une ressource
 * 
 * @author damien planchamp
 *
 */

public class SourisEmploieDuTempsListener  implements MouseListener{
	private FenetreEmploiDuTemps p;
	private Ressource ressource;
	private String nomProjet;
	private String date;
	private int jourHomme;
	private int pourcent;
	
	public SourisEmploieDuTempsListener(FenetreEmploiDuTemps p, Ressource ressource, String nomProjet, String date, int jourHomme, int pourcent) {
		this.p = p;
		this.ressource = ressource;
		this.nomProjet = nomProjet;
		this.date = date;
		this.jourHomme = jourHomme;
		this.pourcent = pourcent;
	}

	/**
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Plage plage = new Plage(nomProjet, date, jourHomme, pourcent);
		ressource.addPlage(plage);
		p.dispose();
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
