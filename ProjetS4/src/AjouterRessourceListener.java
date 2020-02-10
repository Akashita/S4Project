import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AjouterRessourceListener implements ActionListener {
	Entreprise entreprise;
	String type;
	
	public AjouterRessourceListener(Entreprise entreprise, String type) {
		this.entreprise = entreprise;
		this.type = type;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreAjouterRessource(entreprise, type);
	}

}
