package Model;
import java.util.ArrayList;




public class Projet implements Comparable<Projet>{

	private ArrayList<Activite> lActivite;//liste des activites
	//tri� par ordre de priorit�
	
	private String nom;//nom des projets, clefs primaires (sert � les diff�rencier)
	private boolean selectionner;
	private float priorite;
	
	public Projet(String nom, float priorite) {
		this.lActivite =  new ArrayList<Activite>();
		this.nom = nom;
		this.selectionner = false;
		this.priorite = priorite;
	}
	
	public String getNom() {
		return this.nom;
	}
		
	public String toString() {
		String liste = "Nom du projet : " + this.nom + ". \nIl contient les activites suivantes : ";
		
		for(int i = 0; i < this.lActivite.size(); i++){
			liste += this.lActivite.get(i).toString();
			liste += "\n";
		}
		return liste;
	}

	
	public boolean getSelectionner() {
		return selectionner;
	}

	public ArrayList<Activite> getListe(){
		return lActivite;
	}
	
	public void selectionner() {
		this.selectionner = true;
	}
	
	public void deselectionner() {
		this.selectionner = false;
	}
		
	public void ajouter(Activite activite) { //test si la activite est d�j� dans le projet sinon la rajoute
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
		lActivite.add(activite);
	}
	
	public void enlever(Activite activite) { //test si la activite est d�j� dans le projet si oui l'enl�ve
		int[] test = this.chercherActivite(activite);
		
		if (test[0]==1) {
			this.lActivite.remove(test[1]);
		}
	}
	
	public int[] chercherActivite(Activite activite) { //cherche l’activite dans le projet et donne la place si trouv�
		Boolean pasTrouve = true;
		int[] res = {0,0};//a droite la place du projet cherch� et a gauche si il est trouv� 0 non/1 oui
		
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
}
