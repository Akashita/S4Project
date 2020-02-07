//histoire de faire mes tests => j'aime faire des tests @jules 
public class Main {

	static Entreprise test = new Entreprise();

		public static void main (String[] args) {
			 test.creerProjet("test, le projet");
			 test.nouvPersonne("test, le projet", "geyer", "jules", Personne.COLLABORATEUR);
			 test.nouvPersonne("test, le projet", "Planchamp", "Damien", Personne.ADMINISTRATEUR);
			 
			 /* Test methode equals de Ressource
			 Personne p1 = new Personne("azert", "yolo", "chef", 1555);
			 Personne p2 = new Personne("az", "yo", "ch", 1555);
			 System.out.println(p1.equals(p2));
			 */

			 System.out.println(test.toString());

			}

		
}
