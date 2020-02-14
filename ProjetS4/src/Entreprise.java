import  java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JPanel;


//model il sert a cr�er des projets puis leur donne des ressources.

public class Entreprise extends Observable{
		private ArrayList<Projet> listeProjet;//liste qui contient tous les projets de l'entreprise
		private ArrayList<String> listeType;//liste qui contient tous les types de ressourceAutre qui ont d�j� �t� cr�e pour les r�utiliser
		private ArrayList<Ressource> listeRessource;//liste qui contient 
		private int idCour;//id des ressources
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
			String chaineRessourceProjet = "voici la liste des projets ainsi que leurs ressources : ";
			for (int i = 0; i < this.listeProjet.size(); i++) {
				chaineRessourceProjet += this.listeProjet.get(i).toString(); 
				
			}
			chaineRessourceProjet += ". \n Liste des Ressource de l'entreprise et leurs disponibilit�s : ";

			for (int i = 0; i < this.listeRessource.size(); i++) {
				Ressource resCour;
				chaineRessourceProjet += " \n";
				resCour =  this.listeRessource.get(i);
				chaineRessourceProjet += resCour.getNom() + "    ---disponible : ";
				chaineRessourceProjet += resCour.getDispo() + "    ---matricule : ";
				chaineRessourceProjet += resCour.getId() + ".";
			}
			
			chaineRessourceProjet += ".  \nIl y a aussi ces types disponibles : ";
			
			for (int i = 0; i < this.listeType.size(); i++) {
				chaineRessourceProjet += this.listeType.get(i).toString(); 
				chaineRessourceProjet += ", ";
			}
			
			
			return chaineRessourceProjet;
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
		
		public ArrayList<Ressource> getRessource(){
			return listeRessource;
		}
		
		public void ajouterRessource(Ressource resCour) {
			this.listeRessource.add(resCour);
		}
		
		public void supprimerRessource(int idRessource) {
			
			int[] place = this.chercherRessource(idRessource);
			if (place[0] == 1) {
				int rangRessource = place[1];
				Ressource resCour = this.listeRessource.get(rangRessource);

				if (resCour.getDispo() == true) {//on enleve la ressource si elle est disponible 
					this.listeRessource.remove(resCour);

						}
				
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
		
		//m�thode pour cr�er des ressources et les attribuer a des projets
		public void nouvPersonne (String nomProjet, String nom, String prenom, String role) {
			Personne nouvPersonne = new Personne(nom,prenom,role, this.idCour);
			int [] place = 	this.chercheProjet(nomProjet);
			this.incrementId();
			this.ajouterRessource(nouvPersonne);
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				Projet projetCour = this.listeProjet.get(place[1]);
				projetCour.ajouter(nouvPersonne);	
				nouvPersonne.rendIndisponible();
				nouvPersonne.setProjet(projetCour);

				
			}
			else {
				nouvPersonne.rendDisponible();//si le nom du projet est mal �crit ou faux la ressource vas dans la liste de ressource disponible

			}
		}
		
		public void nouvPersonne (String nom, String prenom, String role) {
			Personne nouvPersonne = new Personne(nom,prenom,role, this.idCour);
			this.incrementId();
			this.ajouterRessource(nouvPersonne);
			nouvPersonne.rendDisponible();
		}
		
		public void nouvSalle (String nomProjet, String nom, int capacite) {
			Salle nouvSalle = new Salle(this.idCour,nom, capacite);
			int [] place = 	this.chercheProjet(nomProjet);
			this.incrementId();
			this.ajouterRessource(nouvSalle);
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				Projet projetCour = this.listeProjet.get(place[1]);
				projetCour.ajouter(nouvSalle);	
				nouvSalle.rendIndisponible();
				nouvSalle.setProjet(projetCour);
			}
			else {
				nouvSalle.rendDisponible();//si le nom du projet est mal �crit ou faux la ressource vas dans la liste de ressource disponible

			}
		}
		
		public void nouvSalle (String nom, int capacite) {
			Salle nouvSalle = new Salle(this.idCour,nom, capacite);
			this.incrementId();
			this.ajouterRessource(nouvSalle);
			nouvSalle.rendDisponible();
		}
		
