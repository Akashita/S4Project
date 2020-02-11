import java.util.ArrayList;

import javax.swing.JOptionPane;

public class FenetreAjouterRessource {
	Entreprise entreprise;
	String type;
	
	public FenetreAjouterRessource(Entreprise entreprise, String type) {
		this.entreprise = entreprise;
		this.type = type;
		ajoutRessource();
	}
	
	private void ajoutRessource() {
		ArrayList<Ressource> listeRessource = new ArrayList<Ressource>();
		for (int i=0; i<entreprise.getRessource().size(); i++) {
			Ressource ressource = entreprise.getRessource().get(i);
			if (ressource.getType() == Ressource.PERSONNE && type == Ressource.PERSONNE) {
				listeRessource.add(ressource);
			}
			if (ressource.getType() == Ressource.SALLE && type == Ressource.SALLE) {
				listeRessource.add(ressource);
			}
			if (ressource.getType() == Ressource.CALCULATEUR && type == Ressource.CALCULATEUR) {
				listeRessource.add(ressource);
			}
		}

		String tab[] = new String[listeRessource.size()];

		if (type == Ressource.PERSONNE) {
			for (int i=0; i<tab.length;i++) {
				Personne personne = (Personne) listeRessource.get(i);
				tab[i] =personne.getPrenom() +" "+ personne.getNom(); 
			}
			ajoutPersonne(tab, listeRessource);
		}
		if (type == Ressource.SALLE) {
			for (int i=0; i<tab.length;i++) {
				Salle Salle = (Salle) listeRessource.get(i);
				tab[i] = Salle.getNom(); 
			}
			ajoutSalle(tab, listeRessource);			
		}
		if (type == Ressource.CALCULATEUR) {
			for (int i=0; i<tab.length;i++) {
				Calculateur Calculateur = (Calculateur) listeRessource.get(i);
				tab[i] = Calculateur.getNom(); 
			}
			ajoutCalculateur(tab, listeRessource);			
		}
	}
	
	private void ajoutPersonne(String tab[], ArrayList<Ressource> listeRessource) {
		Projet projet = entreprise.getProjetSelectionner();
		JOptionPane.showInputDialog(null, 
	  	      "Selectionner un salarié à ajouter",
	  	      "Ajouter un salarié au projet" + projet.getNom(),
	  	      JOptionPane.QUESTION_MESSAGE,
	  	      null,
	  	    tab,
	  	  tab[0]);	    
		int indice = indiceChoisiePersonne(tab, listeRessource);
	    entreprise.ajouterRessourceProjet(listeRessource.get(indice).getId(), projet.getNom());

	}
	
	private void ajoutSalle(String tab[], ArrayList<Ressource> listeRessource) {
		Projet projet = entreprise.getProjetSelectionner();
	    JOptionPane.showInputDialog(null, 
	  	      "Selectionner une salle à ajouter",
	  	      "Ajouter un salarié au projet" + projet.getNom(),
	  	      JOptionPane.QUESTION_MESSAGE,
	  	      null,
	  	    tab,
	  	  tab[0]);
		int indice = indiceChoisieRessource(tab, listeRessource);
	    entreprise.ajouterRessourceProjet(listeRessource.get(indice).getId(), projet.getNom());

	}
	
	private void ajoutCalculateur(String tab[], ArrayList<Ressource> listeRessource) {
		Projet projet = entreprise.getProjetSelectionner();
	    JOptionPane.showInputDialog(null, 
	  	      "Selectionner un salarié à ajouter",
	  	      "Ajouter un salarié au projet" + projet.getNom(),
	  	      JOptionPane.QUESTION_MESSAGE,
	  	      null,
	  	    tab,
	  	  tab[0]);	 
		int indice = indiceChoisieRessource(tab, listeRessource);
	    entreprise.ajouterRessourceProjet(listeRessource.get(indice).getId(), projet.getNom());

	}
	
	private int indiceChoisiePersonne(String tab[], ArrayList<Ressource> listeRessource) {
		int indice = 0;
		for (int i=0; i<tab.length;i++) {
			Personne personne = (Personne) listeRessource.get(i);
			if (tab[i] == personne.getPrenom() +" "+ personne.getNom()) {
				indice = i;
			}
		}
		return indice;
	}

	private int indiceChoisieRessource(String tab[], ArrayList<Ressource> listeRessource) {
		int indice = 0;
		for (int i=0; i<tab.length;i++) {
			Ressource ressource =  listeRessource.get(i);
			if (tab[i] == ressource.getNom()) {
				indice = i;
			}
		}
		return indice;
	}
}
