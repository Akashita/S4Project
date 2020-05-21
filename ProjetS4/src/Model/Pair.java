package Model;

public class Pair<L,R> {
	
	/* Classe qui permet de gérer les duets
	 * 
	 * */

  private final L type;
  private final R id;

  public Pair(L left, R right) {
    assert left != null;
    assert right != null;

    this.type = left;
    this.id = right;
  }

  public L getLeft() { return type; }
  public R getRight() { return id; }

  @Override
  public int hashCode() { return type.hashCode() ^ id.hashCode(); }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    Pair pairo = (Pair) o;
    return this.type.equals(pairo.getLeft()) && this.id.equals(pairo.getRight());
  }

}