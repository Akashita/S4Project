import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

public class PanelPrincipal extends JPanel implements Observer{
	private Entreprise entreprise;

	public PanelPrincipal (Entreprise entreprise) {
		this.entreprise = entreprise;
		entreprise.addObserver(this);
	}
	
	public void paint(Graphics g){
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		entreprise.dessineToi(g);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();		
	}

}
