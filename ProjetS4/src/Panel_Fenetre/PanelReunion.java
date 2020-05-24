package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Model.Temps;

/**
 * Affiche la liste des reunions de l'activite
 * permet d'ajouter ou de supprimer des reunions
 * @author Damien
 *
 */
public class PanelReunion extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idActivite;
	
	public PanelReunion(Entreprise entreprise, FenetreModal fm, int idActivite) {
		super(entreprise, fm);
		this.idActivite = idActivite;
		initialiseReunion(this, idActivite);
		initialiseCalendrier(Temps.getAujourdhui(), this);
		creerInterface();
	}
	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);

		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 4;
		gc.weighty = 5;

		
		gc.fill = GridBagConstraints.CENTER;
		
		gc.insets = new Insets(1, 5, 1, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;


		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(creerTitre("Reunions existants"), gc);
		
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.RELATIVE;
		gc.gridy ++;
		this.add(afficheListeReunion(this, idActivite), gc);
				
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.REMAINDER;
		gc.gridy ++;
		this.add(panelBouton(), gc);

	}
	
	private JPanel panelBouton() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(couleurFond);
		p.add(panelCalendrierAvecHeure(calendrier1));
		p.add(boutonAjoutConge);
		p.add(creerBoutonFin(this, "Terminer"));
		return p;
	}
	
	protected void actionFin() {
		fm.dispose();
	}



}
