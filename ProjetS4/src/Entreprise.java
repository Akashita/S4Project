import java.util.ArrayList;

//model il sert a créer des projets puis leur donne des ressources.

public class Entreprise {
		private ArrayList<Projet> lPro;
		private int idCour;
		
		public Entreprise() {
			this.lPro =  new ArrayList<Projet>();
			this.idCour = 0;
		}
		
		public void incrementId (){ //fonction a utilisé sur chaque nouvelle ressource pour leur attribué un iD
			this.idCour = this.idCour +1 ;
		}
		
		public int getId() {
			return this.idCour;
		}
		
		
		
		public boolean chercheProjet(Projet projetTest) {
			//cherche si le nom est déjà utilisé si il est trouvé renvoi true si pas trouvé et false si pas trouvé
			
			Boolean notTrouve = true;
			int var = 0; //variable de teste pour la varEME place du tableau
			
			if (this.lPro.size()== 0) {//si l'arrayList est vide il n'y a pas déjà ce projet.
				return notTrouve;
			}
			else {
				
				do{
					if (this.lPro.get(var).equals(projetTest)) {
						notTrouve = false;
					}
					else {
						var = var +1; //on incrémente var pour accéder à chercher
					}
					
				}
				while(notTrouve);
				return notTrouve;
			}
			
		}
		
		public void creerProjet(String nom) {//crée un projet si son nom n'est pas déjà utilisé
			
			Projet newProjet = new Projet(nom);
			
			if (this.chercheProjet(newProjet)) {
				this.lPro.set(0,newProjet);
			}
			
		}
	
}
