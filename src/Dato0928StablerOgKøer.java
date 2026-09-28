import java.util.*;

public class Dato0928StablerOgKøer {
    public static void main(String[] args) {
        Stabel<Integer> heltallsStabel = new LLStabelOgKø<>();
        int[] tabell = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : tabell)
            heltallsStabel.push(i);
        while (!heltallsStabel.isEmpty())
            System.out.println(heltallsStabel.pop());

        Kø<Integer> heltallsKø = new LLStabelOgKø<>();
        for (int i : tabell)
            heltallsKø.enqueue(i);
        while (!heltallsKø.isEmpty())
            System.out.println(heltallsKø.dequeue());
    }
}

interface Stabel<T> {
    void push(T t);
    T pop();
    T peek();
    boolean isEmpty();
}

interface Kø<T> {
    void enqueue(T t);
    T dequeue();
    T peek();
    boolean isEmpty();
}

interface PrioritetsKø<T> {
    void add(T t, int prioritering);
    T remove();
    T peek();
    boolean isEmpty();
}

class TulleteTabellStabel<T> implements Stabel<T> {
    private ArrayDeque<T> faktiskStabel = new ArrayDeque<>();

    @Override
    public void push(T t) {
        faktiskStabel.push(t);
    }

    @Override
    public T pop() {
        return faktiskStabel.pop();
    }

    @Override
    public T peek() {
        return faktiskStabel.peek();
    }

    @Override
    public boolean isEmpty() {
        return faktiskStabel.isEmpty();
    }
}

class LLStabelOgKø<T> implements Stabel<T>, Kø<T> {
    private class Node {
        T verdi;
        Node neste;
        public Node(T verdi, Node neste) {
            this.verdi = verdi; this.neste = neste;
        }
    }

    Node hode = null;
    Node hale = null;

    @Override
    public void push(T t) {
        hode = new Node(t, hode);
        if (hale == null)
            hale = hode;
    }

    @Override
    public T pop() {
        if (hode == null)
            throw new NoSuchElementException("Stabel er tom.");
        T tmp = hode.verdi;
        hode = hode.neste;
        if (hode == null)
            hale = null;
        return tmp;
    }

    @Override
    public void enqueue(T t) {
        Node tmp = new Node(t, null);
        if (hale == null) {
            hode = tmp;
            hale = tmp;
        } else {
            hale.neste = tmp;
            hale = hale.neste;
        }
    }

    @Override
    public T dequeue() {
        return pop();
    }

    @Override
    public T peek() {
        if (hode == null)
            throw new NoSuchElementException("Stabelen/Køen er tom.");
        return hode.verdi;
    }

    @Override
    public boolean isEmpty() {
        return (hode == null);
    }
}