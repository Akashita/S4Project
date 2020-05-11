package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import Fenetre.FenetreModal;
import Fenetre.FenetrePrincipale;
import GestionTicket.Ticket;
import Model.Entreprise;
import SQL.JavaSQLTicket;

public class PanelTache extends JPanel{

	private Entreprise entreprise;
	private Color couleurFond;
	private boolean afficheTicket = false;
	public final static int TICKET = 0, OPTIMISATION = 1;
	private JButton boutonNouveauTicket;
	private JList<Ticket> ticketRecu, ticketEnvoye;
	
	
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
		
		
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(5, 15, 5, 15);
		gc.gridx = 2;
		gc.gridy = 4;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(creerLabelIco(new ImageIcon("images/mail_white.png"), TICKET), gc);
	}
	
	

	private void afficheTicket(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		//gc.ipadx = gc.anchor = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(5, 15, 5, 15);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.RELATIVE;		
		
		//tickets recu
		gc.gridheight = 1;
		this.add(creerLabel("Ticket recus", true), gc);
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
		ArrayList<Ticket> ticketRecuTab = null;
		ArrayList<Ticket> ticketEnvTab = null;


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
	
		gc.gridy ++;
		gc.gridheight = 2;
		this.add(creerList(ticketRecuTab), gc);
		
		
		//ticket envoy�
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.gridy ++;
		this.add(creerLabel("Ticket envoy�", true), gc);

		gc.gridy ++;
		gc.gridheight = 2;
		this.add(creerList(ticketEnvTab), gc);
		
		//bouton nouveau ticket
		gc.ipady = gc.anchor = GridBagConstraints.SOUTH;
		gc.gridy ++;
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

	private JList creerList(ArrayList<Ticket> lt) {
		Ticket [] tt = new Ticket [lt.size()];
		for (int i=0; i<tt.length; i++) {
			tt[i] = lt.get(i);
		}
		JList<Ticket> jlt = new JList<Ticket>();
		jlt.setBackground(couleurFond);
		jlt.setFont(new Font("Arial", Font.PLAIN, 15));
		return jlt;
	}
	
	public void nouveauTicket() {
		new FenetreModal(entreprise, FenetrePrincipale.NouveauTicket);
	}
}
