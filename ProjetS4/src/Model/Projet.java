package Model;
import java.util.ArrayList;




public class Projet implements Comparable<Projet>{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private ArrayList<Activite> lActivite;//liste des activites (ordonnees par ordre)
	
	private String nom;
	private boolean selectionner;
	private float priorite; //Priorite du projet 
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Projet(String nom, float priorite) {
		this.lActivite =  new ArrayList<Activite>();
		this.nom = nom;
		this.selectionner = false;
		this.priorite = priorite;
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getNom() {
		return this.nom;
	}
	
	public boolean getSelectionner() {
		return selectionner;
	}
	
	public ArrayList<Activite> getListe(){
		return lActivite;
	}
	
	//--------------------------------------------------------------------------------->>>>> Setteurs simples
	public void selectionner() {
		this.selectionner = true;
	}
	
	public void deselectionner() {
		this.selectionner = false;
	}
	
	//--------------------------------------------------------------------------------->>>>> Comparaison
	@Override
	public boolean equals(Object obj) {//permet de tester si deux projets ont le m�me nom.
		if(obj instanceof Projet && obj != null) {
			Projet res = (Projet)obj;
			return nom == res.nom;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Projet proj) {
		int res;
		if(priorite == proj.priorite) {
			res = 0;
		} else if(priorite < proj.priorite) {
			res = -1;
		} else {
			res = 1;
		}
		
		return res;
	}	
	
	//--------------------------------------------------------------------------------->>>>> toString	
	public String toString() {
		String liste = "Projet : " + this.nom + ". \n Il contient les activites suivantes : ";
		for(int i = 0; i < this.lActivite.size(); i++){
			liste += this.lActivite.get(i).toString();
			liste += "\n";
		}
		return liste;
	}
	
	//--------------------------------------------------------------------------------->>>>> Gestion des activites

	/**
	 * Ajoute une activite au projet 
	 * @param activite   L'activite a ajouter
	 */
	public void ajouter(Activite activite) { //test si la activite est d�j� dans le projet sinon la rajoute
		lActivite.add(activite);
		//TODO
		//TODO
		/*int[] test = this.chercherActivite(activite);
		
		if (test[0]==0) {
			Boolean place = false;
			int i = 0;
			while (i < lActivite.size() && !place) {
				if (activite.compareTo(lActivite.get(i)) == 1) { //Si activite > lActivite.egt(i)
					lActivite.add(i, activite);
					place = true;
				}
			}
			if (place = false) {
				lActivite.add(activite);
			}
		}*/
	}
	
	
	/**
	 * Enleve une activite du projet
	 * @param activite   L'activite a enlever
	 */
	public void enlever(Activite activite) { //test si la activite est d�j� dans le projet si oui l'enl�ve
		int[] test = this.chercherActivite(activite);
		
		if (test[0]==1) {
			this.lActivite.remove(test[1]);
		}
	}
	
	
	/**
	 * Cherche une activite dans le projet
	 * @param activite   L'activite a chercher
	 * @return un doublet qui indique la position de l'activite et si elle a ete trouvee
	 */
	public int[] chercherActivite(Activite activite) { 
		Boolean pasTrouve = true;
		int[] res = {0,0};
		
		if (this.lActivite.size()==0) {
			return res;
		}
		else {
			
			do{
				if (this.lActivite.get(res[1]).equals(activite)) {
					res[0] = 1;
					pasTrouve = false;
				}
				else {
					res[1] = res[1] + 1;
				}
				
			}
			while((pasTrouve) && (res[1] < this.lActivite.size()));
			return res;
		}
	}

	public float getPriorite() {
		return priorite;
	}
	

}
