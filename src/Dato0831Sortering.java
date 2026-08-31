import java.util.Arrays;

public class Dato0831Sortering {
    public static void main(String[] args) {
        int[] tabell = {9, 1, 7, 4, 2, 8};

        int[] tilSortering = tabell.clone();
        utvalgssortering(tilSortering);
        System.out.println(Arrays.toString(tilSortering));

        tilSortering = tabell.clone();
        innsettingssortering(tilSortering);
        System.out.println(Arrays.toString(tilSortering));

        tilSortering = tabell.clone();
        boblesorteringv1(tilSortering);
        System.out.println(Arrays.toString(tilSortering));

        tilSortering = tabell.clone();
        boblesorteringv2(tilSortering);
        System.out.println(Arrays.toString(tilSortering));

        tilSortering = tabell.clone();
        boblesorteringv3(tilSortering);
        System.out.println(Arrays.toString(tilSortering));
    }

    public static int maks(int[] a, int fra, int til) {
        if (til > a.length) {
            throw new ArrayIndexOutOfBoundsException("Du har brukt for stor `til`-verdi.");
        }
        if (fra < 0) {
            throw new ArrayIndexOutOfBoundsException("Du har brukt negativ `fra`-verdi.");
        }
        int maksPos = fra;
        int maksVerdi = a[fra];
        for (int i = fra + 1; i < til; i++) {
            if (a[i] > maksVerdi) {
                maksVerdi = a[i];
                maksPos = i;
            }
        }
        return maksPos;
    }

    public static void utvalgssortering(int[] a) {
        for (int i = a.length; i > 0; i--) {
            int maksPos = maks(a, 0, i); // Kjøres ca n ganger
            bytt(a, maksPos, i-1); // kjøres ca n ganger
        }
    }

    public static void bytt(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void innsettingssortering(int[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i-1; j >= 0; j--) {
                if (a[j] > a[j+1]) {
                    bytt(a, j, j+1);
                } else {
                    break;
                }
            }
        }
    }

    // Mest rett frem variant, blir aldri O(n) siden vi også looper gjennom
    // en sortert tabell
    public static void boblesorteringv1(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 1; j < a.length; j++) {
                if (a[j-1] > a[j]) {
                    bytt(a, j-1, j);
                }
            }
        }
    }

    // To forbedringer:
    // Vi stopper når vi ikke lenger fant noen inversjoner, dvs
    // når vi har en sortert tabell.
    // Vi sjekker ikke sluttverdiene, de er jo allerede sortert.
    public static void boblesorteringv2(int[] a) {
        for (int i = 0; i < a.length; i++) {
            boolean inversionFound = false; // Ikke funnet noen i denne omgang
            for (int j = 1; j < a.length - i; j++) {
                if (a[j-1] > a[j]) {
                    inversionFound = true; // Jo, fant visst.
                    bytt(a, j-1, j);
                }
            }
            if (!inversionFound) {
                break; // Om ingen inversjoner, sortert tabell. Vi er ferdige.
            }
        }
    }

    // Siste forbedring:
    // Vi husker _hvor_ vi så siste inversjon.
    // Resten av tabellen er sortert, så holder å
    // sjekke til dette punktet.
    public static void boblesorteringv3(int[] a) {
        int inversionFound = a.length;
        while (inversionFound > 0) {
            int newInversionFound = 0;
            for (int j = 1; j < inversionFound; j++) {
                if (a[j-1] > a[j]) {
                    newInversionFound = j;
                    bytt(a, j-1, j);
                }
            }
            inversionFound = newInversionFound;
        }
    }
}
