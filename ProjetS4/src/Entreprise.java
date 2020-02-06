import java.util.ArrayList;

//model il sert a créer des projets puis leur donne des ressources.

public class Entreprise {
		private ArrayList<Projet> lPro;
		private int idCour;
		
		//création de l'entreprise unique il faudra lui ajouter un nom si on désire étendre nos activités
		public Entreprise() {
			this.lPro =  new ArrayList<Projet>();
			this.idCour = 0;
		}
		//classe de base qui permettent de voir la chaîne et récupérer les infos de la classe
		public String toString() {
			String chaineRessourceProjet = "voici la liste des projets ainsi que leurs ressources : ";
			for (int i = 0; i < this.lPro.size(); i++) {
				chaineRessourceProjet += this.lPro.get(i).toString(); 
				
			}
			return chaineRessourceProjet;
		}
		
		public void incrementId (){ //fonction a utilisé sur chaque nouvelle ressource pour leur attribué un iD
			this.idCour = this.idCour +1 ;
		}
		
		public int getId() {
			return this.idCour;
		}
		
		public int[] chercheProjet(String nomProjet) {
			//cherche si le nom est déjà utilisé si il est trouvé renvoi true si pas trouvé et false si pas trouvé
			
			Boolean notTrouve = true;//sert a sortir plus vite de la boucle
			int[] res = {0,0};//a gauche la place du projet cherché et a droite si il est trouvé 0 non 1 oui
			
			if (this.lPro.size()== 0) {//si l'arrayList est vide il n'y a pas déjà ce projet.
				
				return res;
			}
			else {
				
				do{
					if (this.lPro.get(res[1]).getNom() == nomProjet) {
						res[0] = 1;
						notTrouve = false;
					}
					else {
						res[1] = res[1] + 1; //on incrémente res pour accéder à chercher
					}
					
				}
				while((notTrouve) && (res[1] < this.lPro.size()));
				return res ;
			}
			
		}
		
		
		//fonctions de créations d'éléments de l'entreprise, les ressources ainsi que les projets
		public void creerProjet(String nom) {//crée un projet si son nom n'est pas déjà utilisé
			
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
