package fr.uvsq.cprog.collex;

public final class CmdFindIp implements Commande {
    private final NomMachine nom;

    public CmdFindIp(NomMachine nom) {
        this.nom = nom;
    }

    @Override
    public String execute(Dns dns) {
        DnsItem item = dns.getItem(nom);   // peut renvoyer null si inconnu
        return (item != null) ? item.getIP().toString() : "Inconnu";
    }
}
