package Ressource;

import java.util.ArrayList;

public class Domaine {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private ArrayList<String> listeDomaine;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEURS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public Domaine() {
		listeDomaine = new ArrayList<String>();
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	//--------------------------------------------------------------------------------->>>>> Setteurs simples
	public void setDomaine(ArrayList<String> listeDomaine) {
		this.listeDomaine = listeDomaine;
	}
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public ArrayList<String> getListeDomaine(){
		return listeDomaine;
	}
	
	//--------------------------------------------------------------------------------->>>>> Autres
	public boolean estPresent(String domaine) {
		boolean estPresent = false;
		for (int i=0;i<listeDomaine.size();i++) {
			if (domaine == listeDomaine.get(i)) {
				estPresent = true;
			}
		}
		return estPresent;
	}
	
	
	public void ajoutDomaine (String domaine) {
		listeDomaine.add(domaine);
	}

	public void enleverDomaine (String domaine) {
		int index = 0;
		for (int i=0;i<listeDomaine.size();i++) {
			if (domaine == listeDomaine.get(i)) {
				index = i;
			}
		}
		listeDomaine.remove(index);
	}



}
