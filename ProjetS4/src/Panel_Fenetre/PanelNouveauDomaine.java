package Panel_Fenetre;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Ressource.Domaine;
import Ressource.Ressource;

public class PanelNouveauDomaine extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public PanelNouveauDomaine(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		initialiseDomaine(this);
		creerInterface();
	}
	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		/* Le gridBagConstraints va définir la position et la taille des éléments */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert à définir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.CENTER;
		
		/* insets définir la marge entre les composant new Insets(margeSupérieure, margeGauche, margeInférieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir où on place le composant s'il n'occupe pas la totalité de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx définit le nombre de cases en abscisse */
		gc.weightx = 5;
		
		/* weightx définit le nombre de cases en ordonnée */
		gc.weighty = 11;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;
		this.add(creerTitre("Domaines existants"), gc);

		gc.fill = GridBagConstraints.BOTH;
		gc.gridheight = 6;
		gc.gridy = 1;
		this.add(afficheListeDomaine(), gc);
		
		gc.fill = GridBagConstraints.CENTER;
		gc.gridwidth = 3;
		gc.gridheight = 1;
		gc.gridy = 3;
		gc.gridx = 2;
		this.add(creerTitre("Creer un nouveau domaine"), gc);
		
		
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 2;
		gc.gridy = 4;
		this.add(textFieldNom, gc);
		gc.gridwidth = 1;
		gc.gridx = 4;
		this.add(boutonAjoutDomaine, gc);

		
		gc.gridwidth = 1;		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 4;
		gc.gridy = 5;
		this.add(creerBoutonFin(this, "Terminer"), gc);

	}
	


	protected void actionFin() {
		entreprise.setDomaine(listeDomaine);
		fm.dispose();
	}
}
