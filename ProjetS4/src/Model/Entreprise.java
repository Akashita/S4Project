package Model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JPanel;

import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.RessourceAutre;
import Ressource.Salle;


//model il sert a cr�er des projets puis leur donne des ressources.

public class Entreprise extends Observable{
		private ArrayList<Projet> listeProjet;//liste qui contient tous les projets de l'entreprise
		private ArrayList<String> listeType;//liste qui contient tous les types de ressourceAutre qui ont d�j� �t� cr�e pour les r�utiliser
		private ArrayList<Ressource> listeRessource;//liste de toutes les differentes ressources de l’entrepris
		private int idCour;//id des ressources
		private int idAct; //id des activités
		private ArrayList<JPanel> listePanel = new ArrayList<JPanel>();

		//cr�ation de l'entreprise unique il faudra lui ajouter un nom si on d�sire �tendre nos activit�s
		public Entreprise() {
			this.listeProjet =  new ArrayList<Projet>();
			this.listeType =  new ArrayList<String>();
			this.listeRessource =  new ArrayList<Ressource>();
			this.idCour = 0;
			
		}
		
		//classe de base qui permettent de voir la cha�ne et r�cup�rer les infos de la classe
		@Override
		public String toString() {
			String chaineActProjet = "Voici la liste des projets ainsi que leurs activites : ";
			for (int i = 0; i < this.listeProjet.size(); i++) {
				chaineActProjet += this.listeProjet.get(i).toString(); 
				
			}
			return chaineActProjet;
		}
		
		public void incrementId (){ //fonction a utiliser sur chaque nouvelle ressource pour leur attribuer un iD
			this.idCour = this.idCour +1 ;
		}
		
		public int getId() {
			return this.idCour;
		}
		
		public Projet getDernierProjet() { //retourne le dernier projet creer, pour PanelProjet
			return listeProjet.get(listeProjet.size()-1);
		}
		
		public ArrayList<Projet> getListeProjet(){
			return listeProjet;
		}


		public Projet getProjetSelectionner() {
			Projet projet = null;
			for (int i=0; i<listeProjet.size();i++) {
				if (listeProjet.get(i).getSelectionner()) {
					projet = listeProjet.get(i);
				}
			}
			return projet;
		}
		
		public void selectionnerProjet(String nom) {
			for (int i=0; i<listeProjet.size(); i++) {
				Projet projet = listeProjet.get(i);
				if (projet.getNom() == nom) {
					projet.selectionner();
				}
			}
			update();
		}
		
		public void deselectionnerProjet() { //utile pour le graphique
			for (int i=0; i<listeProjet.size(); i++) {
				listeProjet.get(i).deselectionner();
			}
		}
		
		public JPanel getPanelDuProjet() {
			JPanel panel = null;
			for (int i=0; i<listeProjet.size();i++) {
				if (listeProjet.get(i).getSelectionner()) {
					panel = listePanel.get(i);
				}
			}
			return panel;
		}
		
		public void selectionnerActivite(int id) {
			Projet projet = getProjetSelectionner();
			ArrayList<Activite> listeAct = projet.getListe();
			for (int i=0; i<listeAct.size(); i++) {
				Activite act = listeAct.get(i);
				if (act.getId() == id) {
					act.selectionner();
				}
			}
			update();
		}
		
		public void deselectionnerActivite() { //utile pour le graphique
			ArrayList<Activite> listeAct = getProjetSelectionner().getListe();
			for (int i=0; i<listeAct.size(); i++) {
				listeAct.get(i).deselectionner();
			}
		}

		public Activite getActiviteSelectionner() {
			//Projet projet = getProjetSelectionner();
			ArrayList<Activite> listeAct = getProjetSelectionner().getListe();
			Activite act = null;
			for (int i=0; i<listeAct.size();i++) {
				if (listeAct.get(i).getSelectionner()) {
					act = listeAct.get(i);
				}
			}
			return act;
		}

		public ArrayList<Ressource> getListeRessourceType(String type){
			ArrayList<Ressource> nouvelleListe = new ArrayList<Ressource>();
			for (int i=0; i<listeRessource.size(); i++) {
				Ressource ressource = listeRessource.get(i);
				if(ressource.getType() == type) {
					nouvelleListe.add(ressource);
				}
			}
			return nouvelleListe;
		}
				
		public void ajouterRessource(Ressource resCour) {
			this.listeRessource.add(resCour);
		}
		
