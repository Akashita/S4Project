import java.util.ArrayList;




public class Projet {

	private ArrayList<Ressource> lRes;
	private String nom;
	
	public Projet(String nom) {
		this.lRes =  new ArrayList<Ressource>();
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	public String toString() {
		String liste = "";
		
		for(int i = 0; i < this.lRes.size(); i++){
			liste += this.lRes.get(i).toString();
		}
		return this.nom + liste;
	}
	
	public void ajouter(Ressource ressource) {
		int[] test = this.trouverPlace(ressource);
		if (test[1]==0) {
			this.lRes.set(test[0], ressource);
		}
	}
	
	public void enlever(Ressource ressource) {
		int[] test = this.trouverPlace(ressource);
		if (test[1]==1) {
			this.lRes.remove(test[0]);
		}
	}
	private int[] trouverPlace(Ressource ressource) {
		Boolean notTrouve = true;
		int[] res = {0,0};
		
		
		if (this.lRes.size()==0) {
			return res;
		}
		else {
			
			do{
				if (this.lRes.get(res[0]).equals(res)) {
					res[1] = 1;
					notTrouve = false;
				}
				else {
					res[0] = res[0] + 1;
				}
				
			}
			while(notTrouve);
			return res;
		}
	}
	
}
