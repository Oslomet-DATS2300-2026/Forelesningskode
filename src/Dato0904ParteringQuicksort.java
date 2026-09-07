import java.util.Arrays;

public class Dato0904ParteringQuicksort {
    public static void main(String[] args) {
        char[] tabell = {'a', 'c', 'd', 'e', 'h', 'i', 'k', 'o', 'i', 'r', 'u', 'z'};
        int førsteKonsonant = parterv1vokalmentullete(tabell, 0, 11);
        System.out.println("Første konsonant er nå på plass " + førsteKonsonant);
        System.out.println(Arrays.toString(tabell));

        int[] talltabell = {1, 5, 7, 8, 2, 3, 6};
        int førsteStørre = parterSkilleverdi(talltabell, 0, 6, talltabell[6]);
        System.out.println("Første verdi større eller lik 6 er på plass " + førsteStørre);
        System.out.println(Arrays.toString(talltabell));

        talltabell = new int[]{1, 5, 7, 8, 2, 3, 6};
        kvikkSortering(talltabell);
        System.out.println(Arrays.toString(talltabell));
    }

    public static boolean erVokal(char c) {
        char[] vokaler = {'a', 'e', 'i', 'o', 'u', 'y'};
        for (int i = 0; i < vokaler.length; i++) {
            if (c == vokaler[i])
                return true;
        }
        return false;
    }

    public static void bytt(char[] a, int i, int j) {
        char tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void bytt(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static int parterv1vokal(char[] tabell) {
        return parterv1vokal(tabell, 0, tabell.length-1);
    }
    private static int parterv1vokal(char[] tabell, int venstre, int høyre) {
       while (venstre <= høyre) {
           if (erVokal(tabell[venstre])) {
               venstre++;
           } else {
               bytt(tabell, venstre, høyre--);
           }
       }
       return venstre;
    }

    private static int parterv1vokalmentullete(char[] tabell, int venstre, int høyre) {
        for (;venstre <= høyre; venstre++) {
            if (!erVokal(tabell[venstre]))
                bytt(tabell, venstre--, høyre--);
        }
        return venstre;
    }

    public static int parterv2vokal(char[] tabell) {
        int venstre = 0;
        int høyre = tabell.length-1;
        while (true) {
            while (venstre <= høyre && erVokal(tabell[venstre]))
                venstre++;
            while (venstre <= høyre && !erVokal(tabell[høyre]))
                høyre--;
            if (venstre < høyre)
                bytt(tabell, venstre, høyre);
            else break;
        }
        return venstre;
    }

    private static int parterSkilleverdi(int[] tabell, int v, int h, int pivot) {
        while (true) {
            while (v <= h && tabell[v] < pivot) v++;
            while (v <= h && tabell[h] >= pivot) h--;
            if (v < h)
                bytt(tabell, v, h);
            else break;
        }
        return v;
    }

    public static void kvikkSortering(int[] tabell) {
        kvikkSortering(tabell, 0, tabell.length-1);
    }

    private static void kvikkSortering(int[] tabell, int v, int h) {
        if (v >= h) return;
        int midt_i = (v+h)/2;
        bytt(tabell, midt_i, h);
        int skilleverdi = tabell[h];
        int førsteStore = parterSkilleverdi(tabell, v, h-1, skilleverdi);
        bytt(tabell, h, førsteStore);
        kvikkSortering(tabell, v, førsteStore-1);
        kvikkSortering(tabell, førsteStore+1, h);
    }
}