		public void supprimerRessource(int idRessource) {
			
			int[] place = this.chercherRessource(idRessource);
			if (place[0] == 1) {
				int rangRessource = place[1];
				Ressource resCour = this.listeRessource.get(rangRessource);
				this.listeRessource.remove(resCour);
			}
			
		}
		
		public int[] chercherRessource(int idCour) {
			Boolean pasTrouve = true;
			int[] res = {0,0};//a droite la place du projet cherch� et a gauche si il est trouv� 0 non/1 oui
			if (this.listeRessource.size()==0) {
				return res;
			}
			else {
				do{
					if (this.listeRessource.get(res[1]).getId() == idCour) {
						res[0] = 1;
						pasTrouve = false;
					}
					else {
						res[1] = res[1] + 1;
					}
				}
				while((pasTrouve) && (res[1] < this.listeRessource.size()));
				return res;
			}
		}	
		
		public int[] chercheProjet(String nomProjet) {
			
			Boolean pasTrouve = true;//sert a sortir plus vite de la boucle
			int[] res = {0,0};//a droite la place du projet cherch� et a gauche si il est trouv� 0 non/1 oui
			
			if (this.listeProjet.size()== 0) {//si l'arrayList est vide il n'y a pas d�j� ce projet.
				
				return res;
			}
			else {
				
				do{
					if (this.listeProjet.get(res[1]).getNom() == nomProjet) {
						res[0] = 1;
						pasTrouve = false;
					}
					else {
						res[1] = res[1] + 1; //on incr�mente res pour acc�der � chercher plus loin.
					}
					
				}
				while((pasTrouve) && (res[1] < this.listeProjet.size()));
				return res ;
			}
			
		}
		
		//m�thode pour rajouter un type de RessourceAutre
				public void nouvTypeRessource(String nouvType) {
					Boolean pasTrouve = true;//sert a sortir plus vite de la boucle
					int i = 0;
					if (this.listeType.size()== 0) {//si l'arrayList est vide il n'y a pas d�j� ce projet.
						this.listeType.add(nouvType);

					}
					else {
						
						do{
							if (this.listeType.get(i) == nouvType) {//teste si le nom est d�j� pr�sent dans les types de ressources
								pasTrouve = false;//sort de la boucle sans rien faire
							}
							else {
								i++; //on incr�mente i pour acc�der � chercher plus loin.
							}
							
						}
						while((pasTrouve) && (i < this.listeType.size()));
						this.listeType.add(nouvType);
					}

				}
								
		//fonctions de cr�ations d'�l�ments de l'entreprise, les ressources ainsi que les projets
		//les m�thodes sont doubl�s -> direct dans un projet ou dans l'entreprise
		
		public void creerProjet(String nom) {//cr�e un projet si son nom n'est pas d�j� utilis�
			Projet newProjet = new Projet(nom);
			if (this.chercheProjet(newProjet.getNom())[0] == 0) {
				this.listeProjet.add(newProjet);
				newProjet.selectionner();
				this.listePanel.add(new JPanel());
			}
			update();
		}

		
		public void creerActivite(Projet projet, String titre, int charge, String ordre, LocalDate debut) {
			this.idAct++;
			Activite act = new Activite(idAct, titre, charge, ordre, debut);
			act.selectionner();
			projet.ajouter(act);
			update();
		}
		
		public void nouvPersonne (String nom, String prenom/*, String role*/) {
			//Personne nouvPersonne = new Personne(nom,prenom,role, this.idCour);
			Personne nouvPersonne = new Personne(nom,prenom, this.idCour);
			this.incrementId();
			this.ajouterRessource(nouvPersonne);
			update();

		}
		
		
		public void nouvSalle (String nom, int capacite) {
			Salle nouvSalle = new Salle(this.idCour,nom, capacite);
			this.incrementId();
			this.ajouterRessource(nouvSalle);
			update();

		}
		
		
		public void nouvRessourceAutre (String nom,String type) {
			RessourceAutre nouvRessourceAutre = new RessourceAutre(nom, type, this.idCour);
			this.incrementId();
			this.ajouterRessource(nouvRessourceAutre);
			this.nouvTypeRessource(type);//ajout du type a la liste de type personnalisable

			update();

		}
		
		public void nouvCalculateur (String nom) {
			Calculateur nouvCalculateur = new Calculateur(nom, this.idCour);
			this.incrementId();
			this.ajouterRessource(nouvCalculateur);
			update();
		}
		
		public void ajouterRessourceActivite(Ressource res) {
			Activite act = getActiviteSelectionner();
			act.ajouterRessource(res);
			update();
		}
			
		public void update() {
			this.setChanged();
			this.notifyObservers();	
		}
}
