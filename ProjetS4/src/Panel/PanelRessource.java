package Panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisRessourceListener;
import Model.Entreprise;
import Ressource.Personne;
import Ressource.Ressource;

public class PanelRessource extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	
	public PanelRessource(Entreprise entreprise) {
		this.entreprise= entreprise;
		afficheInterface();
	}
	
	private void afficheInterface() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.setBackground(PanelPrincipal.BLEU1);
		this.add(menuRessource(), BorderLayout.WEST);
		ArrayList<String> ressourceAfficher = entreprise.getListeRessourceAfficher();
		if (ressourceAfficher.size() > 0) {
			JPanel panel = new JPanel();
			panel.setBackground(PanelPrincipal.BLEU1);
			panel.setLayout(new BorderLayout());
			panel.add(marge(), BorderLayout.NORTH);
			panel.add(marge(), BorderLayout.WEST);
			panel.add(creerPanelListe(ressourceAfficher), BorderLayout.EAST);
			this.add(panel, BorderLayout.EAST);
		}
		this.revalidate();		
	}

	private JPanel marge() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLEU1);
		panel.setPreferredSize(new Dimension(20,15));
		return panel;
	}

	private JPanel menuRessource() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		panel.setBackground(PanelPrincipal.BLEU1);
		panel.add(new JLabel("                        "));
		
		JPanel panelIco = new JPanel();
		panelIco.setBackground(PanelPrincipal.BLEU1);
		panelIco.setLayout(new GridLayout(3,0));

		panelIco.add(creerLabelIco(new ImageIcon("images/user_white.png"), Ressource.PERSONNE));
		panelIco.add(creerLabelIco(new ImageIcon("images/door_white.png"), Ressource.SALLE));
		panelIco.add(creerLabelIco(new ImageIcon("images/computer_white.png"), Ressource.CALCULATEUR));
		panel.add(panelIco);
		
		//panel.add(new JLabel("      "));

		return panel;
	}
	
	private JLabel creerLabelIco(ImageIcon icon, String type) {
		JLabel label = new JLabel(icon);
		label.addMouseListener(new MouseListener() {           
			@Override
			public void mouseClicked(MouseEvent e) {
				entreprise.selectionnerListeRessource(type);
 			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
    	});
		return label;
	}
	
	private JPanel creerPanelListe(ArrayList<String> ressourceAfficher) {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLEU1);
		panel.setLayout(new GridLayout(ressourceAfficher.size(),0));
		for (int i=0; i<ressourceAfficher.size(); i++) {
			panel.add(creerPanelRessource(ressourceAfficher.get(i)));
		}			
		return panel;
	}
		
	
	private JPanel creerPanelRessource(String type) {
		ArrayList<Ressource> listeRessource = entreprise.getListeRessourceType(type);
		JPanel panelPrincipale = new JPanel();
		panelPrincipale.setBackground(PanelPrincipal.BLEU1);
		panelPrincipale.setLayout(new BorderLayout());
		panelPrincipale.add(creerLabelInterfaceRessource(type), BorderLayout.NORTH);
		
		JPanel principalListe = new JPanel();
		principalListe.setLayout(new BorderLayout());
		principalListe.add(marge(), BorderLayout.NORTH);
		
		JPanel panelListe = new JPanel();
		panelListe.setBackground(PanelPrincipal.BLEU1);

		panelListe.setLayout(new BoxLayout(panelListe, BoxLayout.Y_AXIS));
		panelListe.setPreferredSize(new Dimension(180,this.getHeight()));

		if (listeRessource.size() > 0) {	 
			for (int i=0; i<listeRessource.size(); i++) {
				Ressource ressource = listeRessource.get(i);
				String nom;
				if (ressource.getType() == Ressource.PERSONNE) {
					nom = (((Personne) ressource).getPrenom()) + " " + ressource.getNom();
				}
				else {
					nom = ressource.getNom();
				}
				panelListe.add(creerLabel(nom, ressource));
			}
		}
		principalListe.add(panelListe);
		panelPrincipale.add(principalListe, BorderLayout.WEST);
		return panelPrincipale;			
	}
	
	private JLabel creerLabel(String nom, Ressource ressource) {
		JLabel label = new JLabel(nom);
		label.setForeground(PanelPrincipal.BLANC);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.addMouseListener(new SourisRessourceListener(entreprise, ressource));
		return label;
	}
	
	private JLabel creerLabel(String nom, boolean estGras) {
		JLabel label = new JLabel(nom);
		if(estGras) {
			label.setFont(new Font("Arial", Font.BOLD, 15));
		}
		else {
			label.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return label;
	}

	private JPanel creerLabelInterfaceRessource(String type) {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLEU1);
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel labelTxt = new JLabel();
		JLabel labelIco = new JLabel();
		if (type == Ressource.PERSONNE) {
			labelIco = new JLabel(new ImageIcon("images/user_white.png"));
			labelTxt = creerLabel("  "+Ressource.PERSONNE+"s", true);
		}
		if (type == Ressource.SALLE) {
			labelIco = new JLabel(new ImageIcon("images/door_white.png"));
			labelTxt = creerLabel("  "+Ressource.SALLE+"s", true);					
		}
		if (type == Ressource.CALCULATEUR) {
			labelIco = new JLabel(new ImageIcon("images/computer_white.png"));	
			labelTxt = creerLabel("  "+Ressource.CALCULATEUR+"s", true);	
		}
		labelTxt.setForeground(PanelPrincipal.BLANC);
		panel.add(labelIco);
		panel.add(labelTxt);
		return panel;
	}

}
