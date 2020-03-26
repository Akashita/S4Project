package Fenetre;

import javax.swing.JButton;
import javax.swing.JPanel;

public interface FenetreInterface {
	
	/**
	 * methode pour creer l'interface de la fenetre
	 */
	void creationInterface();
	
	/**
	 * interaction avec le bouton fin
	 */
	void actionBoutonFin();
	
	/**
	 * creer le bouton pour pour executer la demande
	 * @return un bouton 
	 */
	JButton creerBoutonFin();
	
	/**
	 * creer un bouton pour annuler les saisies et fermer la fenetre
	 * @return un bouton
	 */
	JButton creerBoutonAnnuler();
	
	/**
	 * creer un panel contenant le bouton "fin" et "annuler"
	 * @return un panel qui contient deux boutons
	 */
	JPanel ajoutBouton();

}
