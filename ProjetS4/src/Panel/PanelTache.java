package Panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.CORBA.portable.InputStream;

import Model.Entreprise;
import Ressource.Ressource;

public class PanelTache extends JPanel{

	private Entreprise entreprise;
	private Color couleurFond;
	private boolean afficheTicket = false;
	public final static int TICKET = 0;
	
	
	public PanelTache(Entreprise entreprise) {
		this.entreprise = entreprise;
		couleurFond = PanelPrincipal.BLEU1;
		afficheTicket = entreprise.getAfficheTicket();
		afficheInterface();
	}
	
	private void afficheInterface() {
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 3;
		
		gc.weighty = 4;

		if (afficheTicket) {
			afficheTicket();
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
	
	

	private void afficheTicket() {
		
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

}
