package fr.uvsq.cprog.collex;
public class DnsItem {
    private final NomMachine nom;
    private final AdresseIP IP;

    public DnsItem(NomMachine nom, AdresseIP IP) {
        if (nom == null || IP == null) {
            throw new IllegalArgumentException("Nom de machine ou adresse IP ne peut pas être null");
        }
        this.nom = nom;
        this.IP = IP;
    }

    public NomMachine getNom() {
        return nom;
    }

    public AdresseIP getIP() {
        return IP;
    }

    @Override
    public String toString() {
        return nom.toString() + " -> " + IP.toString();
    }
}
