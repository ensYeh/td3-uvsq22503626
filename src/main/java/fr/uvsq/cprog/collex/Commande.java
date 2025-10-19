package fr.uvsq.cprog.collex;

/** Contrat d'une commande DNS : exécuter et renvoyer le texte à afficher. */
public interface Commande {
    String QUIT_SENTINEL = "__QUIT__";
    String execute(Dns dns);
}
