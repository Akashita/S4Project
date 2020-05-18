package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.CreneauHoraire;
import Model.Temps;
import Ressource.Ressource;

/**
 * Genere l'interface de l'emploi du temps de la ressource
 * @author Damien
 *
 */
public class PanelEDTRessource extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ressource ressource;
	private CreneauHoraire [][] tableauCreneau;
	private int semaineSelectionner;
	private int anneeSelectionner;
	private Color couleurFond;
	private JSlider slide = new JSlider();
	


	public PanelEDTRessource(Ressource resssource) {
		this.ressource = resssource;
		couleurFond = PanelPrincipal.BLEU2;
		this.setBackground(couleurFond);
		semaineSelectionner = Temps.getSemaine();
		anneeSelectionner = Temps.getAnnee();
		configureSlider();
		afficherEmploiDuTemps();
	}


	private void afficherEmploiDuTemps() {
		tableauCreneau = ressource.getSemaineEDT(anneeSelectionner, semaineSelectionner);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 0, 0, 1);
		
		
		gc.weightx = 6.5; //5 jour de la semaine + 0.5 pour les heures
		gc.weighty = 11.5; //1 pour la date, 9 pour le planning, 0,5 pour le slider, 0,5 pour la liste semaine, 0,5 pour la liste année

		
		//liste heure
		gc.fill = GridBagConstraints.NORTH;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.weightx = 0.5;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		for (int i=0; i<9; i++) {
			this.add(creerLabelInterface(Integer.toString(i+8)+"h", true), gc);
			gc.gridy ++;
		}	
		gc.gridy = 9;
		gc.ipady = gc.anchor = GridBagConstraints.SOUTH;
		this.add(creerLabelInterface(Integer.toString(17)+"h", true), gc);

		
		//liste date
		gc.fill = GridBagConstraints.BOTH;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth=1;
		LocalDate[] tabDate = Temps.getJourSemaine(anneeSelectionner, semaineSelectionner);
		for (int i=0; i<5; i++) {
			LocalDate jourActuel = tabDate[i];
			String jour = jourActuel.getDayOfWeek().toString();	
			int intJourDate = jourActuel.getDayOfMonth();
			String stringJourDate = Integer.toString(intJourDate);
			if (intJourDate < 10) { stringJourDate = 0 + stringJourDate; }
			
			int intMoisDate = jourActuel.getMonthValue();
			String stringMoisDate = Integer.toString(intMoisDate);
			if (intMoisDate < 10) { stringMoisDate = 0 + stringMoisDate; }
			String date = stringJourDate + "/" + stringMoisDate + "/" + jourActuel.getYear();			
			this.add(creerLabelInterface("<html>"+jour+"<br>"+date+"</html>", false), gc);		
			gc.gridx ++;	
		}
		
		gc.insets = new Insets(0, 0, 0, 0);
		//planning
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth=1;
		for (int i=0; i<5; i++) {
			gc.gridy = 1;
			for (int j=0; j<8; j++) {
				this.add(creerLabelCreneau(tableauCreneau[i][j]), gc);
				if (j==3) { // on laisse un espace pour le creneau de la pose
					gc.gridy ++;
				}
				gc.gridy ++;
			}
			gc.gridy=5;
			JPanel panel = new JPanel();
			panel.setBackground(PanelPrincipal.INDISPO);
			if(i == 2) {
				panel.setLayout(new BorderLayout());
				JLabel label = new JLabel("pause déjeuner");
				label.setForeground(PanelPrincipal.BLANC);
				//label.setFont(new Font("Arial", Font.BOLD, 10));
			    label.setHorizontalAlignment(JLabel.CENTER);
			    label.setVerticalAlignment(JLabel.CENTER);
				panel.add(label, BorderLayout.CENTER);				
			}
			this.add(panel, gc);
			gc.gridx ++;
		}
		
		//gc.insets = new Insets(0, 0, 5, 5);
		//slider
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 1;
		gc.gridy = 10;
		gc.gridwidth=6;
		this.add(slide, gc);
		
		//liste semaine
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 1;
		gc.gridy = 11;
		gc.gridwidth=1;
		int ecart = slide.getValue();
		for(int i=0; i<5; i++) {
			this.add(creerLabelInteractifSemaine(1+i+ecart),gc);	
			gc.gridx ++;
		}
		
		//liste des annees
		gc.weightx = 10;
		gc.weighty = 0.5;
		gc.gridx = 1;
		gc.gridy = 12;
		for(int i=0; i<5; i++) {
			this.add(creerLabelInteractifAnnee(i+Temps.getAnnee()-1),gc);	
			gc.gridx ++;
		}
		

	
	}
	
	
	private void configureSlider() {
		slide.setMinimum(0);
		slide.setMaximum(48);
	    slide.setPaintTicks(false);
	    slide.setPaintLabels(false);
	    slide.setBackground(couleurFond);
	    int defaultPos = semaineSelectionner;
	    if(defaultPos <= 4) { defaultPos = 4;}
	    if(defaultPos >= 48) { defaultPos = 48;}
	    slide.setValue(defaultPos-3);
	    slide.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent event){
	        	modification();	        	
	        }
	      });  
	}
	
	
	private JLabel creerLabelInteractifSemaine(int numeroSemaine) {
		JLabel label = new JLabel(Integer.toString(numeroSemaine));
		if(semaineSelectionner == numeroSemaine) {
			label.setOpaque(true);
			label.setBackground(Color.YELLOW);
		}
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectionnerSemaine(label);
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}	
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
		});
		return label;
	}

	public void selectionnerSemaine(JLabel label) {
		semaineSelectionner = Integer.parseInt(label.getText());
		modification();
	} 

	private JLabel creerLabelInteractifAnnee(int numeroAnnee) {
		JLabel label = new JLabel(Integer.toString(numeroAnnee));
		if(anneeSelectionner == numeroAnnee) {
			label.setOpaque(true);
			label.setBackground(Color.YELLOW);
		}
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectionnerAnnee(label);
			}
			@Override
			public void mouseReleased(MouseEvent e) {}			
			@Override
			public void mousePressed(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
		});
		return label;
	}

	public void selectionnerAnnee(JLabel label) {
		anneeSelectionner = Integer.parseInt(label.getText());
		modification();
	} 
	
	private void modification() {
		this.removeAll();
		this.afficherEmploiDuTemps();
		this.revalidate();
		this.repaint();
	}

	
	private JLabel creerLabelCreneau(CreneauHoraire creneau) {
		JLabel label = new JLabel();
		if (creneau != null) {
			
			//TODO LE NOM DU PROJET EST INDEFINI POUR LE MOMENT
			/*
            String[] regex = creneau.getTitre().split("%", 2); 
			label.setText("<html>"+regex[0]+"<br>"+regex[1]+"</html>");
			*/
			
			label.setText("Activité n°"+creneau.getTitre());
			
			label.setOpaque(true);
			label.setBackground(creneau.getCouleurActivite());			
		}
		else {
			label.setText(" ");
			label.setOpaque(true);
			label.setBackground(Color.WHITE);			
		}
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return label;
	}
	
	
	
	private JPanel creerLabelInterface(String texte, boolean heure) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);

		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel(texte);
		if (heure) {
			panel.add(label, BorderLayout.NORTH);
		}
		else {
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label, BorderLayout.CENTER);
		}
		return panel ;
	}
	
	public int getIdRessource() {
		return ressource.getId();
	}
}
