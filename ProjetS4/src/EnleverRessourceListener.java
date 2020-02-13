import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnleverRessourceListener implements ActionListener {
	Entreprise entreprise;
	
	public EnleverRessourceListener(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreEnleverRessource(entreprise);
	}

}
