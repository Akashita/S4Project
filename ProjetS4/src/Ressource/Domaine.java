package Ressource;

import java.util.ArrayList;

public class Domaine {
	
	private ArrayList<String> listeDomaine;
	
	
	public Domaine() {
		listeDomaine = new ArrayList<String>();
	}
	
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

	public ArrayList<String> getListeDomaine(){
		return listeDomaine;
	}
}
