package Model;
import java.util.ArrayList;




public class Projet {

	private ArrayList<Activite> listeActivite;//liste des activites
	private String nom;//nom des projets, clefs primaires (sert � les diff�rencier)
	private boolean selectionner = false;
	
	public Projet(String nom) {
		this.listeActivite =  new ArrayList<Activite>();
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String toString() {
		String liste = "Nom du projet : " + this.nom + ". \nIl contient les activites suivantes : ";
		
		for(int i = 0; i < this.listeActivite.size(); i++){
			liste += this.listeActivite.get(i).toString();
			liste += "\n";
		}
		return liste;
	}
	
	public boolean getSelectionner() {
		return selectionner;
	}

	public ArrayList<Activite> getListe(){
		return listeActivite;
	}
	
	public void selectionner() {
		this.selectionner = true;
	}
	
	public void deselectionner() {
		this.selectionner = false;
	}
		
	public void ajouter(Activite activite) { //test si la activite est d�j� dans le projet sinon la rajoute
		int[] test = this.chercherActivite(activite);
		
		if (test[0]==0) {
			this.listeActivite.add(activite);
		}
	}
	
	public void enlever(Activite activite) { //test si la activite est d�j� dans le projet si oui l'enl�ve
		int[] test = this.chercherActivite(activite);
		
		if (test[0]==1) {
			this.listeActivite.remove(test[1]);
		}
	}
	
	public int[] chercherActivite(Activite activite) { //cherche l’activite dans le projet et donne la place si trouv�
		Boolean pasTrouve = true;
		int[] res = {0,0};//a droite la place du projet cherch� et a gauche si il est trouv� 0 non/1 oui
		
		if (this.listeActivite.size()==0) {
			return res;
		}
		else {
			
			do{
				if (this.listeActivite.get(res[1]).equals(activite)) {
					res[0] = 1;
					pasTrouve = false;
				}
				else {
					res[1] = res[1] + 1;
				}
				
			}
			while((pasTrouve) && (res[1] < this.listeActivite.size()));
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
}
