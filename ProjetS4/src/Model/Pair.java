package Model;


public class Pair<L,R> {
	//Classe g�n�rique, le type des variables pass�e en param�tre est � indiquer.

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private final L left;
	private final R right;

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public Pair(L left, R right) {
		assert left != null;
		assert right != null;

		this.left = left;
		this.right = right;
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public L getLeft() {
		return left;
	}


	public R getRight() {
		return right;
	}

	//--------------------------------------------------------------------------------->>>>> Override
	@Override
	public int hashCode() {
		return left.hashCode() ^ right.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) return false;
		Pair<?, ?> pairo = (Pair<?, ?>) o;
		return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
	}

}
