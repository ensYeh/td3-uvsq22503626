package fr.uvsq.cprog.collex;

public final class CmdQuit implements Commande {
    @Override
    public String execute(Dns dns) {
        return Commande.QUIT_SENTINEL;
    }
}
