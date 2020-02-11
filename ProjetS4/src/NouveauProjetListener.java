import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NouveauProjetListener implements ActionListener {
	Entreprise entreprise;
	PanelProjet pp;
	
	public NouveauProjetListener(Entreprise entreprise, PanelProjet pp){
		this.entreprise = entreprise;
		this.pp = pp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new FenetreNouveauProjet(entreprise, pp);
	}

}
