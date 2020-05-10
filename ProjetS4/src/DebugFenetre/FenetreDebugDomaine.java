package DebugFenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import Fenetre.FermerFenetre;
import Model.Entreprise;

public class FenetreDebugDomaine extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	private int type;
	public static final int AFFICHE = 0, AJOUT = 1, SUPPRIME = 2;
	
	public FenetreDebugDomaine(Entreprise entreprise,int type){	
		this.entreprise = entreprise;
		this.type = type;
		
		this.setTitle("gestionBDD domaine");
		this.setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.addWindowListener(new FermerFenetre(this));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.0;
        PanelDebugMenuBarre panelBarre = new PanelDebugMenuBarre(entreprise, this);
		this.add(panelBarre,c);

        c.gridwidth = GridBagConstraints.REMAINDER; //end row

        c.weightx = 0.0;                //reset to the default
        c.weighty = 1.0;               

		PanelDebugActionBarre panelAction = new PanelDebugActionBarre(entreprise, this, PanelDebugActionBarre.DOMAINE);
		this.add(panelAction,c);

        c.gridwidth = GridBagConstraints.REMAINDER; //end row
		

		
        if (type == this.AFFICHE) {

    		PanelDebugAffichage panelAffichage = new PanelDebugAffichage(entreprise,PanelDebugAffichage.DOMAINE);
    		this.add(panelAffichage,c);
    		}
    		
    		else if (type == this.AJOUT) {
    	        c.weighty = 0.0;               

    			PanelDebugAjout panelAjout = new PanelDebugAjout(entreprise,this,PanelDebugAffichage.DOMAINE);
    			this.add(panelAjout,c);
    		}
    		
    		else if (type == this.SUPPRIME) {
    	        c.weighty = 0.0;               

    			PanelDebugSupprime panelSupprime = new PanelDebugSupprime(entreprise,this,PanelDebugAffichage.DOMAINE);
    			this.add(panelSupprime,c);
    		}
		
		this.setVisible(true);	
}
}