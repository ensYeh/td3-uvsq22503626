package fr.uvsq.cprog.collex;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DnsTUI {

  private final Scanner in;
  private final PrintStream out;

  public DnsTUI() { this(System.in, System.out); }
  public DnsTUI(InputStream input, PrintStream output) {
    this.in = new Scanner(input);
    this.out = output;
  }

  public Commande nextCommande() {
    if (!in.hasNextLine()) return null;
    return nextCommande(in.nextLine());
  }

  public Commande nextCommande(String ligne) {
    if (ligne == null || (ligne = ligne.trim()).isEmpty()) return null;
  
    String lower = ligne.toLowerCase(Locale.ROOT);
    if (lower.equals("quit") || lower.equals("exit")) return dns -> "__QUIT__";

    String[] tok = ligne.split("\\s+");
    String cmd = tok[0].toLowerCase(Locale.ROOT);

    switch (cmd) {
      case "add":
        return buildAdd(tok);        // add <ip> <nom>
      case "ls":
        return buildLs(tok);         // ls [-a] <domaine>
      default:
        // ni add ni ls => soit IP => nom, soit nom => IP
        return isIp(ligne) ? byIp(ligne) : byName(ligne);
    }
  }

  public void affiche(String texte) {
    if (texte != null && !texte.isBlank()) out.println(texte);
  }

  private Commande buildAdd(String[] tok) {
    if (tok.length != 3) return dns -> "ERREUR : usage = add <ip> <nom.qualifie>";
    String ip = tok[1], nom = tok[2];
    return dns -> {
      try {
        dns.addItem(new AdresseIP(ip), new NomMachine(nom));
        return "";
      } catch (IllegalArgumentException e) {
        return "ERREUR : " + e.getMessage();
      }
    };
  }

  private Commande buildLs(String[] tok) {
    boolean sortByIp = false;
    String domaine;

    if (tok.length == 2) {
      domaine = tok[1];
    } else if (tok.length == 3 && tok[1].equals("-a")) {
      sortByIp = true;
      domaine = tok[2];
    } else {
      return dns -> "ERREUR : usage = ls [-a] <domaine>";
    }

    final boolean byIp = sortByIp;
    final String dom = domaine;
    return dns -> {
      List<DnsItem> items = dns.getItems(dom);
      items.sort((a, b) -> byIp
          ? a.getIP().getValeur().compareTo(b.getIP().getValeur())
          : a.getNom().toString().compareTo(b.getNom().toString()));
      return formatItems(items);
    };
  }

  private Commande byIp(String ipTxt) {
    return dns -> {
      DnsItem it = dns.getItem(new AdresseIP(ipTxt));
      return it == null ? "Non trouvé" : it.getNom().toString();
    };
  }

  private Commande byName(String nomTxt) {
    return dns -> {
      DnsItem it = dns.getItem(new NomMachine(nomTxt));
      return it == null ? "Non trouvé" : it.getIP().getValeur();
    };
  }

  private static String formatItems(List<DnsItem> items) {
    StringBuilder sb = new StringBuilder();
    for (DnsItem it : items) {
      sb.append(it.getIP().getValeur()).append(' ')
        .append(it.getNom().toString()).append('\n');
    }
    return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
  }

  private static final Pattern IPV4 = Pattern.compile("^(\\d{1,3}\\.){3}\\d{1,3}$");
  private static boolean isIp(String s) { return IPV4.matcher(s).matches(); }
}
