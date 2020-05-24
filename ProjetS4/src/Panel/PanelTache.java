package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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
import SQL.JavaSQLTicket;

/**
 * Affiche la liste des tache (ticket et optimisation)
 * Et affiche la liste des ticket ou la liste de propostion d'optimisation du projet selectionner
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
	private ArrayList<Ticket> ticketTab, ticketRecuTab, ticketEnvTab;


	
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
		ticketTab = ticketRecuTab = ticketEnvTab = new ArrayList<Ticket>();
		
		ticketRecuTab = entreprise.getListeTicketRecuDeUserSaufMemeReceveurEnvoyeurPasTransfert(entreprise.getUser().getId());
		ticketEnvTab = entreprise.getListeTicketEnvoyeDeUserSaufMemeReceveurEnvoyeurPasTransfert(entreprise.getUser().getId());

		
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 3;
		
		gc.weighty = 2;

		switch (entreprise.getAfficheTache()) {
		case TICKET: affichePanelTicket(gc);
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
		this.add(panelIcon(TICKET), gc);

		gc.gridy ++;
		this.add(panelIcon(OPTIMISATION), gc);
}
	
	private JPanel panelIcon(int tache) {
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		p.setLayout(new GridLayout(1,2,5,5));
		p.add(creerIcon(tache));
		int nbNotif = 0;
		switch (tache) {
		case TICKET:
			for (int i=0; i<ticketRecuTab.size(); i++) {
				if (ticketRecuTab.get(i).getStatut() == Ticket.NONVU) {
					nbNotif ++;
				}
			}
			break;

		default:
			break;
		}
		if (nbNotif > 0) {
			JPanel panelNotif = new JPanel() ;
			panelNotif.setBackground(PanelPrincipal.NOTIFICATION);
			JLabel notif = new JLabel();
			notif.setText(Integer.toString(nbNotif));
			notif.setForeground(PanelPrincipal.BLANC);
			notif.setFont(new Font("Arial", Font.BOLD, 15));
			panelNotif.add(notif);
			p.add(panelNotif);
		}

		return p;
	}
	
	private JLabel creerIcon(int tache) {
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


	private void affichePanelTicket(GridBagConstraints gcPrincipale) {
		gcPrincipale.fill = GridBagConstraints.BOTH;
		gcPrincipale.gridx = 0;
		gcPrincipale.gridy = 0;	
		gcPrincipale.gridwidth = GridBagConstraints.RELATIVE;
		gcPrincipale.gridheight = GridBagConstraints.REMAINDER;
		
		
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		
		p.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.weightx = 1;
		gc.weighty = 3;
		

		//ticket envoye
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		p.add(afficheTitreEtList("Tickets reçus", ticketRecuTab),gc);

		
		//tickets recu
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = 1;
		gc.gridx = 0;
		gc.gridy ++;
		p.add(afficheTitreEtList("Tickets envoyés", ticketEnvTab),gc);

		
		//bouton nouveau ticket
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridheight = 1;
		gc.gridx = 0;
		gc.gridy ++;
		p.add(boutonNouveauTicket, gc);
		
		this.add(p, gcPrincipale);
	}

	private JPanel afficheTitreEtList(String titre, ArrayList<Ticket> liste) {
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		p.setLayout(new GridLayout(2,1,2,0));
		p.add(creerLabel(titre, true));
		p.add(creerList(liste));

		return p;
	}
	
	private void afficheOptimisation (GridBagConstraints gcPrincipale) {
		gcPrincipale.fill = GridBagConstraints.BOTH;
		gcPrincipale.gridx = 0;
		gcPrincipale.gridy = 0;	
		gcPrincipale.gridwidth = GridBagConstraints.RELATIVE;
		gcPrincipale.gridheight = GridBagConstraints.REMAINDER;
		
		
		JPanel p = new JPanel();
		
		this.add(p, gcPrincipale);
		
		p.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.CENTER;
		//gc.ipadx = gc.anchor = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(0, 10, 0, 10);
		gc.gridx = 0;
		gc.gridy = 0;
		
		//tickets recu
		gc.gridheight = 1;
		p.add(creerLabel("Proposition du système", true), gc);

		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy=1;
		gc.gridheight = 2;
		//p.add(creerList(ticketRecuTab), gc);
		
		
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
