package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Model.Entreprise;
import Ressource.Ressource;

/**
 * Affiche personne, salle, calculateur, et recherche
 * Affiche la liste concerné
 * @author Damien
 *
 */
public class PanelRessource extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Color couleurFond;
	private JTextField rechercher;
	
	public PanelRessource(Entreprise entreprise) {
		this.entreprise= entreprise;
		couleurFond = PanelPrincipal.BLEU1;
		afficheInterface();
	}
	
	
	public void afficheInterface() {
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.BOTH;
		
		gc.insets = new Insets(5, 10, 5, 10);
		
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.SOUTH;
		gc.weightx = 3;
		gc.weighty = 3;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(creerImageRessource(Ressource.PERSONNE), gc);
		gc.gridy ++;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		this.add(creerImageRessource(Ressource.SALLE), gc);
		gc.gridy ++;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		this.add(creerImageRessource(Ressource.CALCULATEUR), gc);
		
		
		if (entreprise.getAfficheListeRessource() != Ressource.RIEN) {
			gc.gridx ++;
			gc.gridy = 0;
			gc.gridwidth = GridBagConstraints.REMAINDER;
			gc.gridheight = GridBagConstraints.REMAINDER;
			int type = entreprise.getAfficheListeRessource();
			ArrayList<Ressource> l = entreprise.getListeRessourceEntrepriseParType(type);
			this.add(creerList(l), gc);
		}
	}

	
	private JLabel creerImageRessource(int type) {
		int ressourceSelectionner = entreprise.getAfficheListeRessource();
		ImageIcon ico = new ImageIcon();
		switch (type) {
		case Ressource.PERSONNE:
			if (ressourceSelectionner == Ressource.PERSONNE) {
				ico = new ImageIcon("images/user_grey_grand.png");
			}
			else {
				ico = new ImageIcon("images/user_white.png");
			}		
			break;
		case Ressource.SALLE:
			if (ressourceSelectionner == Ressource.SALLE) {
				ico = new ImageIcon("images/door_grey_grand.png");
			}
			else {
				ico = new ImageIcon("images/door_white.png");
			}
			break;
		case Ressource.CALCULATEUR:
			if (ressourceSelectionner == Ressource.CALCULATEUR) {
				ico = new ImageIcon("images/computer_grey_grand.png");
			}
			else {
				ico = new ImageIcon("images/computer_white.png");
			}
			break;

		default:
			break;
		}
		
		
		JLabel label = new JLabel(ico);
		label.addMouseListener(new MouseListener() {           
			@Override
			public void mouseClicked(MouseEvent e) {
				entreprise.setAfficheListeRessource(type);
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

	private JScrollPane creerList(ArrayList<Ressource> lr) {
		Ressource [] tr = new Ressource [lr.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = lr.get(i);
		}
		JList<Ressource> jlt = new JList<Ressource>(tr);
		jlt.setBackground(couleurFond);
		
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.setForeground(PanelPrincipal.BLANC);
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
			entreprise.afficheInfoRessource(r, r.getId());
	}

	private void afficherRechercher() {
    	rechercher = new JTextField("rechercher");
    	rechercher.getFont().deriveFont(Font.ITALIC);
    	rechercher.setForeground(PanelPrincipal.GRIS2);
    	rechercher.addMouseListener(new MouseListener() {           
			@Override
			public void mouseClicked(MouseEvent e) {
    	        JTextField texteField = ((JTextField)e.getSource());
    	        texteField.setText("");
    	        texteField.getFont().deriveFont(Font.PLAIN);
    	        texteField.setForeground(PanelPrincipal.NOIR);
    	        texteField.removeMouseListener(this);
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
	}	

}
