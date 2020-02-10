import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NouveauProjetListener implements ActionListener {
	Entreprise entreprise;
	
	public NouveauProjetListener(Entreprise entreprise){
		this.entreprise = entreprise;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new FenetreNouveauProjet(entreprise);
	}

}
