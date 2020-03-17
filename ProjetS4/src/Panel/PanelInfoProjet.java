package Panel;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisActiviteListener;
import EcouteurEvenement.SourisProjetListener;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;

public class PanelInfoProjet extends JPanel{
	
	Entreprise entreprise;
	
	public PanelInfoProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		if (entreprise.getProjetSelectionner() != null) {
			if (entreprise.getProjetSelectionner().getListe().size()>0) {//on vérifie qu'il y a au moins une activité
				afficheInterface();
			}
		}
	}
	
	public void afficheInterface() {
		Projet projet = entreprise.getProjetSelectionner();
		ArrayList<Activite> listeActivite = projet.getListe();
	    this.setBorder(BorderFactory.createTitledBorder("Liste des activités"));
		this.setBackground(Color.WHITE);			
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (int i=0; i<listeActivite.size(); i++) {
			Activite act = listeActivite.get(i);
			boolean selectionner = false;
			int idActiviteSelectionner = entreprise.getActiviteSelectionner().getId();

			if(act.getId() == idActiviteSelectionner) {
				selectionner = true;
			}
			this.add(panelActivite(act, selectionner));
		}
	}

	private JPanel panelActivite(Activite act, boolean selectionner) {
		JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.addMouseListener(new SourisActiviteListener(this, act.getId()));
		if(selectionner) {
			panel.setBackground(Color.BLUE);
		}
		else {
			panel.setBackground(Color.WHITE);			
		}
	    panel.setBorder(BorderFactory.createTitledBorder(act.getTitre()));
	    panel.add(new JLabel("Charge de travail: " + act.getCharge() + " jour/homme"));
	    panel.add(new JLabel("Ordre: " + act.getOrdre()));
	    panel.add(new JLabel("Commence le: " + act.getJourDebut()));
		return panel;
	}
	
	public void selectionnerActivite(int idActiviteSelectionner) {
		entreprise.deselectionnerActivite();
		entreprise.selectionnerActivite(idActiviteSelectionner);
	} 
}
