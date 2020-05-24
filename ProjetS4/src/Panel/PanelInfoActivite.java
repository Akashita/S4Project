package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Model.Activite;
import Model.Entreprise;
import Ressource.Ressource;


/**
 * Affiche ses information et sa liste de ressource.
 * recliquer dessus permet de voir son emploi du temps 
 * @author Damien
 *
 */
public class PanelInfoActivite extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Activite activite;
	private Color couleurFond;

	public PanelInfoActivite (Entreprise entreprise, Activite activite) {
		this.entreprise = entreprise;
		this.activite = activite;

		if (activite != null) {
			couleurFond = PanelPrincipal.BLEU3;
			this.setBackground(couleurFond);
			this.setLayout(new BorderLayout());
			ArrayList<Ressource> listeR = entreprise.getListeRessourcedeActiviteParId(Ressource.PERSONNE, activite.getId());

			if (entreprise.getAfficheEDTActivite() && listeR.size() > 0) { //on affiche son edt
				this.add(new PanelEDTActivite(entreprise, activite));
			}
			else {
				if (entreprise.getActiviteSelectionner() != null) {
					if (activite.getId() == entreprise.getActiviteSelectionner().getId()) {
						couleurFond = PanelPrincipal.BLEU2;
					}					
				}
				this.add(afficheInterface(), BorderLayout.CENTER);
			}
		}
	}

	

	public JPanel afficheInterface() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		
		/* Le gridBagConstraints va definir la position et la taille des elements */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert à definir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.BOTH;
		
		/* insets definir la marge entre les composant new Insets(margeSuperieure, margeGauche, margeInferieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir où on place le composant s'il n'occupe pas la totalite de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx definit le nombre de cases en abscisse */
		gc.weightx = 5;
		
		/* weightx definit le nombre de cases en ordonnee */
		gc.weighty = 1;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		

		
		
			panel.add(creerLabel(activite.getTitre(), true), gc);
			
			gc.gridx = 0;
			gc.gridy = 1;
			ArrayList<String> ld = entreprise.getListeDomaineParIdActivite(activite.getId());
			String liste = "";
			for (int i=0; i<ld.size(); i++) {
				liste += ld.get(i);
				if (i<ld.size()-1) {
					liste += " / ";
				}
			}
			if (ld.size() == 0) {
				liste = "aucun";
			}
			panel.add(labelInfo("Domaines demandés: "+liste), gc);
			

			gc.gridx = 0;
			gc.gridy = 2;
			panel.add(labelInfo("Travail achevé: pas encore implemente"), gc);

			gc.gridx = 1;
			gc.gridy = 0;
			panel.add(labelInfo("Commence le: " + activite.getJourDebut()), gc);
			gc.gridx = 1;
			gc.gridy = 1;
			panel.add(labelInfo("Charge de travail: "+activite.getChargeJHomme()+" jour/homme"), gc);


		gc.fill = GridBagConstraints.BOTH;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx ++;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = GridBagConstraints.REMAINDER;
		for (int i=2; i<5; i++) {
			int type = Ressource.RIEN;
			switch (i){
			case 2: type = Ressource.PERSONNE;
			break;
			case 3: type = Ressource.SALLE;
			break;
			case 4: type = Ressource.CALCULATEUR;
			break;
			}
			gc.gridx = i;
			ArrayList<Ressource> listeR = entreprise.getListeRessourcedeActiviteParId(type, activite.getId());
			if (listeR.size() > 0) {
				panel.add(creerList(listeR),gc);
			}
			else {
				JPanel listeVide = new JPanel();
				listeVide.setBackground(couleurFond);
				panel.add(listeVide, gc);				
			}
		}
		return panel;
	}
	
	private JScrollPane creerList(ArrayList<Ressource> lr) {
		Ressource [] tr = new Ressource [lr.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = lr.get(i);
		}
		JList<Ressource> jlt = new JList<Ressource>(tr);
		jlt.setBackground(couleurFond);
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("unchecked")
				JList<Ressource> jlt = (JList<Ressource>)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	afficheRessource(jlt.getSelectedValue());
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(jlt);
		scrollPane.setViewportView(jlt);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
	}

	public void afficheRessource(Ressource r) {
			entreprise.afficheInfoRessource(r);
	}

	

	private JLabel creerLabel(String nom, boolean estGras) {
		JLabel label = new JLabel(nom);
		if(estGras) {
			label.setFont(new Font("Arial", Font.BOLD, 30));
		}
		else {
			label.setFont(new Font("Arial", Font.PLAIN, 30));
		}
		return label;
	}

	private JPanel labelInfo(String nom) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	    String[] regex = nom.split(":", 2); 

		JLabel labelTitre = new JLabel(regex[0]+":");
		labelTitre.setFont(new Font("Arial", Font.BOLD, 15));
		labelTitre.setHorizontalAlignment(JLabel.CENTER);
		panel.add(labelTitre);
			
		JLabel labelResultat = new JLabel(regex[1]);
		labelResultat.setFont(new Font("Arial", Font.PLAIN, 15));
		labelResultat.setForeground(PanelPrincipal.GRIS2);
		labelResultat.getFont().deriveFont(Font.ITALIC);
		labelResultat.setHorizontalAlignment(JLabel.CENTER);
		panel.add(labelResultat);

		return panel;
	}

	
	public Activite getActivite() {
		return activite;
	}
	
	
	
}
