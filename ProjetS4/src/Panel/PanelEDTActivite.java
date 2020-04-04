package Panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import Model.Activite;

public class PanelEDTActivite extends JPanel{

	private Activite activite;
	
	public PanelEDTActivite(Activite activite) {
		this.activite = activite;
		this.setLayout(new BorderLayout());
		this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
	}
	
	private JPanel afficherEmploiDuTemps() {
		JPanel panel = new JPanel();
		
		return panel;
		
	}

}
