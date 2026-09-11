import java.util.Comparator;
import java.util.NoSuchElementException;

public class Dato0911GeneriskKode {
    public static void main(String[] args) {
        String[] strengTabell = {"Hei", "Hallo", "Hvordan går det?"};
        bytt(strengTabell, 0, 1);
        A[] aTabell = {new A(), new A(), new A()};
        bytt(aTabell, 0, 1);

        String hei = (String) finnVerdi_old(strengTabell, new A());
        hei = finnVerdi(strengTabell, "Hei");
        //hei = finnVerdi(strengTabell, new A()); // Her skjønner java at T må være String.
        finnVerdi(strengTabell, new A()); // Her tror java at T er Object.

        // Kan spesifisere typen (men litt stygg notasjon her)
        Dato0911GeneriskKode.<String>finnVerdi(strengTabell, "Hei");
        //Dato0911GeneriskKode.<String>finnVerdi(strengTabell, new A()) // funker ikke

        // Vi "bokser inn" hetall
        Heltall[] heltallsListe = {new Heltall(5), new Heltall(7), new Heltall(12)};
        bytt(heltallsListe, 0, 1);

        // finnes allerede i java:
        Integer[] heltallsListe2 = {5, 7, 12};
        bytt(heltallsListe2, 0, 1);

        // merk: dette funker ikke:
        int[] primitivHeltallListe = {5, 7, 12};
        //Integer[] heltallsListe3 = primitivHeltallListe;
        // Må gjøre:
        Integer[] heltallsListe3 = new Integer[3];
        for (int i = 0; i < 3; i++) {
            heltallsListe3[i] = primitivHeltallListe[i];
        }
        int funnetTolv = finnVerdi(heltallsListe3, 12);

        Integer nullPeker = null;
        // int nullVerdi = nullPeker; // dette gir nullpointerexception

        System.out.println("Er 'a' mindre enn 'b'?: " + ('a' < 'b'));

        // Vil dette funke?
        Heltall maksVerdi = maksGenerisk(heltallsListe);
        System.out.println("Maks verdi i lista: " + maksVerdi.verdi);

        maksVerdi = maksGenerisk(heltallsListe, new HeltallsComparator());
    }

    public static void bytt(int[] tabell, int i, int j) {
        int tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static int finnVerdi(int[] tabell, int verdi) {
        for (int i = 0; i < tabell.length; i++) {
            if (tabell[i] == verdi)
                return tabell[i];
        }
        return 0;
    }

    public static int maks(int[] tabell) {
        if (tabell.length == 0) throw new NoSuchElementException("Tom liste har ingen maks.");
        int maksVerdi = tabell[0];
        for (int i = 1; i < tabell.length; i++) {
            if (tabell[i] > maksVerdi) {
                maksVerdi = tabell[i];
            }
        }
        return maksVerdi;
    }

    // Løsning 1: Dårlig.
    public static void bytt_old(Object[] tabell, int i, int j) {
        Object tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static Object finnVerdi_old(Object[] tabell, Object verdi) {
        for (int i = 0; i < tabell.length; i++) {
            if (tabell[i].equals(verdi))
                return tabell[i];
        }
        return null;
    }

    // Løsning 2: Mye bedre
    public static <Type> void bytt(Type[] tabell, int i, int j) {
        Type tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    public static <T> T finnVerdi(T[] tabell, T verdi) {
        for (int i = 0; i < tabell.length; i++) {
            if (tabell[i].equals(verdi))
                return tabell[i];
        }
        return null;
    }


    public static <T extends Comparable<? super T>> T maksGenerisk(T[] tabell) {
        if (tabell.length == 0) throw new NoSuchElementException("Tom liste har ingen maks.");
        T maksVerdi = tabell[0];
        for (int i = 1; i < tabell.length; i++) {
            if (tabell[i].compareTo(maksVerdi) > 0) {
                maksVerdi = tabell[i];
            }
        }
        return maksVerdi;
    }

    public static <T> T maksGenerisk(T[] tabell, Comparator<? super T> sammenlikner) {
        if (tabell.length == 0) throw new NoSuchElementException("Tom liste har ingen maks.");
        T maksVerdi = tabell[0];
        for (int i = 1; i < tabell.length; i++) {
            if (sammenlikner.compare(tabell[i], maksVerdi) > 0) {
                maksVerdi = tabell[i];
            }
        }
        return maksVerdi;
    }

    public static <T extends Comparable<? super T>> T maksGenerisk2(T[] tabell) {
        return maksGenerisk(tabell, Comparator.naturalOrder());
    }

    public static <T extends Comparable<? super T>> T minGenerisk(T[] tabell) {
        return maksGenerisk(tabell, Comparator.reverseOrder());
    }
}

class A {}

class Heltall implements Comparable<Heltall> {
    int verdi;

    public Heltall(int verdi) {
        this.verdi = verdi;
    }

    @Override
    public int compareTo(Heltall other) {
        return this.verdi - other.verdi;
    }
}

class HeltallsComparator implements Comparator<Heltall> {
    @Override
    public int compare(Heltall o1, Heltall o2) {
        if (o1.verdi % 2 != 0) return -1;
        return o1.verdi - o2.verdi;
    }
}
