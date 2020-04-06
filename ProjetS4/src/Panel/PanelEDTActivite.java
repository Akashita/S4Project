package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import Model.Activite;

public class PanelEDTActivite extends JPanel{

	private Activite activite;
	private int nbPersonne;
	private Color couleurProjet;
	private Color couleurActivite;
	
	
	public PanelEDTActivite(Activite activite) {
		this.activite = activite;
		this.setLayout(new BorderLayout());
		this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
	}
	
	private JPanel afficherEmploiDuTemps() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		return panel;
		
	}

	private JPanel affichePlanningRessource() { 
		JPanel annee  = new JPanel();
		annee.setLayout(new GridLayout(0, 12));
		JPanel mois  = new JPanel();
		annee.setLayout(new GridLayout(0, 4));
		JPanel semaine  = new JPanel();
		annee.setLayout(new GridLayout(0, 7));
		
		
		return annee;
	}
}
