package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe Dns : gère une base locale de noms et adresses IP.
 */
public class Dns {
  private final Map<String, DnsItem> byIp = new HashMap<>();
  private final Map<String, DnsItem> byName = new HashMap<>();
  private final Path fichierBase;

  public Dns(String fichier) throws IOException {
    this.fichierBase = Path.of(fichier);
    chargerBase();
  }

  public DnsItem getItem(AdresseIP ip) {
    return byIp.get(ip.getValeur());
  }

  public DnsItem getItem(NomMachine nom) {
    return byName.get(nom.toString());
  }

  public List<DnsItem> getItems(String domaine) {
    List<DnsItem> liste = new ArrayList<>();
    for (DnsItem item : byName.values()) {
      if (item.getNom().getDomaine().equals(domaine)) {
        liste.add(item);
      }
    }
    return liste;
  }

  public void addItem(AdresseIP ip, NomMachine nom) throws IOException {
    if (byIp.containsKey(ip.getValeur()) || byName.containsKey(nom.toString())) {
      throw new IllegalArgumentException("ERREUR : IP ou nom déjà existant !");
    }
    DnsItem item = new DnsItem(nom, ip);
    byIp.put(ip.getValeur(), item);
    byName.put(nom.toString(), item);
    sauvegarderBase();
  }

  private void chargerBase() throws IOException {
    if (!Files.exists(fichierBase)) return;
    for (String ligne : Files.readAllLines(fichierBase, StandardCharsets.UTF_8)) {
      if (ligne.isBlank()) continue;
      String[] parts = ligne.trim().split("\\s+");
      if (parts.length == 2) {
        AdresseIP ip = new AdresseIP(parts[0]);
        NomMachine nom = new NomMachine(parts[1]);
        DnsItem item = new DnsItem(nom, ip);
        byIp.put(ip.getValeur(), item);
        byName.put(nom.toString(), item);
      }
    }
  }

  private void sauvegarderBase() throws IOException {
    List<String> lignes = new ArrayList<>();
    for (DnsItem item : byName.values()) {
      lignes.add(item.getIP().getValeur() + " " + item.getNom());
    }
    Files.write(fichierBase, lignes, StandardCharsets.UTF_8);
  }
}
