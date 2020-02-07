import java.util.ArrayList;




public class Projet {

	private ArrayList<Ressource> lRes;//liste des ressources
	private String nom;//nom des projets, clefs primaires (sert à les différencier)
	
	public Projet(String nom) {
		this.lRes =  new ArrayList<Ressource>();
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	public String toString() {
		String liste = " ";
		
		for(int i = 0; i < this.lRes.size(); i++){
			liste += this.lRes.get(i).toString();
		}
		return this.nom + liste;
	}
	
	public void ajouter(Ressource ressource) { //test si la ressource est déjà dans le projet sinon la rajoute
		int[] test = this.chercherRessource(ressource);
		
		if (test[0]==0) {
			this.lRes.add(ressource);
		}
	}
	
	public void enlever(Ressource ressource) { //test si la ressource est déjà dans le projet si oui l'enlève
		int[] test = this.chercherRessource(ressource);
		
		if (test[0]==1) {
			this.lRes.remove(test[0]);
		}
	}
	public int[] chercherRessource(Ressource ressource) { //cherche la ressource dans le projet et donne la place si trouvé
		Boolean pasTrouve = true;
		int[] res = {0,0};//a droite la place du projet cherché et a gauche si il est trouvé 0 non/1 oui
		
		if (this.lRes.size()==0) {
			return res;
		}
		else {
			
			do{
				if (this.lRes.get(res[1]).equals(ressource)) {
					res[0] = 1;
					pasTrouve = false;
				}
				else {
					res[1] = res[1] + 1;
				}
				
			}
			while((pasTrouve) && (res[1] < this.lRes.size()));
			return res;
		}
	}
	@Override
	public boolean equals(Object obj) {//permet de tester si deux projets ont le même nom.
		if(obj instanceof Projet && obj != null) {
			Projet res = (Projet)obj;
			return nom == res.nom;
		} else {
			return false;
		}
	}
	
}
