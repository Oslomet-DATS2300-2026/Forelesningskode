import java.lang.reflect.Array;
import java.util.*;

public class Dato1026_234TreBonus {
    public static void main(String[] args) {
        ToTreFireTre<Integer> tre = new ToTreFireTre<>(Comparator.naturalOrder());
        assert !tre.finn(12) : "Treet er tomt, burde gi false";
        int[] verdier = {3, 17, 13, 10, 8, 11, 9, 7, 2, 5, 4, 19, 14, 1, 0, 15, 18, 12, 6, 16};
        for (int i : verdier) {
            boolean innlagt = tre.leggInn(i);
            assert innlagt : "Verdiene ligger ikke i treet, burde gi true";
        }
        assert tre.toString().equals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]") : "Treet sorterer feil eller mangler elementer.";
        assert tre.finn(9) : "9 ligger i treet, burde gi true";
        assert !tre.finn(25) : "25 ligger ikke i treet, burde gi false";
        boolean alleredeDer = tre.leggInn(11);
        assert !alleredeDer : "11 ligger allerede i treet, burde gi false";
    }
}

class ToTreFireTre <T> {
    private class Node {
        Node[] barn;
        T[] verdier;
        int antallVerdier;

        @SuppressWarnings("unchecked")
        public Node() {
            // Arrays.asList lager en fikset-lengde-liste, så dette er essensielt T[3] og Node[4]
            verdier = (T[]) new Object[3];
            barn = (Node[]) Array.newInstance(Node.class, 4);
            antallVerdier = 0;
        }

        public Node(T verdi, Node venstre, Node høyre) {
            this();
            verdier[0] = verdi;
            antallVerdier++;
            barn[0] = venstre;
            barn[1] = høyre;
        }

        public boolean leggInn(T verdi) {
            assert antallVerdier < 3 : "Vi kan ikke legge inn i en full node. Vi burde ha splittet tidligere.";
            for (int i = 0; i < 3; i++) {
                if (verdier[i] == null) {
                    verdier[i] = verdi;
                    break;
                }
                int cV = cmp.compare(verdi, verdier[i]);
                if (cV < 0) {
                    T tmp = verdier[i];
                    verdier[i] = verdi;
                    verdi = tmp;
                } else if (cV == 0) {
                    return false;
                }
            }
            antallVerdier++;
            return true;
        }

        public int finnBarn(T verdi) {
            // returnerer første verdi vi er større enn eller lik.
            for (int i = 0; i < antallVerdier; i++) {
                if (cmp.compare(verdi, verdier[i]) < 0)
                    return i;
            }
            return antallVerdier;
        }

        public void splittBarn(int i) {
            assert antallVerdier < 3 : "Vi kan ikke splitte et barn om vi selv er fulle.";
            Node relevantBarn = barn[i];
            assert relevantBarn.antallVerdier == 3 : "Vi kan ikke splitte en node som har færre enn 3 barn.";
            Node venstreDel = new Node(relevantBarn.verdier[0], relevantBarn.barn[0], relevantBarn.barn[1]);
            Node høyreDel = new Node(relevantBarn.verdier[2], relevantBarn.barn[2], relevantBarn.barn[3]);
            System.arraycopy(verdier, i, verdier, i+1, 2-i); // Flytt alle verdier ett steg mot høyre
            verdier[i] = relevantBarn.verdier[1]; // Legg inn midterste verdi fra det relevante barnet.
            System.arraycopy(barn, i+1, barn, i+2, 2-i); // Flytt alle barn ett steg mot høyre
            barn[i] = venstreDel; // Fyll inn venstrebiten
            barn[i+1] = høyreDel; // Fyll inn høyrebiten
            antallVerdier++;
        }

        public boolean erBlad() {
            // Vi er en bladnode om vi ikke har noen barn.
            // Siden barn[0] er første barn, betyr det at vi ikke har et første barn.
            return barn[0] == null;
        }
    }
    Comparator<? super T> cmp;
    Node rot;

    public ToTreFireTre(Comparator<? super T> cmp) {
        rot = new Node();
        this.cmp = cmp;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        if (rot.antallVerdier == 3) {
            Node nyRot = new Node();
            nyRot.barn[0] = rot;
            nyRot.splittBarn(0);
            rot = nyRot;
        }
        Node denne = rot;
        // Vet at vi er i en node med mindre enn 3 verdier
        while (!denne.erBlad()) {
            int i = denne.finnBarn(verdi);
            if (i > 0 && verdi.equals(denne.verdier[i-1]))
                // Om relevant barn er på plass i, er verdien som muligens er lik på plass i-1.
                // Men må sjekke om i > 0, for ellers får vi feilmelding.
                return false; // Ikke lov med like verdier
            if (denne.barn[i].antallVerdier == 3) {
                denne.splittBarn(i);
                continue;
                // Om vi splitter så vet vi ikke om vi skal til venstre, høyre, eller har lik verdi.
                // Jeg er lat, så jeg sjekker igjen. Kunne bare sjekka hvilke av disse tre som gjelder manuelt.
            }
            denne = denne.barn[i];
        }
        // denne er nå et barn, og har mindre enn 3 verdier
        return denne.leggInn(verdi);
    }

    public boolean finn(T verdi) {
        Objects.requireNonNull(verdi);
        Node denne = rot;
        while (denne != null) {
            int i = denne.finnBarn(verdi);
            if (i > 0 && verdi.equals(denne.verdier[i-1]))
                return true;
            denne = denne.barn[i];
        }
        return false;
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        toStringRec(sj, rot);
        return sj.toString();
    }

    private void toStringRec(StringJoiner sj, Node denne) {
        if (denne.erBlad()) {
            for (int i = 0; i < denne.antallVerdier; i++) {
                sj.add(denne.verdier[i].toString());
            }
        } else {
            for (int i = 0; i < denne.antallVerdier; i++) {
                toStringRec(sj, denne.barn[i]);
                sj.add(denne.verdier[i].toString());
            }
            toStringRec(sj, denne.barn[denne.antallVerdier]);
        }
    }
}