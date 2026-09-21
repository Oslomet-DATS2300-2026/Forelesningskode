import java.util.Iterator;
import java.util.StringJoiner;

public class Dato0921IterasjonOgFunksjonellInterface {
    public static void main(String[] args) {
        int[] tabell = {2, 3, 7, 1, 4, 5, 8};

        for (int j : tabell) {
            System.out.println(j);
        }

        String[] strengTabell = {"Hei", "Hallo", "God dag"};

        for (String s : strengTabell) {
            System.out.println(s);
        }

        System.out.println("Her starter Vår Iterator:");

        Iterator<String> it = new VårIterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println(s);
        }

        TabellListeMedIterator<String> tbl = new TabellListeMedIterator<>();
        tbl.leggInn("Hei");
        tbl.leggInn("Hallo");
        tbl.leggInn("Hvordan går det med deg?");

        System.out.println("Her starter TabellListeIterator:");
        for (String s : tbl)
            System.out.println(s);

        for (Integer i : new AlleNaturligeTall()) {
            System.out.println(i);
        }
    }
}

class VårIterator implements Iterator<String> {
    int i = 0;
    String[] sTabell = {"Hallo", "hvordan", "går", "det", "?"};

    public VårIterator() {}

    @Override
    public boolean hasNext() {
        return i < sTabell.length;
    }

    @Override
    public String next() {
        return sTabell[i++];
    }
}

class TabellListeMedIterator<T> implements Liste<T>, Iterable<T> {

    private T[] tabell;
    private int antall;
    private int kapasitet;

    public TabellListeMedIterator() {
        this(32);
    }

    @SuppressWarnings("unchecked")
    public TabellListeMedIterator(int kapasitet) {
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

    @Override
    public Iterator<T> iterator() {
        return new TabellListeIterator();
    }

    private class TabellListeIterator implements Iterator<T> {
        int i = 0;
        @Override
        public boolean hasNext() {
            return i < antall;
        }

        @Override
        public T next() {
            return tabell[i++];
        }
    }
}

class AlleNaturligeTall implements Iterator<Integer>, Iterable<Integer> {
    int i = 0;
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        return i++;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
}