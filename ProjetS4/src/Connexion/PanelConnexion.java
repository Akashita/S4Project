package Connexion;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Entreprise;
import Panel.PanelPrincipal;
import Ressource.Personne;
import SQL.JavaSQLPersonne;

public class PanelConnexion extends JPanel{

	private FenetreConnexion fc;
    private JTextField textFieldLogin = new JTextField(), textFieldMdp = new JTextField();
    private JButton boutonConnexion, boutonQuitter;
    private Color couleurFond;
    private Personne user = null;
    
    
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
		this.add(creerTitre("Veillez entrer vos coordonn�es"), gc);
		
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
		this.add(textFieldMdp, gc);	

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

    //cr�ation du model
    private void actionConnexion(FenetreConnexion fc) {
    	//ABSOLUMENT DEGUEU A MODIFIER APRES LE 12 
    	Boolean compteExiste = false;
    	Boolean compteAdmin = false;
    	Personne user = null;
    	
    	
    	ArrayList<Personne> personneTab = new ArrayList<Personne>();
		JavaSQLPersonne sqlPersonne = new JavaSQLPersonne();
		try {
			personneTab = sqlPersonne.affiche();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < personneTab.size(); i++) {
			String log = textFieldLogin.getText();
			String mdp = textFieldMdp.getText();		
			
			String logI = personneTab.get(i).getLogin();
			String mdpI = personneTab.get(i).getMdp();

			if ( logI.equals(log)) {
				if ( mdpI.equals(mdp)) {
					compteExiste = true;
					user = personneTab.get(i);
					if (personneTab.get(i).getRole().equals(Personne.DEBUG)) {
						compteAdmin = true;
					}
				}
			}
		}
		
		
		
		if (!textFieldLogin.getText().isEmpty()) {
			if (!textFieldMdp.getText().isEmpty()) {
				if (compteExiste) {
					if (compteAdmin) {
						new Entreprise("debugBDD");
					}
					else {
						new Entreprise(user);
						fc.dispose();
					}
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veuillez ecrire le mot de passe", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veuillez ecrire le login", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}	
    }
    
    private void actionQuitter(FenetreConnexion fc) {
    	fc.dispose();
    }
}
