import java.util.*;

public class Dato1117FaktiskEksamen {
    public static void main(String[] args) {
        int[] tabell = {8, 7, 1, 0, 3, 2, 4, 6, 5};
        SkipList<Integer> hl = new SkipList<>(Comparator.naturalOrder());
        for (int i : tabell)
            hl.leggInn(i);
        System.out.println(hl); // printer verdiene sortert

        sorter(tabell, 0, tabell.length);
        System.out.println(Arrays.toString(tabell)); // printer sortert tabell
    }


    private static void flett(int[] a, int fra, int midt, int til) {
        // Vi vil flette alle verdier fra og med `fra` til `midt` med
        // alle verdier fra og med `midt` til `til`. Jeg lager en ny
        // tabell å lagre verdiene i midlertidig.
        int[] sortert = new int[til - fra];
        int i = fra, j = midt, k = 0;
        while (i < midt && j < til) {
            if (a[i] < a[j])
                sortert[k++] = a[i++];
            else
                sortert[k++] = a[j++];
        }
        while (i < midt) sortert[k++] = a[i++];
        while (j < til) sortert[k++] = a[j++];
        // Når vi har flettet, legg alle verdiene tilbake inn i tabellen
        // Går inn fra og med `fra` til `til`
        for (int l = 0; l < til - fra; l++)
            a[fra+l] = sortert[l];
    }

    public static void sorter(int[] a, int fra, int til) {
        if (til - fra <= 1)
            return;
        int midt = (fra + til) / 2;
        sorter(a, fra, midt);
        sorter(a, midt, til);
        flett(a, fra, midt, til);
    }
}

class SkipList<T> {
    private class Node {
        Node neste, ned;
        T verdi;
        public Node(T verdi, Node neste, Node ned) {
            this.verdi = verdi; this.neste = neste; this.ned = ned;
        }
    }
    Node hode;
    Comparator<? super T> cmp;
    public SkipList(Comparator<? super T> cmp) {
        this.cmp = cmp; hode = new Node(null, null, null); // Lager tom hodekolonne
    }
    private boolean coinFlip() { // 50/50 sjanse for true/false.
        return (new Random()).nextBoolean();
    }
    public boolean leggInn(T verdi) {
        Stack<Node> s = finn(verdi); // Her brukes finn-metoden.
        Node denne;
        Node ned = null; // ned er noden vi har lagt til lenger ned i systemet
        while (!s.isEmpty()) {
            denne = s.pop();
            denne.neste = new Node(verdi, denne.neste, ned);
            ned = denne.neste;
            if (coinFlip()) // Flipp mynt for å se om vi vil videre opp.
                break;
        }
        if (s.isEmpty() && coinFlip()) {
            // Flipp mynten en siste gang for å se om vi lager nytt nivå.
            Node nyttNivå = new Node(verdi, null, ned);
            hode = new Node(null, nyttNivå, hode);
        }
        return true;
    }
    private Stack<Node> finn(T verdi) {
        Stack<Node> stabel = new Stack<>();
        Node denne = hode;
        while (denne != null) {
            if (denne.neste != null && cmp.compare(denne.neste.verdi, verdi) < 0) {
                // Fremdeles ikke gått forbi verdien vi leter etter. Gå videre.
                // Vi må sjekke at denne.neste != null for å ikke få nullptrexception.
                denne = denne.neste;
            } else {
                // Neste steg er for langt. Lagre denne, gå ned.
                stabel.push(denne);
                denne = denne.ned;
            }
        }
        // Avslutter når vi går ut av bunnen.
        return stabel;
    }

    public String toString() {
        // Legger til en toString-metode for å se at vi får en sortert liste.
        Node denne = hode;
        while (denne.ned != null) // Gå ned til nederste nivå
            denne = denne.ned;
        denne = denne.neste; // gå forbi det tomme "hode"-elementet.
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        while (denne != null) {
            sj.add(denne.verdi.toString());
            denne = denne.neste;
        }
        return sj.toString();
    }
}