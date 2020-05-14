package Panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import EcouteurEvenement.SourisProjetListener;
import Model.Entreprise;
import Model.Projet;

public class PanelProjet extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Color couleurFond;
	
	public PanelProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		couleurFond = PanelPrincipal.BLEU2;
		this.setBackground(couleurFond);
		afficheInterface();
	}
		

	public void afficheInterface() {
		this.setLayout(new GridBagLayout());		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.insets = new Insets(5, 5, 5, 5);
		

		gc.weightx = 3;
		
		gc.weighty = 1;

		
		gc.fill = GridBagConstraints.BOTH;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(panelProjet(),gc);

	}

	
	
	private JScrollPane panelProjet() {
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		ArrayList<Projet> lp = entreprise.getListeProjet();
		p.setLayout(new GridLayout(1,lp.size()));

		for (int i=0; i<lp.size(); i++) {
			Projet projet = lp.get(i);							
			PanelInfoProjet pip = new PanelInfoProjet(entreprise);
			pip.addMouseListener(new SourisProjetListener(entreprise, projet));	
			p.add(pip);								
		}
		JScrollPane jsp = new JScrollPane(p);
		jsp.setViewportView(p);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		return jsp;

	}
	
	
	
	
	
}
