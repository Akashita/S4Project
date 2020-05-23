package Model;

import java.awt.Color;
import java.time.LocalDate;

import Panel.PanelPrincipal;

public class CreneauHoraire {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public static final int CONGE = 0, REUNION = 1, TRAVAIL = 2;
	
	private String titre;
	private int debut;
	private int position;
	private Color couleur;
	private Activite activite;
	private int type;
	private LocalDate date;
	private int id;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	public CreneauHoraire(Activite activite, int debut, LocalDate date, int type, String titre, Color couleur, int id) { //Par d�fault un cr�neau horaire est de type "TRAVAIL"
		this.activite = activite;
		this.debut = debut;
		this.type = type;
		this.titre = titre;
		this.couleur = couleur;
		this.date = date;
		this.id = id;

		//On cherche la position du creneau dans une journee (les creneaux sont tous les uns apres mes autres)
		int i = Entreprise.HEURE_DEBUT_MATIN;
		int pos = 0;
		boolean posTrouve = false;
		
		while(i != Entreprise.HEURE_FIN_APREM && !posTrouve) {
			if (i == Entreprise.HEURE_FIN_MATIN) {
				i = Entreprise.HEURE_DEBUT_APREM;
			} 
			if (i == debut) {
				this.position = pos;
				posTrouve = true;
			}
			pos++;
			i++;
		}	
	}
	
	public CreneauHoraire(String titre, Activite activite, int debut, Color couleur) {
		this(activite, debut, null, TRAVAIL, titre, couleur, 0);
	}
	
	public CreneauHoraire(Activite activite, int debut, LocalDate date, int type, String titre, int id) {
		this(activite, debut, date, -1, titre, null, id);
		if(type == CONGE) {
			this.titre = "Conge";
			this.couleur = Color.RED;
		} else if(type == REUNION) {
			this.titre = "Reunion";
			this.couleur = Color.ORANGE;
		}
	}
	
	/**
	 * Genere un conge pour une personne
	 * @param date du conge
	 */
	public CreneauHoraire(LocalDate date) {
		this(null, 8, date, CONGE, "CONGE", PanelPrincipal.CONGE, 0);
	}

	/**
	 * Genere un conge pour une personne via la bdd
	 * @param date du conge
	 */
	public CreneauHoraire(LocalDate date, int id) {
		this(null, 8, date, CONGE, "CONGE", PanelPrincipal.CONGE, id);
	}

	/**
	 * Genere une reunion pour une personne
	 * @param date de la reunion
	 */
	public CreneauHoraire(LocalDate date, int debut, String titre) {
		this(null, debut, date, REUNION, titre, PanelPrincipal.REUNION, 0);
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public Activite getActivite() {
		return activite;
	}
	
	public int getPosition() {
		return position;
	}

	public String getTitre() {
		return titre;
	}
	
	public int getDebut() {
		return debut;
	}
	
	public int getType() {
		return type;
	}
	
	public LocalDate getDate() {
		return date;
	}
	

	public Color getCouleur() {
		return couleur;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CreneauHoraire && obj != null) {
			CreneauHoraire res = (CreneauHoraire)obj;
			boolean ret = false;
			switch (res.getType()) {
			case CONGE:
				ret = (date.equals(res.getDate()));
				break;
			case REUNION:
				ret = (debut == res.getDebut()) && (date.equals(res.getDate()));
				break;
			case TRAVAIL:
				ret = debut == res.getDebut();
				break;

			default:
				break;
			}
			return ret;
		} else {
			return false;
		}
	}
	
	//--------------------------------------------------------------------------------->>>>> toString
	@Override 
	public String toString() {
		return Temps.dateToString(date);
	}

}
