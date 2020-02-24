package miinaharava.mallit;

/**
 * Vaikeustaso on container-luokka miinaharava-pelin vaikeustasolle.
 *
 * @author Jaakko Ikäheimo
 */
public class Vaikeustaso {

    public final String nimi;
    public final int rivienLkm;
    public final int sarakkeidenLkm;
    public final int miinojenLkm;

    public Vaikeustaso(String nimi, int kentanKoko, int miinojenLkm) {
        this(nimi, kentanKoko, kentanKoko, miinojenLkm);
    }

    public Vaikeustaso(String nimi, int rivienLkm, int sarakkeidenLkm, int miinojenLkm) {
        this.nimi = nimi;
        this.rivienLkm = rivienLkm;
        this.sarakkeidenLkm = sarakkeidenLkm;
        this.miinojenLkm = miinojenLkm;
    }
}
