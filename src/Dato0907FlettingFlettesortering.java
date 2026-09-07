import java.util.Arrays;

public class Dato0907FlettingFlettesortering {
    public static void main(String[] args) {
        int[] a = {2, 4, 5, 10, 23, 49};
        int[] b = {1, 7, 13, 14, 15, 35, 53, 56};
        int[] res = sortertFletting(a, b);
        System.out.println(Arrays.toString(res));

        int[] tabell = {2, 1, 5, 10, 13, 23, 14, 4, 7, 15, 35, 56, 53, 49};
        int[] sortertTabell = fletteSortering(tabell);
        System.out.println(Arrays.toString(sortertTabell));
    }
    private static int[] sortertFletting(int[] a, int[] b) {
        int[] resultat = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j])
                resultat[k++] = a[i++];
            else
                resultat[k++] = b[j++];
        }
        while (i < a.length)
            resultat[k++] = a[i++];
        while (j < b.length)
            resultat[k++] = b[j++];
        return resultat;
    }

    public static int[] fletteSortering(int[] a) {
        if (a.length < 2) return a.clone();
        int halvveis = a.length/2;
        int[] venstre = new int[halvveis];
        int[] høyre = new int[a.length-halvveis];
        System.arraycopy(a, 0, venstre, 0, halvveis);
        System.arraycopy(a, halvveis, høyre, 0, a.length - halvveis);
        venstre = fletteSortering(venstre);
        høyre = fletteSortering(høyre);
        return sortertFletting(venstre, høyre);
    }
}
