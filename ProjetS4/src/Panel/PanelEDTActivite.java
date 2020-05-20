package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisRessourceListener;
import Model.Activite;
import Model.CreneauHoraire;
import Model.Entreprise;
import Model.Temps;
import Ressource.Personne;
import Ressource.Ressource;

/**
 * Affiche l'emploi du temps de l'activit� sous forme de diagramme de gantt
 * @author Damien
 *
 */
public class PanelEDTActivite extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Activite activite;
	private int nbPersonne;
	private Entreprise entreprise;
	private Color couleurFond;
	private ArrayList<Ressource> listePersonne;
	
	public PanelEDTActivite(Entreprise entreprise, Activite activite) {
		this.entreprise = entreprise;
		this.activite = activite;
		couleurFond = PanelPrincipal.BLEU3;
		if (entreprise.getActiviteSelectionner() != null) {
			if (activite.getId() == entreprise.getActiviteSelectionner().getId()) {
				couleurFond = PanelPrincipal.BLEU2;
			}					
		}
		listePersonne = entreprise.getListeRessourcedeActiviteParId(Ressource.PERSONNE, activite.getId());
		nbPersonne = listePersonne.size();
		this.setLayout(new BorderLayout());
		if (nbPersonne > 0) {
			this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);			
		}
	}
	
	private JPanel afficherEmploiDuTemps() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		
		/* Le gridBagConstraints va définir la position et la taille des éléments */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert à définir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.BOTH;
		
		/* insets définir la marge entre les composant new Insets(margeSupérieure, margeGauche, margeInférieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir où on place le composant s'il n'occupe pas la totalité de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx définit le nombre de cases en abscisse */
		int nbMois = 12;
		gc.weightx = nbMois+1;
		
		/* weightx définit le nombre de cases en ordonnée */
		gc.weighty = nbPersonne+1;
		
		/* pour dire qu'on ajoute un composant en position (i, j), on définit gridx=i et gridy=j */
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		panel.add(creerLabel(activite.getTitre(), true), gc);
		
		gc.fill = GridBagConstraints.NONE;
		gc.ipady = gc.anchor = GridBagConstraints.SOUTH;

		for (int i=0; i<nbMois; i++) {
			gc.gridx ++;
			panel.add(labelMois(i), gc);
		}
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(5, 2, 2, 5);

		for (int i=0; i<nbPersonne;i++) {
			gc.gridwidth = 1;
			gc.gridx = 0;
			gc.gridy ++;
			Ressource res = listePersonne.get(i);
			String nom = ((Personne) res).getPrenomNom();
			panel.add(creerLabel(nom, res), gc);
			
			gc.gridwidth = GridBagConstraints.REMAINDER;
			gc.gridx ++;
			panel.add(afficheMoisRes(res), gc);
			//panel.add(afficheMoisRes(res), gc);
			
		}
		return panel;
	}
	
	/**
	 * met à jour debut et fin qu'il  faut afficher pour voir toute les ressource de l'act
	 */
	/*private void duree() {
		LocalDate [] duree = new LocalDate[2]; // la premiere case correspon
		
		LocalDate debut, lastDebut; 
		
		CreneauHoraire fin;
		//fin = res.getDernierCreneauActivite(activite);

		ArrayList<Ressource> listeRes = activite.getListeRessourceType(Ressource.PERSONNE);
		for (int i=0; i<listeRes.size(); i++) {
			Ressource res = listeRes.get(i);
			debut = res.getPremierCreneauActivite(activite);
			if (debut.getDebut().get)
		}

		this.debut = debut;
		this.fin = fin;
}*/
	
	/*private JPanel afficheEDTRessource(Ressource res) {
		JPanel panel = new JPanel();
		int nbMois = 
		panel.setBackground(couleurFond);
		return panel;
		
	}*/
	
	private JPanel afficheMoisRes(Ressource res) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		for (int i=1; i<52;i++) {
			JPanel semaine = new JPanel();
					semaine.setBackground(PanelPrincipal.BLANC);
					if(travailleSemaine(res.getSemaineEDT(Temps.getAnnee(), i))) {
						semaine.setBackground(PanelPrincipal.BLEU1);
					}
					panel.add(semaine);
		}
		panel.setBackground(couleurFond);
		return panel;
	}
	
	private boolean travailleSemaine(CreneauHoraire [][] liste) {
		boolean travaille = false;
		for (int i=0; i<liste.length; i++) {
			for (int j=0; j<liste[0].length; j++) {
				if (liste[i][j] != null && liste[i][j].getActivite().getId() == activite.getId()) {
					travaille = true;
					break;
				}
			}
			if (travaille) {
				break;
			}
		}
		return travaille;
	}
	
	private JLabel creerLabel(String nom, Ressource ressource) {
		JLabel label = new JLabel(nom);
		label.setBackground(couleurFond);
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		label.addMouseListener(new SourisRessourceListener(entreprise, ressource));
		return label;
	}

	private JLabel labelMois(int numeroMois) {
		JLabel label = new JLabel();
		switch (numeroMois) {
		case 0:
			label.setText("Janvier");
			break;
		case 1:
			label.setText("Fevrier");
			break;
		case 2:
			label.setText("Mars");
			break;
		case 3:
			label.setText("Avril");
			break;
		case 4:
			label.setText("Mai");
			break;
		case 5:
			label.setText("Juin");
			break;
		case 6:
			label.setText("Juillet");
			break;
		case 7:
			label.setText("Aout");
			break;
		case 8:
			label.setText("Septembre");
			break;
		case 9:
			label.setText("Octobre");
			break;
		case 10:
			label.setText("Novembre");
			break;
		case 11:
			label.setText("Decembre");
			break;
		
		}
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.add(label);
		return label;
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

}
