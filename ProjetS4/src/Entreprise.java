import java.util.ArrayList;

//model il sert a cr�er des projets puis leur donne des ressources.

public class Entreprise {
		private ArrayList<Projet> lPro;
		private ArrayList<String> listeType;

		private int idCour;
		
		//cr�ation de l'entreprise unique il faudra lui ajouter un nom si on d�sire �tendre nos activit�s
		public Entreprise() {
			this.lPro =  new ArrayList<Projet>();//liste qui contient tous les projets de l'entreprise
			this.listeType =  new ArrayList<String>();//liste qui contient tous les types de ressourceAutre qui ont d�j� �t� cr�e pour les r�utiliser

			this.idCour = 0;
		}
		//classe de base qui permettent de voir la cha�ne et r�cup�rer les infos de la classe
		@Override
		public String toString() {
			String chaineRessourceProjet = "voici la liste des projets ainsi que leurs ressources : ";
			for (int i = 0; i < this.lPro.size(); i++) {
				chaineRessourceProjet += this.lPro.get(i).toString(); 
				
			}
			chaineRessourceProjet += ". Il y a aussi ces types disponibles : ";
			
			for (int i = 0; i < this.listeType.size(); i++) {
				chaineRessourceProjet += " ";
				chaineRessourceProjet += this.listeType.get(i).toString(); 
				
			}
			return chaineRessourceProjet;
		}
		
		public void incrementId (){ //fonction a utiliser sur chaque nouvelle ressource pour leur attribuer un iD
			this.idCour = this.idCour +1 ;
		}
		
		public int getId() {
			return this.idCour;
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
		
		
		//fonctions de cr�ations d'�l�ments de l'entreprise, les ressources ainsi que les projets
		
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
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				this.lPro.get(place[1]).ajouter(nouvPersonne);	
				this.incrementId();
			}
		}
		
		public void nouvSalle (String nomProjet, String nom, int capacite) {
			Salle nouvSalle = new Salle(this.idCour,nom, capacite);
			int [] place = 	this.chercheProjet(nomProjet);
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				this.lPro.get(place[1]).ajouter(nouvSalle);	
				this.incrementId();
			}
		}
		
		//m�thode de cr�ation des RessourceAutre personnalisable et qui permettent de cr�er des types diff�rents
		public void nouvRessourceAutre (String nomProjet, String nom,String type) {
			RessourceAutre nouvRessourceAutre = new RessourceAutre(nom, type, this.idCour);
			int [] place = 	this.chercheProjet(nomProjet);
			if (place[0] == 1) {//cherche si le projet existe si oui rajoute la ressource
				this.lPro.get(place[1]).ajouter(nouvRessourceAutre);	
				this.incrementId();
				this.nouvTypeRessource(type);
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
		

}
