package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import Fenetre.FenetreModal;
import Fenetre.FenetrePrincipale;
import GestionTicket.Ticket;
import Model.Entreprise;
import Ressource.Ressource;
import SQL.JavaSQLTicket;

/**
 * Affiche la liste des tache (ticket et optimisation)
 * Et affiche la liste des ticket ou la liste de propostion d'optimisation
 * @author Damien
 *
 */
public class PanelTache extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Color couleurFond;
	public final static int RIEN = -1, TICKET = 0, OPTIMISATION = 1;

	private JButton boutonNouveauTicket;
	
	
	public PanelTache(Entreprise entreprise) {
		this.entreprise = entreprise;
		couleurFond = PanelPrincipal.BLEU1;
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
		
		gc.weighty = 7;

		switch (entreprise.getAfficheTache()) {
		case TICKET: afficheTicket(gc);
		break;
		case OPTIMISATION: afficheOptimisation(gc);
		break;

		default:
			break;
		}
		
		
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 15, 0, 15);
		gc.gridx = 2;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight= 1;
		this.add(creerImageTache(TICKET), gc);

		gc.gridy = 6;
		this.add(creerImageTache(OPTIMISATION), gc);
}
	
	private JLabel creerImageTache(int tache) {
		int tacheSelectionner = entreprise.getAfficheTache();
		ImageIcon ico = new ImageIcon();
		switch (tache) {
		case TICKET:
			if (tacheSelectionner == TICKET) {
				ico = new ImageIcon("images/mail_grey.png");
			}
			else {
				ico = new ImageIcon("images/mail_white.png");
			}		
			break;
		case OPTIMISATION:
			if (tacheSelectionner == OPTIMISATION) {
				ico = new ImageIcon("images/gear_grey.png");
			}
			else {
				ico = new ImageIcon("images/gear_white.png");
			}
			break;

		default:
			break;
		}
		
		
		JLabel label = new JLabel(ico);
		label.addMouseListener(new MouseListener() {           
			@Override
			public void mouseClicked(MouseEvent e) {
				entreprise.setAfficheTache(tache);
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


	private void afficheTicket(GridBagConstraints gc) {
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
		ArrayList<Ticket> ticketRecuTab = new ArrayList<Ticket>();
		ArrayList<Ticket> ticketEnvTab = new ArrayList<Ticket>();


		try {
			ticketTab = JavaSQLTicket.affiche();

			for (int i = 0; i < ticketTab.size(); i++) {
					if (entreprise.getUser().getId() == ticketTab.get(i).getIdReceveur()) {
						ticketRecuTab.add(ticketTab.get(i));
					}
					else if (entreprise.getUser().getId() == ticketTab.get(i).getIdEnvoyeur()) {
						ticketEnvTab.add(ticketTab.get(i));

					}
		
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		gc.fill = GridBagConstraints.CENTER;
		//gc.ipadx = gc.anchor = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(0, 10, 0, 10);
		gc.gridx = 0;
		gc.gridy = 0;
		
		//tickets recu
		gc.gridheight = 1;
		this.add(creerLabel("Ticket recus", true), gc);

		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy=1;
		gc.gridheight = 2;
		this.add(creerList(ticketRecuTab), gc);
		
		
		//ticket envoyé
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridheight = 1;
		gc.gridy = 3;
		this.add(creerLabel("Ticket envoyé", true), gc);

		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 4;
		gc.gridheight = 2;
		this.add(creerList(ticketEnvTab), gc);
		
		//bouton nouveau ticket
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.SOUTH;
		gc.gridy = 6;
		gc.gridheight = 1;
		this.add(boutonNouveauTicket, gc);
		
	}

	private void afficheOptimisation (GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		//gc.ipadx = gc.anchor = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(0, 10, 0, 10);
		gc.gridx = 0;
		gc.gridy = 0;
		
		//tickets recu
		gc.gridheight = 1;
		this.add(creerLabel("Proposition du systeme", true), gc);

		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy=1;
		gc.gridheight = 2;
		//this.add(creerList(ticketRecuTab), gc);
		
		
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
				entreprise.setAfficheTache(tache);
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

	private JScrollPane creerList(ArrayList<Ticket> lt) {
		Ticket [] tt = new Ticket [lt.size()];
		for (int i=0; i<tt.length; i++) {
			tt[i] = lt.get(i);
		}
		JList<Ticket> jlt = new JList<Ticket>(tt);
		jlt.setBackground(couleurFond);
		
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.setForeground(PanelPrincipal.BLANC);
		jlt.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("unchecked")
				JList<Ticket> jlt = (JList<Ticket>)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            //int index = jlt.locationToIndex(evt.getPoint());
		            afficheTicket(jlt.getSelectedValue());
		        }
		    }
		});
		
		
		JScrollPane scrollPane = new JScrollPane(jlt);
		scrollPane.setViewportView(jlt);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
	}
	
	public void afficheTicket(Ticket t) {
		entreprise.afficheInfoTicket(t);
	}
	
	public void nouveauTicket() {
		new FenetreModal(entreprise, FenetrePrincipale.NouveauTicket);
	}
}
