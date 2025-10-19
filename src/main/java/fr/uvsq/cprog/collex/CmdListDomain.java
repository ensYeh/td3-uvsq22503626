package fr.uvsq.cprog.collex;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class CmdListDomain implements Commande {
    private final String domaine;
    private final boolean sortByIp;

    public CmdListDomain(String domaine, boolean sortByIp) {
        this.domaine = domaine;
        this.sortByIp = sortByIp;
    }

    @Override
    public String execute(Dns dns) {
        List<DnsItem> items = dns.getItems(domaine);

        Comparator<DnsItem> cmp = sortByIp
                ? Comparator.comparing(it -> it.getIP().getValeur())
                : Comparator.comparing(it -> it.getNom().getNomMachine());

        return items.stream()
                .sorted(cmp)
                .map(it -> it.getIP() + " " + it.getNom())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
