package DebugFenetre;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Entreprise;



public class NavigationListener  implements ActionListener{
	private Window w;
	private int direction;
	private Entreprise entreprise;
	public static int PERSONNE = 0, MENU = 1, SALLE = 2, SALLEAJOUT = 3, SALLESUPPRIME = 4,DOMAINE = 5, DOMAINEAJOUT = 6, DOMAINESUPPRIME = 7, PERSONNEAJOUT = 8, PERSONNESUPPRIME = 9,
			TICKET = 10, TICKETAJOUT = 11, TICKETSUPPRIME = 12;
	public NavigationListener(Window w,Entreprise entreprise, int direction) {
		this.w = w;
		this.direction = direction;
		this.entreprise = entreprise;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (direction == PERSONNE){
			new FenetreDebugPersonne(entreprise,FenetreDebugPersonne.AFFICHE);
		
		}
		else if (direction == PERSONNEAJOUT) {
			new FenetreDebugPersonne(entreprise,FenetreDebugPersonne.AJOUT);
		}
		else if (direction == PERSONNESUPPRIME) {
			new FenetreDebugPersonne(entreprise,FenetreDebugPersonne.SUPPRIME);
		}
		
		else if (direction == MENU) {
			new FenetreDebugBDD(entreprise);
		}
		else if (direction == SALLE) {
			new FenetreDebugSalle(entreprise,FenetreDebugSalle.AFFICHE);
		}
		else if (direction == SALLEAJOUT) {
			new FenetreDebugSalle(entreprise,FenetreDebugSalle.AJOUT);
		}
		else if (direction == SALLESUPPRIME) {
			new FenetreDebugSalle(entreprise,FenetreDebugSalle.SUPPRIME);
		}
		else if (direction == DOMAINE) {
			new FenetreDebugDomaine(entreprise,FenetreDebugDomaine.AFFICHE);
		}
		else if (direction == DOMAINEAJOUT) {
			new FenetreDebugDomaine(entreprise,FenetreDebugDomaine.AJOUT);
		}
		else if (direction == DOMAINESUPPRIME) {
			new FenetreDebugDomaine(entreprise,FenetreDebugDomaine.SUPPRIME);
		}
		
		else if (direction == TICKET) {
			new FenetreDebugDomaine(entreprise,FenetreDebugTicket.AFFICHE);
		}
		else if (direction == TICKETAJOUT) {
			new FenetreDebugDomaine(entreprise,FenetreDebugTicket.AJOUT);
		}
		else if (direction == TICKETSUPPRIME) {
			new FenetreDebugDomaine(entreprise,FenetreDebugTicket.SUPPRIME);
		}
		w.dispose();

	}
}
