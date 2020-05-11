package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Fenetre.FenetreModal;
import Fenetre.FenetrePrincipale;
import Model.Entreprise;

public class PanelTache extends JPanel{

	private Entreprise entreprise;
	private Color couleurFond;
	private boolean afficheTicket = false;
	public final static int TICKET = 0, OPTIMISATION = 1;
	private JButton boutonNouveauTicket;
	
	public PanelTache(Entreprise entreprise) {
		this.entreprise = entreprise;
		couleurFond = PanelPrincipal.BLEU1;
		afficheTicket = entreprise.getAfficheTicket();
		boutonNouveauTicket = new JButton("Nouveau ticket");
		boutonNouveauTicket.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	nouveauTicket();
	        }
	    });			
		
		afficheInterface();
	}
	
	private void afficheInterface() {
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 3;
		
		gc.weighty = 9;

		if (afficheTicket) {
			afficheTicket(gc);
		}
		
		
		gc.fill = GridBagConstraints.BOTH;
		//gc.ipadx = gc.anchor = GridBagConstraints.HORIZONTAL;
		//gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(5, 15, 5, 15);
		gc.gridx = 2;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		//gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(creerLabelIco(new ImageIcon("images/mail_white.png"), TICKET), gc);
	}
	
	

	private void afficheTicket(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.BOTH;
		//gc.ipadx = gc.anchor = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(5, 15, 5, 15);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.RELATIVE;		
		
		//tickets recu
		gc.gridheight = 1;
		this.add(creerLabel("Ticket recu", true));

		gc.gridy ++;
		gc.gridheight = 3;
		
		//ticket envoyé
		gc.gridy ++;
		gc.gridheight = 1;
		this.add(creerLabel("Ticket recu", true));

		gc.gridy ++;
		gc.gridheight = 3;
		
		//bouton nouveau ticket
		gc.gridy ++;
		gc.gridheight = 1;
		this.add(boutonNouveauTicket, gc);
		
	}

	private JLabel creerLabel(String nom, boolean estGras) {
		JLabel label = new JLabel(nom);
		if(estGras) {
			label.setFont(new Font("Arial", Font.BOLD, 15));
		}
		else {
			label.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		label.setForeground(PanelPrincipal.BLANC);
		return label;
	}
	
	private JLabel creerLabelIco(ImageIcon icon, int tache) {
		JLabel label = new JLabel(icon);
		label.setBackground(Color.red);
		label.addMouseListener(new MouseListener() {           
			@Override
			public void mouseClicked(MouseEvent e) {
				entreprise.selectionnerTache(tache);
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

	public void nouveauTicket() {
		new FenetreModal(entreprise, FenetrePrincipale.NouveauTicket);
	}
}
