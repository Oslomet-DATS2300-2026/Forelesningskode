import java.util.StringJoiner;

public class Dato1106EksamenFjernDuplikat {
    public static void main(String[] args) {
        int[] liste1 = {3, 2, 2, 2, 2, 5};
        LenketListeEksamen2025<Integer> lle1 = new LenketListeEksamen2025<>();
        for (int i : liste1)
            lle1.leggInn(i);
        System.out.println(lle1);
        lle1.fjernDuplikater();
        System.out.println(lle1);
        int[] liste2 = {3, 2, 2, 3, 5};
        LenketListeEksamen2025<Integer> lle2 = new LenketListeEksamen2025<>();
        for (int i : liste2)
            lle2.leggInn(i);
        System.out.println(lle2);
        lle2.fjernDuplikater();
        System.out.println(lle2);
    }
}
class LenketListeEksamen2025<T> {
    class Node {
        Node neste; T verdi;
        public Node(T verdi) {
            this.verdi = verdi; neste = null;
        }
    }
    Node hode, hale;
    public boolean leggInn(T verdi) {
        if (hode == null)
            hode = hale = new Node(verdi);
        else {
            hale.neste = new Node(verdi);
            hale = hale.neste;
        }
        return true;
    }
    public boolean inneholder(T verdi) {
        Node denne = hode;
        while (denne != null) {
            if (denne.verdi.equals(verdi))
                return true;
            denne = denne.neste;
        }
        return false;
    }
    private void fjernAlleVerdier(Node denne, T verdi) {
        // Fjerne alle noder med verdi T som kommer _etter_ denne.
        if (denne.neste == null)
            return;
        if (denne.neste.verdi.equals(verdi)) {
            denne.neste = denne.neste.neste;
            if (denne.neste == null) {
                hale = denne; // Om vi sletta halen, sett halen til denne noden.
            }
            // Jeg glemte denne i forelesninga, inni hodet mitt var vi i en while-løkke.
            fjernAlleVerdier(denne, verdi);
            // Uten denne ville vi avbrutt etter å ha funnet én lik verdi.
        } else {
            fjernAlleVerdier(denne.neste, verdi); // Gå rekursivt gjennom for å slette alle verdiene, ved å gå videre.
        }

    }

    public void fjernDuplikater() {
        Node denne = hode;
        while (denne != null) {
            fjernAlleVerdier(denne, denne.verdi);
            // Sletter alle verdier som er like dennes verdi, vi har da ingen flere slike i lista.
            denne = denne.neste;
        }
    }

    // Lager en toString så vi kan se at det funker (var ikke i eksamensoppgaven)
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        Node denne = hode;
        while (denne != null) {
            sj.add(denne.verdi.toString());
            denne = denne.neste;
        }
        return sj.toString();
    }
}