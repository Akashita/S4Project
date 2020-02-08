import java.util.ArrayList;

//model il sert a cr�er des projets puis leur donne des ressources.

public class Entreprise {
		private ArrayList<Projet> lPro;//liste qui contient tous les projets de l'entreprise
		private ArrayList<String> listeType;//liste qui contient tous les types de ressourceAutre qui ont d�j� �t� cr�e pour les r�utiliser
		private ArrayList<Ressource> listeRessource;//liste qui contient 
		private int idCour;//id des ressources
		
		//cr�ation de l'entreprise unique il faudra lui ajouter un nom si on d�sire �tendre nos activit�s
		public Entreprise() {
			this.lPro =  new ArrayList<Projet>();
			this.listeType =  new ArrayList<String>();
			this.listeRessource =  new ArrayList<Ressource>();
			this.idCour = 0;
		}
		//classe de base qui permettent de voir la cha�ne et r�cup�rer les infos de la classe
		@Override
		public String toString() {
			String chaineRessourceProjet = "voici la liste des projets ainsi que leurs ressources : ";
			for (int i = 0; i < this.lPro.size(); i++) {
				chaineRessourceProjet += this.lPro.get(i).toString(); 
				
			}
			chaineRessourceProjet += ". \n Liste des Ressource de l'entreprise et leurs disponibilit�s : ";

			for (int i = 0; i < this.listeRessource.size(); i++) {
				Ressource resCour;
				chaineRessourceProjet += " \n";
				resCour =  this.listeRessource.get(i);
				chaineRessourceProjet += resCour.getNom() + "------disponible : ";
				chaineRessourceProjet += resCour.getDispo();
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
		public void ajouterRessource(Ressource resCour) {
			this.listeRessource.add(resCour);
		}
		public void enleverRessource(Ressource resCour) {
			int[] test = this.chercherRessource(resCour);
			if (test[0] == 1) {
			this.listeRessource.remove(test[1]);
		}
		}
		
		public int[] chercherRessource(Ressource ressource) {
			Boolean pasTrouve = true;
			int[] res = {0,0};//a droite la place du projet cherch� et a gauche si il est trouv� 0 non/1 oui
			
			if (this.listeRessource.size()==0) {
				return res;
			}
			else {
				
				do{
					if (this.listeRessource.get(res[1]).equals(ressource)) {
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
			
			if (this.lPro.size()== 0) {//si l'arrayList est vide il n'y a pas d�j� ce projet.
				
				return res;
			}
			else {
				
				do{
					if (this.lPro.get(res[1]).getNom() == nomProjet) {
						res[0] = 1;
						pasTrouve = false;
					}
					else {
						res[1] = res[1] + 1; //on incr�mente res pour acc�der � chercher plus loin.
					}
					
				}
				while((pasTrouve) && (res[1] < this.lPro.size()));
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
				this.lPro.add(newProjet);
			}
			
		}
		
		//m�thode pour cr�er des ressources et les attribuer a des projets
		public void nouvPersonne (String nomProjet, String nom, String prenom, String role) {
			Personne nouvPersonne = new Personne(nom,prenom,role, this.idCour);
			int [] place = 	this.chercheProjet(nomProjet);
			this.incrementId();
			this.ajouterRessource(nouvPersonne);
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				this.lPro.get(place[1]).ajouter(nouvPersonne);	
				nouvPersonne.rendIndisponible();
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
				this.lPro.get(place[1]).ajouter(nouvSalle);	
				nouvSalle.rendIndisponible();
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
				this.lPro.get(place[1]).ajouter(nouvRessourceAutre);	
				nouvRessourceAutre.rendIndisponible();
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

		}
		
		

}
