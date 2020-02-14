package Fenetre;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class FenetreAjouterRessource extends JFrame{
	Entreprise entreprise;
	String type;
	
	public FenetreAjouterRessource(Entreprise entreprise, String type) {
		this.entreprise = entreprise;
		this.type = type;
		this.setTitle("Ajout ressource");
		this.setSize(300,200);
		this.setVisible(true);
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
			ajoutRessource(tab, listeRessource);
		}
		if (type == Ressource.SALLE) {
			for (int i=0; i<tab.length;i++) {
				Salle Salle = (Salle) listeRessource.get(i);
				tab[i] = Salle.getNom(); 
			}
			ajoutRessource(tab, listeRessource);			
		}
		if (type == Ressource.CALCULATEUR) {
			for (int i=0; i<tab.length;i++) {
				Calculateur Calculateur = (Calculateur) listeRessource.get(i);
				tab[i] = Calculateur.getNom(); 
			}
			ajoutRessource(tab, listeRessource);			
		}
	}
	
	private void ajoutRessource(String tab[], ArrayList<Ressource> listeRessource) {
		Projet projet = entreprise.getProjetSelectionner();
		JButton bouton = new JButton("Ajout");
		bouton.setBounds(100, 20, 100, 10);
		final JComboBox jcb = new JComboBox(tab);
		this.add(bouton);
		this.add(jcb);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	    	    int index = jcb.getSelectedIndex();
	    	    entreprise.ajouterRessourceProjet(listeRessource.get(index).getId(), projet.getNom());
	        }
	    });
	}	
}
