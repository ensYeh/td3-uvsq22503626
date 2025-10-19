package fr.uvsq.cprog.collex;

/** Commande exécutable sur un Dns, qui retourne un texte à afficher. */
@FunctionalInterface
public interface Commande {
  String execute(Dns dns) throws Exception;
}
