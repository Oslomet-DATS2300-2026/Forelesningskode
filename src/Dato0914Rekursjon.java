import java.util.Arrays;

public class Dato0914Rekursjon {
    public static void main(String[] args) {
        int n = 100000;
        long svar = summerTallIterativt(n);
        System.out.println("Svaret ble " + svar);

        hanoisTårn(4, 'A', 'C', 'B');

        int[] tabell = {2, 3, 1, 7, 9, 5, 3, 4, 0};
        quickSort(tabell, 0, 8);
        System.out.println(Arrays.toString(tabell));
    }

    static int trekk = 1;

    public static long summerTallIterativt(int n) {
        long sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static int summerTallRekursivt(int n) {
        if (n <= 0) return 0;
        return n + summerTallRekursivt(n-1);
    }

    public static int summerTallHalerekursiv(int n) {
        return summerTallHalerekursiv(n, 0);
    }

    private static int summerTallHalerekursiv(int n, int tilNå) {
        if (n <= 0) return tilNå;
        return summerTallHalerekursiv(n-1, tilNå + n);
    }

    private static int summerTallHaleRekTilIt(int n, int tilNå) {
        while (true) {
            if (n <= 0) return tilNå;
            n = n-1;
            tilNå = tilNå + n;
        }
    }

    public static void hanoisTårn(int n, char start, char slutt, char hjelp) {
        if (n <= 0) return;
        hanoisTårn(n-1, start, hjelp, slutt);
        System.out.println("Trekk " + trekk++ + " Flytt brikke fra " + start + " til " + slutt + ".");
        hanoisTårn(n-1, hjelp, slutt, start);
    }

    public static int partisjoner(int[] tabell, int v, int h, int pivot) {
        while (true) {
            while (v <= h && tabell[v] < pivot) v++;
            while (v <= h && tabell[h] >= pivot) h--;
            if (v < h)
                bytt(tabell, v, h);
            else break;
        }
        return v;
    }

    public static void bytt(int[] tabell, int i, int j) {
        int tmp = tabell[i];
        tabell[i] = tabell[j];
        tabell[j] = tmp;
    }

    private static void quickSort(int[] tabell, int fra, int til) {
        int[] stack = new int[2*tabell.length];
        stack[0] = fra;
        stack[1] = til;
        int stackPeker = 2;

        while (stackPeker > 0) {
            til = stack[--stackPeker];
            fra = stack[--stackPeker];
            if (fra >= til) continue;
            int pivot = tabell[til];
            int k = partisjoner(tabell, fra, til - 1, pivot);
            bytt(tabell, k, til);
            stack[stackPeker++] = fra;
            stack[stackPeker++] = k-1;
            stack[stackPeker++] = k+1;
            stack[stackPeker++] = til;
        }
    }
}
