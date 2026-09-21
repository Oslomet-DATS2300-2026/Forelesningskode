import java.util.StringJoiner;

public class Dato0918BeholdereOgLister {
    public static void main(String[] args) {
        TabellListe<String> tls = new TabellListe<>();
        tls.leggInn("Hei");
        tls.leggInn("Hallo");
        tls.leggInn("Hvordan går det?");
        System.out.println(tls.antall());
        System.out.println(tls);
    }
}

interface Beholder<T> {
    public boolean leggInn(T t);
    // Gir ut true om vi klarte legge inn, false ellers
    public boolean inneholder(T t);
    public boolean taUt(T t);
    public boolean erTom();
    public int antall();
}

interface Liste<T> extends Beholder<T> {
   public boolean leggInn(int i, T t);
   public T taUt(int i);
   public T oppdater(int i, T t);
   public int finn(T t);
}

class TabellListe<T> implements Liste<T> {

    private T[] tabell;
    private int antall;
    private int kapasitet;

    public TabellListe() {
        this(32);
    }

    @SuppressWarnings("unchecked")
    public TabellListe(int kapasitet) {
        if (kapasitet < 0) throw new IllegalArgumentException("Kapasiteten må være minst 0");
        antall = 0;
        this.kapasitet = kapasitet;
        tabell = (T[]) new Object[kapasitet];
    }

    @SuppressWarnings("unchecked")
    private void sjekkPlass() {
        if (antall == kapasitet) {
            T[] nyTabell = (T[]) new Object[2*kapasitet];
            System.arraycopy(tabell, 0, nyTabell, 0, kapasitet);
            tabell = nyTabell;
            kapasitet = 2*kapasitet;
        }
    }

    @Override
    public boolean leggInn(int i, T t) {
        sjekkPlass();
        for (int j = antall; j > i; j--) {
            tabell[j] = tabell[j-1];
        }
        tabell[i] = t;
        antall++;
        return true;
    }

    @Override
    public boolean leggInn(T t) {
        sjekkPlass();
        tabell[antall++] = t;
        return true;
    }

    @Override
    public boolean erTom() {
        return antall == 0;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public T taUt(int i) {
        return null;
    }

    @Override
    public T oppdater(int i, T t) {
        return null;
    }
    @Override
    public boolean inneholder(T t) {
        return false;
    }

    @Override
    public boolean taUt(T t) {
        return false;
    }


    @Override
    public int finn(T t) {
        return 0;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < antall; i++) {
            sj.add(tabell[i].toString());
        }
        return sj.toString();
    }
}

class LenketListe<T> implements Liste<T> {

    private class Node {
        T verdi;
        Node neste;
        public Node(T verdi) {
            this(verdi, null);
        }
        public Node(T verdi, Node neste) {
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    Node hode;

    public LenketListe() {
        hode = null;
    }

    @Override
    public boolean leggInn(T t) {
        hode = new Node(t, hode);
        return true;
    }
    @Override
    public boolean leggInn(int i, T t) {
        if (i == 0) {
            leggInn(t);
        } else {
            leggInn(i - 1, hode, t);
        }
        return true;
    }

    private void leggInn(int i, Node n, T t) {
        if (i == 0) {
            n.neste = new Node(t, n.neste);
        } else {
            leggInn(i-1, n.neste, t);
        }

    }
    @Override
    public boolean inneholder(T t) {
        return false;
    }

    @Override
    public boolean taUt(T t) {
        return false;
    }

    @Override
    public boolean erTom() {
        return false;
    }

    @Override
    public int antall() {
        return 0;
    }


    @Override
    public T taUt(int i) {
        return null;
    }

    @Override
    public T oppdater(int i, T t) {
        return null;
    }

    @Override
    public int finn(T t) {
        return 0;
    }
}