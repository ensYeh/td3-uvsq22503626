package fr.uvsq.cprog.collex;

public class NomMachine {
  private final String nomComplet;

  public NomMachine(String nomComplet) {
    if (nomComplet == null || !nomComplet.contains(".")) {
      throw new IllegalArgumentException("Nom de machine invalide");
    }
    this.nomComplet = nomComplet;
  }

  public String getNomMachine() {
    return nomComplet.substring(0, nomComplet.indexOf('.'));
  }

  public String getDomaine() {
    return nomComplet.substring(nomComplet.indexOf('.') + 1);
  }

  @Override
  public String toString() {
    return nomComplet;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof NomMachine)) return false;
    NomMachine other = (NomMachine) obj;
    return nomComplet.equals(other.nomComplet);
  }

  @Override
  public int hashCode() {
    return nomComplet.hashCode();
  }
}
