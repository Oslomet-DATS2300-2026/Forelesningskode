import java.util.ArrayList;

public class Dato1002BinæreTrær {
    public static void main(String[] args) {
        BinærtreNoder<Integer> bt = new BinærtreNoder<>();
        bt.leggInn(7, 1);
        bt.leggInn(10, 3);
        bt.leggInn(-2, 6);
        bt.leggInn(15, 13);
        bt.leggInn(5, 2);

        // System.out.println(bt.finn(13));

        bt.printInorden();
    }
}

class BinærtreNoder<T> {
    private class Node {
        T verdi;
        Node venstre = null, høyre = null;
        public Node(T verdi) {
            this.verdi = verdi;
        }
    }
    Node rot = null;

    public boolean leggInn(T t, int posisjon) {
        if (rot == null) {
            if (posisjon == 1) {
                rot = new Node(t);
                return true;
            } else {
                return false;
            }
        }

        String venstrehøyre = Integer.toBinaryString(posisjon);
        char[] vhtabell = venstrehøyre.toCharArray();
        Node denne = rot;
        for (int i = 1; i < vhtabell.length-1; i++) {
            if (vhtabell[i] == '0')
                denne = denne.venstre;
            else
                denne = denne.høyre;
            if (denne == null)
                return false;
        }
        Node nyNode = new Node(t);
        if (vhtabell[vhtabell.length-1] == '0')
            denne.venstre = nyNode;
        else
            denne.høyre = nyNode;
        return true;
    }

    public T finn(int posisjon) {
        char[] vhtabell = Integer.toBinaryString(posisjon).toCharArray();
        Node denne = rot;
        for (int i = 1; i < vhtabell.length; i++) {
            if (denne == null) {
                return null;
            }
            if (vhtabell[i] == '0')
                denne = denne.venstre;
            else
                denne = denne.høyre;
        }
        return denne.verdi;
    }

    public void printInorden() {
        printInorden(rot);
    }

    private void printInorden(Node n) {
        if (n == null) return;
        printInorden(n.venstre);
        System.out.println(n.verdi);
        printInorden(n.høyre);
    }

    public void printPreorden() {
        printPreorden(rot);
    }

    private void printPreorden(Node n) {
        if (n == null) return;
        System.out.println(n.verdi);
        printPreorden(n.venstre);
        printPreorden(n.høyre);
    }
    public void printPostorden() {
        printPostorden(rot);
    }

    private void printPostorden(Node n) {
        if (n == null) return;
        printPostorden(n.venstre);
        printPostorden(n.høyre);
        System.out.println(n.verdi);
    }

    public void printNivåorden() {
        // Hvordan implementerer vi dette? Svar: Kø.
        if (rot == null) return;
        Kø<Node> kø = new LLStabelOgKø<>();
        kø.enqueue(rot);
        while (!kø.isEmpty()) {
            Node denne = kø.dequeue();
            if (denne.venstre != null)
                kø.enqueue(denne.venstre);
            if (denne.høyre != null)
                kø.enqueue(denne.høyre);
            System.out.println(denne.verdi);
        }
    }
}