package fr.uvsq.cprog.collex;

public final class CmdFindName implements Commande {
    private final AdresseIP ip;

    public CmdFindName(AdresseIP ip) {
        this.ip = ip;
    }

    @Override
    public String execute(Dns dns) {
        DnsItem item = dns.getItem(ip);
        return (item != null) ? item.getNom().toString() : "Inconnu";
    }
}
