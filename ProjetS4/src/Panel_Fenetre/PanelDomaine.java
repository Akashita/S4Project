package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Fenetre.FenetreModal;
import Model.Entreprise;

/**
 * Affiche la liste de domaine de l'entreprise 
 * permet d'ajouter ou de supprimer des domaines
 * @author Damien
 *
 */
public class PanelDomaine extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public PanelDomaine(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		initialiseDomaine(this);
		creerInterface();
	}
	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);

		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 4;
		gc.weighty = 5;

		
		gc.fill = GridBagConstraints.CENTER;
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;


		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(creerTitre("Domaines existants"), gc);
		
		gc.fill = GridBagConstraints.BOTH;
		gc.gridheight = GridBagConstraints.RELATIVE;
		gc.gridy ++;
		this.add(afficheListeDomaine(this), gc);
		
		gc.fill = GridBagConstraints.CENTER;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.gridy ++;

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridx = 2;
		this.add(panelBouton(), gc);

	}
	
	private JPanel panelBouton() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(couleurFond);
		p.add(textFieldNom);
		p.add(boutonAjoutDomaine);
		p.add(creerBoutonFin(this, "Terminer"));
		return p;
	}
	
	protected void actionFin() {
		fm.dispose();
	}



}
