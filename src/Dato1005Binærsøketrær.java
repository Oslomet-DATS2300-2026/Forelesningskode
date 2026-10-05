import java.util.Comparator;

public class Dato1005Binærsøketrær {
    public static void main(String[] args) {
        BinærSøkeTre<String> bst = BinærSøkeTre.bst();
        bst.leggInn("Hei");
        bst.leggInn("Hvordan går det");
        bst.leggInnRec("ABCDEFG");
        bst.leggInnRec("Senere i alfabetet");

        System.out.println(bst.finn("ABCDEFG"));
        System.out.println(bst.finnRec("ABCDEFG"));
        System.out.println(bst.finn("Hallo"));
        System.out.println(bst.finnRec("Hallo"));
    }
}

class BinærSøkeTre<T> {
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

    public boolean finn(T verdi) {
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

    public boolean finnRec(T verdi) {
        // I snitt O(log n)
        return finnRec(verdi, rot);
    }

    private boolean finnRec(T verdi, Node denne) {
        if (denne == null)
            return false;
        int cmpVal = sammenlikner.compare(verdi, denne.verdi);
        if (cmpVal < 0)
            return finnRec(verdi, denne.venstre);
        else if (cmpVal > 0)
            return finnRec(verdi, denne.høyre);
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
}