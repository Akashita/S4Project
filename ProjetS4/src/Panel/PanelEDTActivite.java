package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisRessourceListener;
import Model.Activite;
import Model.CreneauHoraire;
import Model.EDT;
import Model.Entreprise;
import Model.Temps;
import Ressource.Personne;
import Ressource.Ressource;

/**
 * Affiche l'emploi du temps de l'activite sous forme de diagramme de gantt
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
	private ArrayList<EDT> listeEDTpersonne = new ArrayList<EDT>();
	private LocalDate debut, fin;
	private int nombreDeMois;
	private ArrayList<Integer> listeNumeroSemaine;
	
	
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
			for (int i=0; i<listePersonne.size();i++) {
				Ressource res = listePersonne.get(i);
				EDT edt = entreprise.getEDTRessource(res.getType(), res.getId());
				listeEDTpersonne.add(edt);
			}
			debut = Temps.getAujourdhui();
			fin = entreprise.getLocalDateFinDuneActivite(listeEDTpersonne, activite);
			LocalDate finProjet = entreprise.getProjetDeActiviteParId(activite.getId()).getDeadline();
			if (Temps.dateUnEstSuperieurDateDeux(finProjet, fin)) {
				fin = finProjet;
			}
			
			listeNumeroSemaine = Temps.getNumSemainesEntreDates(debut, fin);
			nombreDeMois = Temps.nombreDeMoisEntreDeuxDates(debut,fin);
			this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);			
		}
	}
	
	private JPanel afficherEmploiDuTemps() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.BOTH;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(2, 2, 2, 2);
		gc.weightx = 1;	
		gc.weighty = nbPersonne+1;

		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridx = 0;
		gc.gridy = 0;
		panel.add(afficheTitreEtEchelle(), gc);

		
		
		for (int i=0; i<nbPersonne;i++) {
			gc.gridy ++;
			panel.add(afficheRessourceEtDiagramme(i), gc);			
		}
		return panel;
	}
	
	private JPanel afficheTitreEtEchelle() {
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.add(creerLabel(activite.getTitre(), true));
		p.add(afficheEchelle());
		return p;
	}

	private JPanel afficheRessourceEtDiagramme(int index) {
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		Ressource res = listePersonne.get(index);
		String nom = ((Personne) res).getPrenomNom();
		p.add(creerLabel(nom, res));
		p.add(afficheDiagrammeGantt(index));
		return p;
	}

	private JPanel afficheEchelle() {
		int annee = debut.getYear();
		int nbAnnee=1;
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		p.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		gc.insets = new Insets(5, 5, 5, 5);	
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = nombreDeMois;
		
		gc.weighty = 2;
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		int numeroMois = debut.getMonthValue();
		for(int i=0; i<=nombreDeMois;i++) {
			if (numeroMois > 12) {
				nbAnnee ++;
				numeroMois = 1;
			}
			p.add(labelMois(numeroMois), gc);
			numeroMois ++;
			gc.gridx ++;
		}
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		for(int i=0; i<nbAnnee;i++) {
			p.add(new JLabel(Integer.toString(annee+i)), gc);
			gc.gridx ++;	
		}
		return p;
	}
	
	private JPanel afficheDiagrammeGantt(int index) {
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		EDT edtR = listeEDTpersonne.get(index);
		int annee = debut.getYear();
		
		for(int i=0; i<listeNumeroSemaine.size();i++) {
			int numSemaine = listeNumeroSemaine.get(i);
			if (i>0 &&  numSemaine < listeNumeroSemaine.get(i-1) ) { // on verifie si on a passe une annee
				annee ++;
			}
			JPanel semaine = new JPanel();
			semaine.setBackground(PanelPrincipal.BLANC);
			CreneauHoraire [][] tableauCreneau = edtR.getSemaineEDT(annee, numSemaine);

			for (int j=0; j<5; j++) {
				JPanel jour = new JPanel();
				if (travailleJour(tableauCreneau, j)) {
					jour.setBackground(PanelPrincipal.BLEU1);
				}
				else {
					jour.setBackground(PanelPrincipal.BLANC);	
				}
				p.add(jour);

			}
		}
		
		return p;
	}

	private boolean travailleJour(CreneauHoraire [][] liste, int numeroJour) {
		boolean travaille = false;
		for (int i=0; i<liste[0].length; i++) {
			if (liste[numeroJour][i] != null && liste[numeroJour][i].getActivite().getId() == activite.getId()) {
				travaille = true;
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
		case 1:
			label.setText("Janvier");
			break;
		case 2:
			label.setText("Fevrier");
			break;
		case 3:
			label.setText("Mars");
			break;
		case 4:
			label.setText("Avril");
			break;
		case 5:
			label.setText("Mai");
			break;
		case 6:
			label.setText("Juin");
			break;
		case 7:
			label.setText("Juillet");
			break;
		case 8:
			label.setText("Aout");
			break;
		case 9:
			label.setText("Septembre");
			break;
		case 10:
			label.setText("Octobre");
			break;
		case 11:
			label.setText("Novembre");
			break;
		case 12:
			label.setText("Decembre");
			break;
		
		}
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
