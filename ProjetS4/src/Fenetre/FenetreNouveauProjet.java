package Fenetre;
import java.awt.BorderLayout;

import Model.Entreprise;
import Panel_Fenetre.PanelNouveauProjet;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer un nouveau projet 
 * 
 * @author damien planchamp
 *
 */
public class FenetreNouveauProjet extends FenetreModal{

		/*
		 * retourne un panel avec le champs de saisie et un label
		 */
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(1, 1));
	    if (type == "nom") {
		    panel.add(new JLabel("Veillez indiquer le nom du projet : "));
	    	nom = new JTextField();
		    panel.add(nom);	    	
	    }
	    if (type == "priorite") {
		    panel.add(new JLabel("Veillez indiquez la priorité du projet: "));
		    priorite = new JTextField();
		    panel.add(priorite);	    	
	    }
		return panel;
	}
	
	@Override
	public JPanel ajoutBouton() {
		JPanel panel = new JPanel();
		panel.setSize(this.getWidth(),10);
		panel.add(creerBouttonAnnuler());
		panel.add(creerBouttonAjout());
		return panel;
	}
	
	/**
	 * 
	 * @param type
	 * @return le bouton qui permet l'action pour creer la nouvelle activité
	 */
	private JButton creerBouttonAjout() {
		JButton bouton = new JButton("Creer");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	creerProjet();	 
	        }
	    });			
	    return bouton;
	}
	
	/**
	 * 
	 * @return le bouton qui permet l'action pour fermer la fenetre
	 */
	private JButton creerBouttonAnnuler() {
		JButton bouton = new JButton("Annuler");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	        }
	    });			
	    return bouton;
	}
	
	
	
	/**
	 * verifie les informations saisie et ajoute le projet au model (Entreprise)
	 */
	private void creerProjet() {
		if (!nom.getText().isEmpty()) {
			if (!priorite.getText().isEmpty()) {
				if (estUnEntier(priorite.getText())) {
					int index = comboBoxPersonne.getSelectedIndex();
				    Ressource ressource = listePersonne.get(index);
					entreprise.creerProjet((Personne)ressource, nom.getText(), Integer.parseInt(priorite.getText()));
					dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veillez ecrire sa priorité", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	private boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
	private static final long serialVersionUID = 1L;

	public FenetreNouveauProjet(Entreprise entreprise) {
		super(entreprise, "Creation projet", 400, 300);
		panelInterface = new PanelNouveauProjet(entreprise, this);
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
}
