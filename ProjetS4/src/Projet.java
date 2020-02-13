import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;




public class Projet {

	private ArrayList<Ressource> listeRessource;//liste des ressources
	private String nom;//nom des projets, clefs primaires (sert � les diff�rencier)
	private boolean selectionner=false;
	
	public Projet(String nom) {
		this.listeRessource =  new ArrayList<Ressource>();
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String toString() {
		String liste = "Nom du projet : " + this.nom + ". \nIl contient les ressources suivantes : ";
		
		for(int i = 0; i < this.listeRessource.size(); i++){
			liste += this.listeRessource.get(i).toString();
			liste += "\n";
		}
		return liste;
	}
	
	public boolean getSelectionner() {
		return selectionner;
	}

	public ArrayList<Ressource> getListe(){
		return listeRessource;
	}
	
	public void selectionner() {
		this.selectionner = true;
	}
	
	public void deselectionner() {
		this.selectionner = false;
	}
		
	public void ajouter(Ressource ressource) { //test si la ressource est d�j� dans le projet sinon la rajoute
		int[] test = this.chercherRessource(ressource);
		
		if (test[0]==0) {
			this.listeRessource.add(ressource);
		}
	}
	
	public void enlever(Ressource ressource) { //test si la ressource est d�j� dans le projet si oui l'enl�ve
		int[] test = this.chercherRessource(ressource);
		
		if (test[0]==1) {
			this.listeRessource.remove(test[1]);
		}
	}
	
	public int[] chercherRessource(Ressource ressource) { //cherche la ressource dans le projet et donne la place si trouv�
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
	
	@Override
	public boolean equals(Object obj) {//permet de tester si deux projets ont le m�me nom.
		if(obj instanceof Projet && obj != null) {
			Projet res = (Projet)obj;
			return nom == res.nom;
		} else {
			return false;
		}
	}
	
	public void dessineToi(Graphics g) {
		 for (int i=0; i<listeRessource.size(); i++) {
			 Ressource ressource = listeRessource.get(i); 
			 if (ressource.getType() == Ressource.PERSONNE) {
				 g.setColor(Color.BLUE);
			 }
			 if (ressource.getType() == Ressource.SALLE) {
				 g.setColor(Color.RED);
			 }
			 if (ressource.getType() == Ressource.CALCULATEUR) {
				 g.setColor(Color.GREEN);
			 }
			 g.fillOval(100+(i*50), 100, 50, 50);
		 }
	}
}
