package Connexion;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Model.Entreprise;
import Ressource.Personne;
import SQL.JavaSQL;


/**
 * Affiche le contenu de la fenetre connexion
 * @author Damien
 *
 */
public class PanelConnexion extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetreConnexion fc;
    private JTextField textFieldLogin = new JTextField();
    private JPasswordField mdpField = new JPasswordField();
    private JButton boutonConnexion, boutonQuitter;
    private Color couleurFond;
    
    
    public PanelConnexion(FenetreConnexion fc) {
    	this.fc = fc;
    	couleurFond = fc.getBackground();
    	initialiseBouton();
    	creerInterface();
	}
    
    private void creerInterface() {
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.CENTER;	
		gc.insets = new Insets(5, 5, 5, 5);	
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 3;	
		gc.weighty = 4;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		this.add(creerTitre("Veuillez entrer vos coordonnées"), gc);
		
		//--------------- zone saisie
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy ++;
		this.add(creerTexte("Login : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldLogin, gc);

		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy ++;
		this.add(creerTexte("Mot de passe : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(mdpField, gc);	

		//------------------- bouton 
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy++;
		this.add(boutonQuitter, gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(boutonConnexion, gc);
    }

	//------------------------------------------------------------------------->>>>>>>>>>>> Label d'affichage de texte
	protected JLabel creerTitre(String titre) {
			JLabel label = new JLabel(titre);
			label.setFont(new Font("Arial", Font.BOLD, 15));
			return label;
		}
		
	protected JLabel creerTexte(String titre) {
			JLabel label = new JLabel(titre);
			label.setFont(new Font("Arial", Font.PLAIN, 15));
			label.setBackground(Color.BLUE);
			return label;
		}

    //----------------------------------------------------------------------------------->>>>>>> Gestion bouton
    private void initialiseBouton() {
    	boutonConnexion = new JButton("Connexion");
    	boutonConnexion.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionConnexion(fc);
	        }
	    });			
    	boutonQuitter = new JButton("Quitter");
		boutonQuitter.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionQuitter(fc);
	        }
	    });			
    }   

    //connexion au compte et creation du model
    @SuppressWarnings("deprecation")
	private void actionConnexion(FenetreConnexion fc) {
    	//ABSOLUMENT DEGUEU A MODIFIER APRES LE 12 
    	
    	String login = textFieldLogin.getText();
    	String mdp = mdpField.getText();
		if (!login.isEmpty()) {
			if (!mdp.isEmpty()) {
				Personne user = (Personne) Entreprise.chercheUser(login, mdp);
				if (Entreprise.chercheUser(login, mdp) != null) {
					new Entreprise(user);
					fc.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Le login et/ou le mot de passe est/sont incorrect/s", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veuillez écrire le mot de passe", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veuillez écrire le login", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}	
    }
    
 
    
    private void actionQuitter(FenetreConnexion fc) {
		JavaSQL.finCo();
		fc.dispose();
    }
}
