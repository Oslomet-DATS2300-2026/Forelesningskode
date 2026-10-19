import java.util.PriorityQueue;

public class Dato1016Huffmann {
    public static void main(String[] args) {
        int bokstav = 'B';
        char tall = 65;
        char annetTall = (char) bokstav;
        System.out.println(bokstav);
        System.out.println(tall);
        System.out.println(annetTall);

        int[] frekvenser = new int[256];
        frekvenser['A'] = 17;
        frekvenser['B'] = 9;
        frekvenser['C'] = 5;
        frekvenser['D'] = 2;
        frekvenser['E'] = 12;
        Huffmantre tre = new Huffmantre(frekvenser);
        String[] koder = tre.finnKoder();
        for (int i = 0; i < 256; i++) {
            if (frekvenser[i] > 0) {
                System.out.println("Koden til " + (char) i + " er " + koder[i]);
            }
        }

        String kode = tre.kod("BAABADA");
        System.out.println(kode);
        String ord = tre.dekod("11100111011000");
        System.out.println(ord);

        int[] lengder = tre.lengder();
        for (int i = 0; i < 256; i++) {
            if (frekvenser[i] > 0) {
                System.out.println("Lengden til " + (char) i + " er " + lengder[i]);
            }
        }

        String[] kanoniskeKoder = tre.finnKanoniskeKoder();
        for (int i = 0; i < 256; i++) {
            if (frekvenser[i] > 0) {
                System.out.println("Kanoniske koden til " + (char) i + " er " + kanoniskeKoder[i]);
            }
        }
    }
}

class Huffmantre {
    private class Node implements Comparable<Node>{
        Node venstre, høyre;
        char verdi;
        int frekvens;

        public Node() {
            this((char)0, 0);
        }

        public Node(char verdi, int frekvens) {
            this.verdi = verdi;
            this.frekvens = frekvens;
            venstre = null;
            høyre = null;
        }

        public Node(Node n1, Node n2) {
            verdi = 0;
            frekvens = n1.frekvens + n2.frekvens;
            venstre = n1;
            høyre = n2;
        }

        @Override
        public int compareTo(Node o) {
            return this.frekvens - o.frekvens;
        }
    }

    Node rot = null;
    int[] frekvenser;

    public Huffmantre(int[] frekvenser) {
        this.frekvenser = frekvenser;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < frekvenser.length; i++) {
            if (frekvenser[i] > 0) {
                Node n = new Node((char) i, frekvenser[i]);
                pq.add(n);
            }
        }
        if (pq.size() < 2)
            throw new IllegalStateException("Må ha minst to symboler med positiv frekvens.");

        while (pq.size() > 1) {
            Node nyNode = new Node(pq.poll(), pq.poll());
            pq.add(nyNode);
        }

        rot = pq.poll();
    }

    public String[] finnKoder() {
        String[] koder = new String[frekvenser.length];
        finnKoder(rot, "", koder);
        return koder;
    }

    private void finnKoder(Node denne, String kodeTilNå, String[] koder) {
        if (denne.verdi != 0) {
            koder[denne.verdi] = kodeTilNå;
            return;
        }
        finnKoder(denne.venstre, kodeTilNå + '0', koder);
        finnKoder(denne.høyre, kodeTilNå + '1', koder);
    }

    public String kod(String s) {
        char[] symboler = s.toCharArray();
        String[] koder = finnKoder();
        StringBuilder kode = new StringBuilder();
        for (char c : symboler) {
            kode.append(koder[c]);
        }
        return kode.toString();
    }

    public String dekod(String kode) {
        StringBuilder svar = new StringBuilder();
        Node denne = rot;
        char[] bits = kode.toCharArray();
        for (char bit : bits) {
            if (bit == '0')
                denne = denne.venstre;
            else
                denne = denne.høyre;
            if (denne.verdi != 0) {
                svar.append(denne.verdi);
                denne = rot;
            }
        }
        if (denne != rot)
            throw new IllegalArgumentException("Streng representer ikke lovlig kode.");
        return svar.toString();
    }

    public int[] lengder() {
        int[] lengder = new int[frekvenser.length];
        lengder(rot, 0, lengder);
        return lengder;
    }

    private void lengder(Node denne, int høyde, int[] lengder) {
        if (denne.verdi != 0)
            lengder[denne.verdi] = høyde;
        else {
            lengder(denne.venstre, høyde + 1, lengder);
            lengder(denne.høyre, høyde + 1, lengder);
        }
    }

    private int maks(int[] a) {
        int maksPos = 0; // Første operasjon.
        int maksVerdi = a[0]; // Andre operasjon. (og tredje? La oss si nei.)
        for (int i = 1; i < a.length; i++) { // 1 + n + (n-1) = 2n
            if (a[i] > maksVerdi) { // (n-1) ganger
                maksVerdi = a[i]; // Ukjent (k) antall ganger
                maksPos = i; // også k ganger
            }
        }
        return maksVerdi; // Siste operasjon
    }

    public String[] finnKanoniskeKoder() {
        int[] lengder = lengder();
        String[] koder = new String[lengder.length];
        int maksLengde = maks(lengder);
        int posisjon = 1 << maksLengde;
        for (int i = maksLengde; i > 0; i--) {
            for (int j = 0; j < lengder.length; j++) {
                if (lengder[j] == i) {
                    koder[j] = Integer.toBinaryString(posisjon).substring(1);
                    posisjon += 1;
                }
            }
            posisjon /= 2;
        }
        return koder;
    }
}