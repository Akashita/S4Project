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
			TICKET = 10, TICKETAJOUT = 11, TICKETSUPPRIME = 12, COMPETENCE = 13, COMPETENCEAJOUT = 14, COMPETENCESUPPRIME = 15, CALCULATEUR = 16, CALCULATEURAJOUT = 17,
			CALCULATEURSUPPRIME = 18,MATERIEL = 19, MATERIELAJOUT = 20, MATERIELSUPPRIME = 21,PROJET = 22, PROJETAJOUT = 23, PROJETSUPPRIME = 24,
			ACTIVITE = 25, ACTIVITEAJOUT = 26, ACTIVITESUPPRIME = 27, PARTICIPESALARIE = 28, PARTICIPESALARIEAJOUT = 29, PARTICIPESALARIESUPPRIME = 30,
			PARTICIPECALCUL = 31, PARTICIPECALCULAJOUT = 32, PARTICIPECALCULSUPPRIME = 33, PARTICIPESALLE = 34, PARTICIPESALLEAJOUT = 35, PARTICIPESALLESUPPRIME = 36,
			LISTEDOMAINE = 37, LISTEDOMAINEAJOUT = 38, LISTEDOMAINESUPPRIME = 39;
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
			new FenetreDebugTicket(entreprise,FenetreDebugTicket.AFFICHE);
		}
		else if (direction == TICKETAJOUT) {
			new FenetreDebugTicket(entreprise,FenetreDebugTicket.AJOUT);
		}
		else if (direction == TICKETSUPPRIME) {
			new FenetreDebugTicket(entreprise,FenetreDebugTicket.SUPPRIME);
		}
		else if (direction == COMPETENCE) {
			new FenetreDebugCompetence(entreprise,FenetreDebugTicket.AFFICHE);
		}
		else if (direction == COMPETENCEAJOUT) {
			new FenetreDebugCompetence(entreprise,FenetreDebugCompetence.AJOUT);
		}
		else if (direction == COMPETENCESUPPRIME) {
			new FenetreDebugCompetence(entreprise,FenetreDebugCompetence.SUPPRIME);
		}
		
		else if (direction == CALCULATEUR) {
			new FenetreDebugCalculateur(entreprise,FenetreDebugCalculateur.AFFICHE);
		}
		else if (direction == CALCULATEURAJOUT) {
			new FenetreDebugCalculateur(entreprise,FenetreDebugCalculateur.AJOUT);
		}
		else if (direction == CALCULATEURSUPPRIME) {
			new FenetreDebugCalculateur(entreprise,FenetreDebugCalculateur.SUPPRIME);
		}
		else if (direction == MATERIEL) {
			new FenetreDebugMateriel(entreprise,FenetreDebugMateriel.AFFICHE);
		}
		else if (direction == MATERIELAJOUT) {
			new FenetreDebugMateriel(entreprise,FenetreDebugMateriel.AJOUT);
		}
		else if (direction == MATERIELSUPPRIME) {
			new FenetreDebugMateriel(entreprise,FenetreDebugMateriel.SUPPRIME);
		}
		else if (direction == PROJET) {
			new FenetreDebugProjet(entreprise,FenetreDebugProjet.AFFICHE);
		}
		else if (direction == PROJETAJOUT) {
			new FenetreDebugProjet(entreprise,FenetreDebugProjet.AJOUT);
		}
		else if (direction == PROJETSUPPRIME) {
			new FenetreDebugProjet(entreprise,FenetreDebugProjet.SUPPRIME);
		}
		else if (direction == ACTIVITE) {
			new FenetreDebugActivite(entreprise,FenetreDebugActivite.AFFICHE);
		}
		else if (direction == ACTIVITEAJOUT) {
			new FenetreDebugActivite(entreprise,FenetreDebugActivite.AJOUT);
		}
		else if (direction == ACTIVITESUPPRIME) {
			new FenetreDebugActivite(entreprise,FenetreDebugActivite.SUPPRIME);
		}
		else if (direction == PARTICIPESALARIE) {
			new FenetreDebugParticipeSalarie(entreprise,FenetreDebugParticipeSalarie.AFFICHE);
		}
		else if (direction == PARTICIPESALARIEAJOUT) {
			new FenetreDebugParticipeSalarie(entreprise,FenetreDebugParticipeSalarie.AJOUT);
		}
		else if (direction == PARTICIPESALARIESUPPRIME) {
			new FenetreDebugParticipeSalarie(entreprise,FenetreDebugParticipeSalarie.SUPPRIME);
		}
		else if (direction == PARTICIPECALCUL) {
			new FenetreDebugParticipeCalcul(entreprise,FenetreDebugParticipeCalcul.AFFICHE);
		}
		else if (direction == PARTICIPECALCULAJOUT) {
			new FenetreDebugParticipeCalcul(entreprise,FenetreDebugParticipeCalcul.AJOUT);
		}
		else if (direction == PARTICIPECALCULSUPPRIME) {
			new FenetreDebugParticipeCalcul(entreprise,FenetreDebugParticipeCalcul.SUPPRIME);
		}
		else if (direction == PARTICIPESALLE) {
			new FenetreDebugParticipeSalle(entreprise,FenetreDebugParticipeSalle.AFFICHE);
		}
		else if (direction == PARTICIPESALLEAJOUT) {
			new FenetreDebugParticipeSalle(entreprise,FenetreDebugParticipeSalle.AJOUT);
		}
		else if (direction == PARTICIPESALLESUPPRIME) {
			new FenetreDebugParticipeSalle(entreprise,FenetreDebugParticipeSalle.SUPPRIME);
		}
		else if (direction == LISTEDOMAINE) {
			new FenetreDebugListeDomaine(entreprise,FenetreDebugListeDomaine.AFFICHE);
		}
		else if (direction == LISTEDOMAINEAJOUT) {
			new FenetreDebugListeDomaine(entreprise,FenetreDebugListeDomaine.AJOUT);
		}
		else if (direction == LISTEDOMAINESUPPRIME) {
			new FenetreDebugListeDomaine(entreprise,FenetreDebugListeDomaine.SUPPRIME);
		}
		w.dispose();

	}
}
