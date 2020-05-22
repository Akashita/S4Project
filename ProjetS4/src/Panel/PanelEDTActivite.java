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
 * Affiche l'emploi du temps de l'activité sous forme de diagramme de gantt
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
			fin = entreprise.getProjetDeActiviteParId(activite.getId()).getDeadline();
			
			LocalDate tempoFin = entreprise.getDebutFinActivite(listeEDTpersonne, activite);

			/*for(int i=0; i<listeEDTpersonne.size();i++) {
				EDT edt = listeEDTpersonne.get(i);
				LocalDate tempoFin = edt.getDerniereDateActivite(activite);
				if (Temps.dateUnEstSuperieurDateDeux(tempoFin, fin)) {
					fin = tempoFin;
				}
			}*/
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
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		int nbMois = 12;
		gc.weightx = nbMois+1;
		
		gc.weighty = nbPersonne+1;
		
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
			panel.add(afficheDiagrammeGantt(i), gc);
			
		}
		return panel;
	}
	
	
	private JPanel afficheDiagrammeGantt(int index) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		EDT edtR = listeEDTpersonne.get(index);
		int annee = debut.getYear();
		
		for(int i=0; i<listeNumeroSemaine.size();i++) {
			int numSemaine = listeNumeroSemaine.get(i);
			if (numSemaine < listeNumeroSemaine.get(i-1) && i>0) { // on vérifie si on a passé une année
				annee ++;
			}
			JPanel semaine = new JPanel();
			semaine.setBackground(PanelPrincipal.BLANC);
			CreneauHoraire [][] tableauCreneau = edtR.getSemaineEDT(annee, numSemaine);

			if(travailleSemaine(tableauCreneau)) {
				semaine.setBackground(PanelPrincipal.BLEU1);
			}

			p.add(semaine);
		}
		
		return p;
	}
	
	/**
	 * met Ã  jour debut et fin qu'il  faut afficher pour voir toute les ressource de l'act
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
	
	/*private JPanel afficheMoisRes(Ressource res) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		EDT edtR = entreprise.getEDTRessource(res.getType(), res.getId());
	
		for (int i=1; i<52;i++) {
			JPanel semaine = new JPanel();
					semaine.setBackground(PanelPrincipal.BLANC);
					CreneauHoraire [][] tableauCreneau = edtR.getSemaineEDT(Temps.getAnnee(), i);

					if(travailleSemaine(tableauCreneau)) {
						semaine.setBackground(PanelPrincipal.BLEU1);
					}
					panel.add(semaine);
		}
		panel.setBackground(couleurFond);
		return panel;
	}
	*/
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
