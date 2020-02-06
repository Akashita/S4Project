//histoire de faire mes tests => j'aime faire des tests @jules 
public class Main {

	static Entreprise test = new Entreprise();

		public static void main (String[] args) {
			 test.creerProjet("test, le projet");
			 test.nouvPersonne("test, le projet", "geyer", "jules", Personne.COLLABORATEUR);
			 test.nouvPersonne("test, le projet", "Planchamp", "Damien", Personne.ADMINISTRATEUR);

			 System.out.println(test.toString());

			}

		
}
