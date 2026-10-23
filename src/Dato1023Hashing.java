import java.util.Objects;

public class Dato1023Hashing {
    public static void main(String[] args) {
        String hei = "Hvordan går det?";
        System.out.println(hei.hashCode());
        String hallo = "Jo det går fint!";
        System.out.println(hallo.hashCode());
        Ansatt nikolai = new Ansatt(15, "Nikolai", new String[] {"DATS2300"});
        System.out.println(nikolai.hashCode());
        Ansatt nikolai2 = new Ansatt(15, "Nikolai", new String[] {"DATS2300", "TRE1300"});
        System.out.println(nikolai2.hashCode());
        System.out.println(nikolai2.equals(nikolai));

        HashAvbildningÅpen<Ansatt, Boolean> erPåJobb = new HashAvbildningÅpen<>(50);
        erPåJobb.leggInn(nikolai, true);
        // er nikolai på jobb?
        System.out.println(erPåJobb.taUt(nikolai2));
    }
}

class Ansatt {
    int ID;
    String navn;
    String[] fag;
    public Ansatt(int ID, String navn, String[] fag) {
        this.ID = ID;
        this.navn = navn;
        this.fag = fag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ansatt ansatt = (Ansatt) o;
        return ID == ansatt.ID && Objects.equals(navn, ansatt.navn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, navn);
    }
}

class HashAvbildningÅpen<K,V> {
    KVPar[] verdier;
    int antall;

    private class KVPar {
        K nøkkel;
        V verdi;
        public KVPar(K nøkkel, V verdi) {
            this.nøkkel = nøkkel;
            this.verdi = verdi;
        }
    }

    @SuppressWarnings("unchecked")
    public HashAvbildningÅpen(int kapasitet) {
        verdier = (KVPar[]) new Object[kapasitet];
    }

    private int beregnPosisjon(K key) {
        int muligensNegativ = key.hashCode() % verdier.length;
        if (muligensNegativ < 0) {
            return muligensNegativ + verdier.length;
        } else {
            return muligensNegativ;
        }
    }

    private void sjekkOgUtvid() {
        if (antall >= 0.75*verdier.length) {
            HashAvbildningÅpen<K,V> ha = new HashAvbildningÅpen(2*verdier.length);
            for (KVPar kv : verdier)
                if (kv != null)
                    ha.leggInn(kv.nøkkel, kv.verdi);
            verdier = ha.verdier;
        }
    }

    public boolean leggInn(K key, V verdi) {
        sjekkOgUtvid();
        int pos = beregnPosisjon(key);
        while (verdier[pos] != null) {
            pos = (pos + 1) % verdier.length;
        }
        verdier[pos] = new KVPar(key, verdi);
        antall++;
        return true;
    }

    public V taUt(K key) {
        int pos = beregnPosisjon(key);
        while (verdier[pos] != null) {
            if (!verdier[pos].nøkkel.equals(key)) {
                pos = (pos + 1) % verdier.length;
            } else {
                return verdier[pos].verdi;
            }
        }
        return null;
    }
}
class HashAvbildningLukket<K,V> {
    KVPar[] verdier;
    int antall;

    private class KVPar {
        K nøkkel;
        V verdi;

        KVPar neste;
        public KVPar(K nøkkel, V verdi, KVPar neste) {
            this.nøkkel = nøkkel;
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    @SuppressWarnings("unchecked")
    public HashAvbildningLukket(int kapasitet) {
        verdier = (KVPar[]) new Object[kapasitet];
    }

    private int beregnPosisjon(K key) {
        int muligensNegativ = key.hashCode() % verdier.length;
        if (muligensNegativ < 0) {
            return muligensNegativ + verdier.length;
        } else {
            return muligensNegativ;
        }
    }

    private void sjekkOgUtvid() {
        if (antall >= 1.5*verdier.length) {
            HashAvbildningLukket<K,V> ha = new HashAvbildningLukket(2*verdier.length);
            for (KVPar kv : verdier)
                while (kv != null) {
                    ha.leggInn(kv.nøkkel, kv.verdi);
                    kv = kv.neste;
                }
            verdier = ha.verdier;
        }
    }

    public boolean leggInn(K key, V verdi) {
        //sjekkOgUtvid();
        int pos = beregnPosisjon(key);
        verdier[pos] = new KVPar(key, verdi, verdier[pos]);
        antall++;
        return true;
    }

    public V taUt(K key) {
        int pos = beregnPosisjon(key);
        KVPar denne = verdier[pos];
        while (denne != null) {
            if (denne.nøkkel.equals(key))
                return denne.verdi;
            denne = denne.neste;
        }
        return null;
    }
}
