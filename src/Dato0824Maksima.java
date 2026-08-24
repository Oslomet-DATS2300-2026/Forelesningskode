public class Dato0824Maksima {
    public static void main(String[] args) {
        int[] tabell = {1, 4, 17, 0, 9, 13, 5, 2};
        int maksPos = maksima(tabell);
        System.out.println("Maks verdi er " + tabell[maksPos] + " på posisjon " + maksPos + ".");
    }

    public static int maksima(int[] a) {
        // Gir ut posisjonen til det
        // største heltallet i tabell a.
        int maksPos = 0; // Første operasjon.
        int maksVerdi = a[0]; // Andre operasjon. (og tredje? La oss si nei.)
        for (int i = 1; i < a.length; i++) { // 1 + n + (n-1) = 2n
            if (a[i] > maksVerdi) { // (n-1) ganger
                maksVerdi = a[i]; // Ukjent (k) antall ganger
                maksPos = i; // også k ganger
            }
        }
        return maksPos; // Siste operasjon
        // Til sammen: f(n) = 3n + 2 + 2k
        // Best case: k = 0, f(n) = 3n+2
        // Worst case: k = n-1, f(n) = 5n
    }
}