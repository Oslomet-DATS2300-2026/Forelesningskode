import java.util.Comparator;
import java.util.StringJoiner;

public class Dato1005Binærsøketrær {
    public static void main(String[] args) {
        BinærSøkeTre<String> bst = BinærSøkeTre.bst();
        bst.leggInn("Hei");
        bst.leggInn("Hvordan går det");
        bst.leggInnRec("ABCDEFG");
        bst.leggInnRec("ABCDEFG");
        bst.leggInnRec("Senere i alfabetet");

        System.out.println(bst.inneholder("ABCDEFG"));
        System.out.println(bst.inneholderRec("ABCDEFG"));
        System.out.println(bst.inneholder("Hallo"));
        System.out.println(bst.inneholderRec("Hallo"));

        System.out.println(bst);

        System.out.println("Skal gi: true - true");
        System.out.println(bst.taUt("ABCDEFG"));
        System.out.println(bst.inneholder("ABCDEFG"));

        System.out.println(bst);
    }
}

class BinærSøkeTre<T> implements Beholder<T>{
    Comparator<? super T> sammenlikner;
    Node rot;

    public BinærSøkeTre(Comparator<? super T> sammenlikner) {
        this.sammenlikner = sammenlikner;
        rot = null;
    }

    public static <T> BinærSøkeTre<T> bst(Comparator<? super T> c) {
        return new BinærSøkeTre<>(c);
    }

    public static <T extends Comparable<? super T>> BinærSøkeTre<T> bst() {
        return new BinærSøkeTre<>(Comparator.naturalOrder());
    }

    private class Node {
        Node venstre, høyre;
        T verdi;
        public Node(T verdi) {
            this.verdi = verdi;
        }
    }

    public boolean leggInn(T verdi) {
        if (rot == null) {
            rot = new Node(verdi);
            return true;
        }

        Node forrige = null;
        Node denne = rot;
        int cmpVal = 0;
        while (denne != null) {
            cmpVal = sammenlikner.compare(verdi, denne.verdi);
            forrige = denne;
            if (cmpVal < 0) {
                denne = denne.venstre;
            } else {
                denne = denne.høyre;
            }
        }
        if (cmpVal < 0)
            forrige.venstre = new Node(verdi);
        else
            forrige.høyre = new Node(verdi);
        return true;
    }

    public boolean leggInnRec(T verdi) {
        rot = leggInnRec(verdi, rot);
        return true;
    }

    private Node leggInnRec(T verdi, Node denne) {
        if (denne == null)
            return new Node(verdi);
        int cmpVal = sammenlikner.compare(verdi, denne.verdi);
        if (cmpVal < 0)
            denne.venstre = leggInnRec(verdi, denne.venstre);
        else
            denne.høyre = leggInnRec(verdi, denne.høyre);
        return denne;
    }

    public boolean inneholder(T verdi) {
        Node denne = rot;
        while (denne != null) {
            int cmpVal = sammenlikner.compare(verdi, denne.verdi);
            if (cmpVal < 0)
                denne = denne.venstre;
            else if (cmpVal > 0)
                denne = denne.høyre;
            else
                return true;
        }
        return false;
    }

    public boolean inneholderRec(T verdi) {
        // I snitt O(log n)
        return inneholderRec(verdi, rot);
    }

    private boolean inneholderRec(T verdi, Node denne) {
        if (denne == null)
            return false;
        int cmpVal = sammenlikner.compare(verdi, denne.verdi);
        if (cmpVal < 0)
            return inneholderRec(verdi, denne.venstre);
        else if (cmpVal > 0)
            return inneholderRec(verdi, denne.høyre);
        else
            return true;
    }

    public int antall() {
        // Er O(n)
        return antall(rot);
    }

    private int antall(Node denne) {
        if (denne == null)
            return 0;
        return 1 + antall(denne.venstre) + antall(denne.høyre);
    }

    public boolean erTom() {
        return rot == null;
    }

    @Override
    public boolean taUt(T t) {
        Node forelder = null;
        Node denne = rot;
        while (denne != null) {
            int cmpVal = sammenlikner.compare(t, denne.verdi);
            if (cmpVal < 0) {
                forelder = denne;
                denne = denne.venstre;
            } else if (cmpVal > 0) {
                forelder = denne;
                denne = denne.høyre;
            } else {
                slettNode(denne, forelder);
                return true;
            }
        }
        return false;
    }

    public int taUtAlle(T t) {
        Stabel<Node> denneStabel = new LLStabelOgKø<>();
        Stabel<Node> foreldreStabel = new LLStabelOgKø<>();
        Node forelder = null;
        Node denne = rot;
        while (denne != null) {
            int cmpVerdi = sammenlikner.compare(t, denne.verdi);
            if (cmpVerdi < 0) {
                forelder = denne;
                denne = denne.venstre;
            } else if (cmpVerdi > 0) {
                forelder = denne;
                denne = denne.høyre;
            } else {
                denneStabel.push(denne);
                foreldreStabel.push(forelder);
                forelder = denne;
                denne = denne.høyre;
            }
        }
        int antallSlettes = 0;
        while (!denneStabel.isEmpty()) {
            denne = denneStabel.pop();
            forelder = foreldreStabel.pop();
            slettNode(denne, forelder);
            antallSlettes++;
        }
        return antallSlettes;
    }

    private void oppdaterNode(Node denne, Node forelder, Node ny) {
        if (forelder == null)
            rot = ny;
        else if (denne == forelder.venstre)
            forelder.venstre = ny;
        else if (denne == forelder.høyre)
            forelder.høyre = ny;
        else
            throw new IllegalArgumentException("Noden 'denne' må være ett av barna til 'forelder'");
    }

    private void slettNode(Node denne, Node forelder) {
        if (denne.venstre == null) {
            // har ett høyrebarn eller ingen barn
            oppdaterNode(denne, forelder, denne.høyre);
        } else if (denne.høyre == null) {
            // har ett venstrebarn
            oppdaterNode(denne, forelder, denne.venstre);
        } else {
            // har to barn
            Node p = denne.høyre;
            Node q = denne;
            while (p.venstre != null) {
                q = p;
                p = p.venstre;
            }
            denne.verdi = p.verdi;
            oppdaterNode(p, q, p.høyre);
        }
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        toStringInOrden(sj, rot);
        return sj.toString();
    }

    private void toStringInOrden(StringJoiner sj, Node denne) {
        if (denne == null) return;
        toStringInOrden(sj, denne.venstre);
        sj.add(denne.verdi.toString());
        toStringInOrden(sj, denne.høyre);
    }
}