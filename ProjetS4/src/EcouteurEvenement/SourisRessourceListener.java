package EcouteurEvenement;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import Model.Entreprise;
import Ressource.Ressource;

/**
 * Cette classe permet l'interaction utilisateur machine avec la souris
 * pour pouvoir selectionner une ressource (presente dans la barre à gauche)
 * 
 * pour pouvoir afficher ses informations, la modifier ou la supprimer
 * 
 * @author damien planchamp
 *
 */
 public class SourisRessourceListener implements MouseListener {
	 //PanelInfoProjet pip;
	 private Ressource res;
	 private Entreprise entreprise;
	
	public SourisRessourceListener(Entreprise entreprise, Ressource res) {
		this.entreprise = entreprise;
		this.res = res;
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//pip.afficheInfoRessource(res);
		entreprise.afficheInfoRessource(res, res.getType());
		//new FenetreInfoRessource(entreprise, res);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
