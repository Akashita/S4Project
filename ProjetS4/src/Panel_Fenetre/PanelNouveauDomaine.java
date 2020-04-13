package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
		gc.weightx = 3;
		
		/* weightx définit le nombre de cases en ordonnée */
		gc.weighty = 2;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.CENTER;
		this.add(creerTexte("Indiquez son nom"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldNom, gc);
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 2;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Créer"), gc);

	}
	

	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
				String nom = textFieldNom.getText().toUpperCase();
				Domaine domaine = entreprise.getDomaine();
				if (!domaine.estPresent(nom)) {
					entreprise.nouvDomaine(nom);
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Ce domaine existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}

		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire le domaine", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
}
