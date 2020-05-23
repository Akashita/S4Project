package Panel_Fenetre;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import Fenetre.FenetreModal;
import Fenetre.FenetrePrincipale;
import Model.Entreprise;
import Model.Temps;
import Ressource.Competence;


public class PanelModifierActivite extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton boutonReunion;

	
	public PanelModifierActivite(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		activite = entreprise.getActiviteSelectionner();
		initialiseCalendrier(activite.getDebut(), this);
		
		textFieldNom.setText(activite.getTitre());
		int charge = (int)activite.getChargeJHomme();
		textFieldCharge.setText(Integer.toString(charge));

		initialiseBouton();
		creerInterface();
	}
	
	private void initialiseBouton() {
		boutonReunion = new JButton("Voir les reunions");
		boutonReunion.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionBoutonReunion();
	        }
	    });			
	}

	private void actionBoutonReunion() {
		new FenetreModal(entreprise, FenetrePrincipale.NouvelleReunion, activite.getId());
	}

	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 3;
		
		gc.weighty = 6;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;		
		this.add(creerTitre("Modifiez ses informations"), gc);

		
		
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 1;
		this.add(creerTexte("Nom"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldNom, gc);			

		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(creerTexte("<html>Charge en jour/homme</html>"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldCharge, gc);			
				
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 3;
		this.add(creerTitre("Debut"), gc);

		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 3;
		this.add(creerTitre("Debut"), gc);

		gc.gridx = 0;
		gc.gridy = 4;
		this.add(panelCalendrier(calendrier1), gc);
	
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 5;
		this.add(boutonReunion, gc);

		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx ++;
		this.add(creerBoutonSupprimer(this), gc);

		this.add(creerBoutonAnnuler(), gc);
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx ++;
		this.add(creerBoutonAnnuler(), gc);

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx ++;
		this.add(creerBoutonFin(this, "Modifier"), gc);

	}
	
	
	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
			if (!textFieldCharge.getText().isEmpty()) {
				if (estUnEntier(textFieldCharge.getText())) {
					String nom = textFieldNom.getText();
					int charge = Integer.parseInt(textFieldCharge.getText());
					LocalDate date =  calendrier1.getDate();
					entreprise.modifierActivite(activite, nom, charge, date);
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veillez ecrire sa priorite", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	protected void supprimer() {
		String texte = "<html> Êtes-vous sur de vouloir supprimer cette activite ? <br> La suppression de cette activite supprimera tout son contenu. </html>";
		int res = JOptionPane.showConfirmDialog(null, texte, "Attention", JOptionPane.YES_NO_OPTION);			
		if (res == 0) { //0 = yes
			entreprise.supprimerActiviter(activite);
			fm.dispose();
		}		
	}

}
