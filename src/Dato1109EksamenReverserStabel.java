import java.util.NoSuchElementException;

public class Dato1109EksamenReverserStabel {
}
class StabelEksamen<T> {
    private class Node {
        T verdi; Node neste;
        public Node(T verdi, Node neste) {
            this.verdi = verdi; this.neste = neste;
        }
    }
    private Node rot = null;
    public void push(T verdi) {
        rot = new Node(verdi, rot);
    }
    public T peek() {
        if (tom()) throw new NoSuchElementException("Stabelen er tom!");
        return rot.verdi;
    }
    public T pop() {
        if (tom()) throw new NoSuchElementException("Stabelen er tom!");
        T verdi = rot.verdi;
        rot = rot.neste;
        return verdi;
    }
    public boolean tom() {
        return rot == null;
    }
    public void reverser() {
        Node nyRot = null;
        Node denne = rot;
        while (denne != null) {
            nyRot = new Node(denne.verdi, nyRot);
            denne = denne.neste;
        }
        rot = nyRot;
    }
}