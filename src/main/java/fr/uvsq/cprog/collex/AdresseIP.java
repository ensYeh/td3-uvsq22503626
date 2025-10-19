package fr.uvsq.cprog.collex;

public class AdresseIP {
  private final String valeur;

  public AdresseIP(String ip) {
    if (ip == null || !ip.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
      throw new IllegalArgumentException("Adresse IP invalide");
    }
    this.valeur = ip;
  }

  public String getValeur() {
    return valeur;
  }

  @Override
  public String toString() {
    return valeur;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof AdresseIP)) return false;
    AdresseIP other = (AdresseIP) obj;
    return valeur.equals(other.valeur);
  }

  @Override
  public int hashCode() {
    return valeur.hashCode();
  }
}
