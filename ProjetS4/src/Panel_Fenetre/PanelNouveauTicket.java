package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import Fenetre.FenetreModal;
import Model.Entreprise;

public class PanelNouveauTicket  extends PanelFenetre{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelNouveauTicket(Entreprise entreprise, FenetreModal fm) {
		super(entreprise,fm);
		initialiseTicket(this);
		creerInterface();
	}
	
	protected void creerInterface() {
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 3;
		gc.weighty = 9;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(creerTitre("Creer un ticket"), gc);
		
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		this.add(creerTexte("Action : "), gc);
		gc.gridx++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(comboBoxActionTicket, gc);


		
	}
}
