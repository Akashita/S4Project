import java.util.ArrayList;

//model il sert a cr�er des projets puis leur donne des ressources.

public class Entreprise {
		private ArrayList<Projet> lPro;
		private int idCour;
		
		//cr�ation de l'entreprise unique il faudra lui ajouter un nom si on d�sire �tendre nos activit�s
		public Entreprise() {
			this.lPro =  new ArrayList<Projet>();
			this.idCour = 0;
		}
		//classe de base qui permettent de voir la cha�ne et r�cup�rer les infos de la classe
		public String toString() {
			String chaineRessourceProjet = "voici la liste des projets ainsi que leurs ressources : ";
			for (int i = 0; i < this.lPro.size(); i++) {
				chaineRessourceProjet += this.lPro.get(i).toString(); 
				
			}
			return chaineRessourceProjet;
		}
		
		public void incrementId (){ //fonction a utilis� sur chaque nouvelle ressource pour leur attribu� un iD
			this.idCour = this.idCour +1 ;
		}
		
		public int getId() {
			return this.idCour;
		}
		
		public int[] chercheProjet(String nomProjet) {
			//cherche si le nom est d�j� utilis� si il est trouv� renvoi true si pas trouv� et false si pas trouv�
			
			Boolean notTrouve = true;//sert a sortir plus vite de la boucle
			int[] res = {0,0};//a gauche la place du projet cherch� et a droite si il est trouv� 0 non 1 oui
			
			if (this.lPro.size()== 0) {//si l'arrayList est vide il n'y a pas d�j� ce projet.
				
				return res;
			}
			else {
				
				do{
					if (this.lPro.get(res[1]).getNom() == nomProjet) {
						res[0] = 1;
						notTrouve = false;
					}
					else {
						res[1] = res[1] + 1; //on incr�mente res pour acc�der � chercher
					}
					
				}
				while((notTrouve) && (res[1] < this.lPro.size()));
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
		
		public void nouvPersonne (String nomProjet, String nom, String prenom, String role) {
			Personne nouvPersonne = new Personne(nom,prenom,role, this.idCour);
			int [] place = 	this.chercheProjet(nomProjet);
			if (place[0] == 1) {
				this.lPro.get(place[1]).ajouter(nouvPersonne);	
				this.incrementId();
			}
			

		}
		
	
}
