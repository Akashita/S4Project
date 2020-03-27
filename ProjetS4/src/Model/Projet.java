package Model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;




public class Projet implements Comparable<Projet>{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private ArrayList<Activite> lActivite;//liste des activites (ordonnees par ordre)
	
	private String nom;
	private boolean selectionner;
	private float priorite; //Priorite du projet (relation d'ordre)
	
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
	 * @return true si l'activite a ete ajoutee
	 */
	public boolean ajouter(Activite activite) {
		int pos = this.chercherActivite(activite);
		if(pos != -1) {
			lActivite.add(pos, activite);
		}
		return pos != -1;
	}
	
	
	/**
	 * Enleve une activite du projet
	 * @param activite   L'activite a enlever
	 * @return true si l'activite a ete enlevee
	 */
	public boolean enlever(Activite activite) { 
		int pos = this.chercherActivite(activite);
		if(pos != -1) {
			lActivite.remove(pos);
		}
		return pos != -1;
	}
	
	
	/**
	 * Cherche une activite dans le projet
	 * @param activite   L'activite a chercher
	 * @return un doublet qui indique la position de l'activite et si elle a ete trouvee
	 */
	public int chercherActivite(Activite activite) { 
		Boolean trouve = false; //Indique si l'indice a ete trouve
		Boolean depasse = false; //Indique si la position a ete depacee
		
		int res = 0;
		Iterator<Activite> itt = lActivite.listIterator();
		Activite actTMP;
		
		while(!trouve && itt.hasNext() && !depasse){
			actTMP = itt.next();
			if(activite.compareTo(actTMP) == 0) {
				trouve = true;
			} else if(activite.compareTo(actTMP) < 0) {
				depasse = true;
			} else {
				res++;
			}
		}
		
		//Si l'activite n'a pas ete trouvee ou a ete depasse, on retourne -1
		if((res + 1) == lActivite.size() && !trouve && !depasse) { 
			res = -1;
		}
		
		return res;
	}
	

}