		//m�thode de cr�ation des RessourceAutre personnalisable et qui permettent de cr�er des types diff�rents
		public void nouvRessourceAutre (String nomProjet, String nom,String type) {
			RessourceAutre nouvRessourceAutre = new RessourceAutre(nom, type, this.idCour);
			int [] place = 	this.chercheProjet(nomProjet);
			this.incrementId();
			this.ajouterRessource(nouvRessourceAutre);
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				Projet projetCour = this.listeProjet.get(place[1]);
				projetCour.ajouter(nouvRessourceAutre);	
				nouvRessourceAutre.rendIndisponible();
				nouvRessourceAutre.setProjet(projetCour);
				this.nouvTypeRessource(type);//ajout du type a la liste de type personnalisable
			}
			else {
				nouvRessourceAutre.rendDisponible();//si le nom du projet est mal �crit ou faux la ressource vas dans la liste de ressource disponible

			}
		}
		
		public void nouvRessourceAutre (String nom,String type) {
			RessourceAutre nouvRessourceAutre = new RessourceAutre(nom, type, this.idCour);
			this.incrementId();
			this.ajouterRessource(nouvRessourceAutre);
			nouvRessourceAutre.rendDisponible();
			this.nouvTypeRessource(type);//ajout du type a la liste de type personnalisable


		}
		
		public void nouvCalculateur(String nomProjet, String nom) {
			Calculateur nouvCalculateur = new Calculateur(nom, idCour);
			int [] place = 	this.chercheProjet(nomProjet);
			this.incrementId();
			this.ajouterRessource(nouvCalculateur);
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				Projet projetCour = this.listeProjet.get(place[1]);
				projetCour.ajouter(nouvCalculateur);	
				nouvCalculateur.rendIndisponible();
				nouvCalculateur.setProjet(projetCour);
			}
			else {
				nouvCalculateur.rendDisponible();//si le nom du projet est mal �crit ou faux la ressource vas dans la liste de ressource disponible

			}
			
		}
		
		public void nouvCalculateur (String nom) {
			Calculateur nouvCalculateur = new Calculateur(nom, this.idCour);
			this.incrementId();
			this.ajouterRessource(nouvCalculateur);
			nouvCalculateur.rendDisponible();

		}
		
		//M�thodes pour changer une ressource de projet en rajouter ou en enlever 
		public void ajouterRessourceProjet(int idRessource,String nomProjet) {//ajouter une ressource a un projet � l'aide de son identifiant
			int[] placeRessource = this.chercherRessource(idRessource);
			if (placeRessource[0] == 1) {
				int rangRessource = placeRessource[1];
					Ressource resCour = this.listeRessource.get(rangRessource);
					if (resCour.getDispo() == true) {//v�rifie la disponibilit� de la ressource
						int[] placeProjet = this.chercheProjet(nomProjet);
						if (placeProjet[0] == 1) {
							Projet projetCour = this.listeProjet.get(placeProjet[1]);
							projetCour.ajouter(resCour);	//on ajoute au projet la ressource
							resCour.setProjet(projetCour);
							resCour.rendIndisponible();
							
						}
					}
			}
			update();
		}
		
		public void enleverRessourceProjet(int idRessource) {
			int[] placeRessource = this.chercherRessource(idRessource);
			if (placeRessource[0] == 1) {
				int rangRessource = placeRessource[1];
				Ressource resCour = this.listeRessource.get(rangRessource);
				resCour.getProjet().enlever(resCour);
				resCour.unsetProjet();
				resCour.rendDisponible();
			}
			update();
		}

		public void deplacerRessourceProjet(int idRessource, String nomProjet) {
			//d�place la ressource d'iD vers le projet entr� sauf si il est dans aucun projet il est mis dans le projet si le nom est faux il est juste retir�
			this.enleverRessourceProjet(idRessource);
			this.ajouterRessourceProjet(idRessource, nomProjet);
		}
			
		public void update() {
			this.setChanged();
			this.notifyObservers();	
		}
}
