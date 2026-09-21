import java.util.Iterator;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

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
            if (s.equals("går"))
                break;
        }
        // iteratoren `it` er nå avbrutt halvveis i jobben.
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

        //for (Integer i : new AlleNaturligeTall()) {
        //    System.out.println(i);
        //}

        TabellListeMedIterator<Integer> tbli = new TabellListeMedIterator<>();
        for (int i : new int[] {1, 2, 3, 4, 5, 5, 7, 0})
            tbli.leggInn(i);

        Iterator<Integer> tblit = tbli.iterator();
        while (tblit.hasNext()) {
            Integer k = tblit.next();
            if (k % 5 == 0)
                tblit.remove();
        } // Denne gjør noe galt, finn ut av.
        System.out.println(tbli);

        tbli.forEach(i -> System.out.println(i));
        tbli.forEach(System.out::println);

        Consumer<Integer> c = new Oppspiser<>();
        tbli.forEach(c);

        System.out.println(endrePåFem(i -> 2*i));
    }

    public static Integer endrePåFem(UnaryOperator<Integer> he) {
        return he.apply(5);
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
        T tmp = tabell[i];
        for (int j = i+1; j< antall; j++) {
            tabell[j-1] = tabell[j];
        }
        antall--;
        return tmp;
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
        for (int i = 0; i < tabell.length; i++) {
            if (tabell[i].equals(t)) {
                for (int j = i+1; j< antall; j++) {
                    tabell[j-1] = tabell[j];
                }
                antall--;
                return true;
            }
        }
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

        @Override
        public void remove() {
            taUt(i);
            i--;
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

class Oppspiser<T> implements Consumer<T> {

    @Override
    public void accept(T t) {
        System.out.println(t);
    }
}

@FunctionalInterface
interface HeltallsEndrer {
    public Integer endre(Integer i);
}
class LenketListeMedIterator<T> implements Liste<T>, Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private class LenketListeIterator implements Iterator<T> {
        Node denne = hode;
        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            T tmp = denne.verdi;
            denne = denne.neste;
            return tmp;
        }
    }

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

    public LenketListeMedIterator() {
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
