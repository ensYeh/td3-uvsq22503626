package fr.uvsq.cprog.collex;

import java.io.IOException;

public final class CmdAddItem implements Commande {
    private final AdresseIP ip;
    private final NomMachine nom;

    public CmdAddItem(AdresseIP ip, NomMachine nom) {
        this.ip = ip;
        this.nom = nom;
    }

    @Override
    public String execute(Dns dns) {
        try {
            dns.addItem(ip, nom);
            return "Ajout réussi : " + ip + " " + nom;
        } catch (IllegalArgumentException e) {
            return "ERREUR : " + e.getMessage();
        } catch (IOException e) {
            return "ERREUR IO : " + e.getMessage();
        }
    }
}
