package fr.uvsq.cprog.collex;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class DnsApp {

  /** Lit src/main/resources/dns.properties et renvoie la valeur de "fichier.dns". */
  private static String lireCheminBase() throws IOException {
    Properties p = new Properties();
    try (FileInputStream fis = new FileInputStream("src/main/resources/dns.properties")) {
      p.load(fis);
    }
    String chemin = p.getProperty("fichier.dns");
    if (chemin == null || chemin.isBlank()) {
      throw new IOException("Propriété 'fichier.dns' absente dans dns.properties");
    }
    return chemin;
  }

  /** Boucle REPL : lit, parse, exécute, affiche, jusqu'à quit/exit. */
  public void run() throws Exception {
    String chemin = lireCheminBase();
    Dns dns = new Dns(chemin);
    DnsTUI tui = new DnsTUI();

    while (true) {
      System.out.print("> ");
      Commande cmd = tui.nextCommande();     
      if (cmd == null) continue;            

      String res = cmd.execute(dns);         
      if ("__QUIT__".equals(res)) break;     
      tui.affiche(res);                      
    }
  }

  public static void main(String[] args) throws Exception {
    new DnsApp().run();
  }
}
