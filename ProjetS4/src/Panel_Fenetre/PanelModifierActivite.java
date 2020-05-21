package Panel_Fenetre;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Model.Temps;
import Ressource.Competence;


public class PanelModifierActivite extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public PanelModifierActivite(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		activite = entreprise.getActiviteSelectionner();
		initialiseCalendrier(activite.getDebut(), this);

		
		textFieldNom.setText(activite.getTitre());
		int charge = (int)activite.getChargeJHomme();
		textFieldCharge.setText(Integer.toString(charge));

		//initialseJMA(projet.getDeadline());

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
		
		//colCompetenceModifie(gc);		
		
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
		this.add(creerBoutonSupprimer(this), gc);

		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
		this.add(creerBoutonAnnuler(), gc);

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Modifier"), gc);

	}
	
	private void colCompetenceModifie(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.BOTH;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx=0;
		gc.gridy++;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.gridheight = 2;
		//this.add(afficheListeDomaine(), gc);

		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx ++;
		gc.gridwidth = 1;
		//this.add(actionModificationCompetence(),gc);
	}

	private Component afficheListeDomaine() {
		String [] tc = new String [ac.size()];
		for (int i=0; i<competences.size(); i++) {
			tc[i] = competences.get(i);
		}
		JList<String> jlt = new JList<String>(tc);
		jlt.setBackground(couleurFond);
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent evt) {
		        if (evt.getButton() == MouseEvent.BUTTON3) { //clic droit
		        	jlt.setSelectedIndex(jlt.locationToIndex(evt.getPoint()));
		        	jlt.setComponentPopupMenu(popupMenuDomaine(jlt.getSelectedValue()));
		        	;
		        }
		    }
		});

		JScrollPane scrollPaneJListDomaine = new JScrollPane(jlt);
		scrollPaneJListDomaine.setViewportView(jlt);
		scrollPaneJListDomaine.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPaneJListDomaine;
	}

	
	
	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
			if (!textFieldCharge.getText().isEmpty()) {
				if (estUnEntier(textFieldCharge.getText())) {
					String nom = textFieldNom.getText();
					int charge = Integer.parseInt(textFieldCharge.getText());
					int jour = Integer.parseInt((String) calendrier1.getComboBoxJour().getSelectedItem());
					int mois = calendrier1.getComboBoxMois().getSelectedIndex()+1;
					int annee = Integer.parseInt((String) calendrier1.getComboBoxAnnee().getSelectedItem());
					LocalDate date =  Temps.creerLaDate(jour, mois, annee);
					entreprise.modifierActivite(activite, nom, charge, date);
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veillez ecrire sa priorité", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	protected void supprimer() {
		String texte = "<html> Êtes-vous sur de vouloir supprimer cette activité ? <br> La suppression de cette activité supprimera tout son contenu. </html>";
		int res = JOptionPane.showConfirmDialog(null, texte, "Attention", JOptionPane.YES_NO_OPTION);			
		if (res == 0) { //0 = yes
			entreprise.supprimerActiviter(activite);
			fm.dispose();
		}		
	}

}
