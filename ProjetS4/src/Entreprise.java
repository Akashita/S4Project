import java.util.ArrayList;

//model il sert a cr�er des projets puis leur donne des ressources.

public class Entreprise {
		private ArrayList<Projet> lPro;
		private int idCour;
		
		public Entreprise() {
			this.lPro =  new ArrayList<Projet>();
			this.idCour = 0;
		}
		
		public void incrementId (){ //fonction a utilis� sur chaque nouvelle ressource pour leur attribu� un iD
			this.idCour = this.idCour +1 ;
		}
		
		public int getId() {
			return this.idCour;
		}
		
		
		
		public boolean chercheProjet(Projet projetTest) {
			//cherche si le nom est d�j� utilis� si il est trouv� renvoi true si pas trouv� et false si pas trouv�
			
			Boolean notTrouve = true;
			int var = 0; //variable de teste pour la varEME place du tableau
			
			if (this.lPro.size()== 0) {//si l'arrayList est vide il n'y a pas d�j� ce projet.
				return notTrouve;
			}
			else {
				
				do{
					if (this.lPro.get(var).equals(projetTest)) {
						notTrouve = false;
					}
					else {
						var = var +1; //on incr�mente var pour acc�der � chercher
					}
					
				}
				while(notTrouve);
				return notTrouve;
			}
			
		}
		
		public void creerProjet(String nom) {//cr�e un projet si son nom n'est pas d�j� utilis�
			
			Projet newProjet = new Projet(nom);
			
			if (this.chercheProjet(newProjet)) {
				this.lPro.set(0,newProjet);
			}
			
		}
	
}
