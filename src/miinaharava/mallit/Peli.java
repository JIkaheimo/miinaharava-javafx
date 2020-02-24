package miinaharava.mallit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Peli tarjoaa rajapinnan Miinaharava-pelille.
 *
 * SALLITUT INTERAKTIOT:
 * <ul>
 * <li>uusi peli
 * <li>käännä ruutu
 * <li>aseta lippu ruutuun
 * <li>poista lippu
 * </ul>
 *
 * @author Jaakko Ikäheimo
 *
 *
 */
public class Peli {

    // Pelin olioattribuuutit.
    private Vaikeustaso vaikeustaso;
    private final Lauta lauta = new Lauta();

    private IntegerProperty miinojaJaljella = new SimpleIntegerProperty(0);

    // Pelin tila-attribuutit.
    private final BooleanProperty peliHavitty = new SimpleBooleanProperty(false);
    private final BooleanProperty peliVoitettu = new SimpleBooleanProperty(false);

    /**
     * Pelin konstruktori
     *
     * @param vaikeustaso
     */
    public Peli(Vaikeustaso vaikeustaso) {
        asetaVaikeustaso(vaikeustaso);
    }

    /**
     * asetaVaikeustaso() asettaa pelin vaikeustason ja aloittaa uuden pelin.
     *
     * @param vaikeustaso
     */
    public final void asetaVaikeustaso(Vaikeustaso vaikeustaso) {
        this.vaikeustaso = vaikeustaso;
        uusiPeli();
    }

    public ReadOnlyIntegerProperty miinojaJaljellaProp() {
        return miinojaJaljella;
    }

    /**
     * haeLauta() palauttaa pelilaudan.
     *
     * @return pelin lauta
     */
    public Lauta haeLauta() {
        return lauta;
    }

    /**
     * uusiPeli() aloittaa uuden pelin alustamalla pelilaudan ja muuttujat.
     */
    public void uusiPeli() {
        lauta.alusta(vaikeustaso.rivienLkm, vaikeustaso.sarakkeidenLkm, vaikeustaso.miinojenLkm);

        this.peliHavitty.set(false);
        this.peliVoitettu.set(false);

        this.miinojaJaljella.set(vaikeustaso.miinojenLkm);
    }

    /**
     * kaannaRuutu() kääntää annetun ruudun laudalla. Jos ruutu ei ole
     * käännettävissä tai peli on lopetettu, mitään ei tapahdu.
     *
     * @param kaannettavaRuutu
     */
    public void kaannaRuutu(Ruutu kaannettavaRuutu) {

        // Tarkistetaan onko ruutu käännettävissä ja pelin tila.
        if (peliLoppunut() || !kaannettavaRuutu.onKaannettavissa()) {
            return;
        }

        // Haetaan ruudun indeksi ja lasketaan sen perusteella sen sijainti laudalla.
        int indeksi = lauta.ruudut.indexOf(kaannettavaRuutu);
        int rivi = indeksi / lauta.sarakemaara();
        int sarake = indeksi % lauta.sarakemaara();

        kaannaRuutu(rivi, sarake);
    }

    /**
     * Kääntää annetussa koordinaatissa olevan ruudun pelilaudalla.
     *
     * @param rivi
     * @param sarake
     */
    private void kaannaRuutu(int rivi, int sarake) {
        Ruutu kaannettavaRuutu = lauta.haeRuutu(rivi, sarake);

        // Testataan onko ruutu oikeellinen
        if (kaannettavaRuutu == null) {
            return;
        }

        // Käännetään itse ruutu..
        kaannettavaRuutu.kaanna();

        // Tarkistetaan onko päli hävitty tai voitettu.
        if (kaannettavaRuutu.onMiinoitettu()) {
            haviaPeli();
            return;
        } else if (onkoVoitettu()) {
            voitaPeli();
            return;
        }

        // Jos miinoja on naapurissa ei käännetä viereisiä ruutuja.
        if (kaannettavaRuutu.miinojaNaapurissa()) {
            return;
        }

        // Haetaan vierekkäiset ruudut.
        Ruutu[] vierekkaisetRuudut = {
            lauta.haeRuutu(rivi - 1, sarake), // yläpuoli
            lauta.haeRuutu(rivi, sarake - 1), // vasen
            lauta.haeRuutu(rivi, sarake + 1), // oikea
            lauta.haeRuutu(rivi + 1, sarake), // alapuoli
        };

        // Käännetään naapurit rekursiivisesti
        for (Ruutu vierekkainen : vierekkaisetRuudut) {
            if (vierekkainen != null) {
                kaannaRuutu(vierekkainen);
            }
        }
    }

    /**
     * Vaihtaa lipun tilaa ruudussa. Jos ruudun tila ei salli liputtamista,
     * mitään ei tapahdu.
     *
     * @param ruutu mihin lippu ollaan vaihtamassa
     */
    public void vaihdaLippuaRuudussa(Ruutu ruutu) {

        // Tarkistetaan pystyykö lipun tilaa vaihtamaan.
        if (!ruutu.lippuVoidaanVaihtaa()) {
            return;
        }

        // Vaihdetaan lipun tilaa.
        if (ruutu.onLiputettu()) {
            ruutu.poistaLippu();
        } else {
            ruutu.asetaLippu();
        }
    }

    /**
     *
     * @return
     */
    public ReadOnlyBooleanProperty peliVoitettuProperty() {
        return peliVoitettu;
    }

    public ReadOnlyBooleanProperty peliHavittyProperty() {
        return peliHavitty;
    }

    /**
     * kaannaMiinat() kääntää kaikki ruudut, jossa on miina.
     */
    private void kaannaMiinat() {
        lauta.ruudut
                .stream()
                .filter((ruutu) -> (ruutu.onMiinoitettu()) && !(ruutu.onKaannetty()))
                .forEach((ruutu) -> ruutu.kaanna());
    }

    /**
     * kaannaMiinat() kääntää kaikki laudan ruudut.
     */
    private void kaannaRuudut() {
        lauta.ruudut
                .stream()
                .filter((ruutu) -> !ruutu.onKaannetty())
                .forEach((ruutu) -> ruutu.kaanna());
    }

    /**
     * onkoVoitettu() palauttaa tiedon siitä onko peli voitettu, eli onko kaikki
     * miinoittamattomat ruudu käännetty onnistuneesti.
     *
     * @return onko peli voitettu
     */
    public boolean onkoVoitettu() {
        // Haetaan käännettyjen, miinoittamattomien ruutujen lukumäärä
        long kaannettyjaMiinoittamattomia
                = lauta.ruudut
                        .stream()
                        .filter(ruutu -> !ruutu.onMiinoitettu() && ruutu.onKaannetty())
                        .count();

        return (lauta.ruutumaara() - vaikeustaso.miinojenLkm) == kaannettyjaMiinoittamattomia;
    }

    void haviaPeli() {
        kaannaMiinat();
        peliHavitty.set(true);
    }

    void voitaPeli() {
        kaannaRuudut();
        peliVoitettu.set(true);
    }

    /**
     * peliLoppunut() palauttaa tiedon siitä onko peli loppunut.
     *
     * @return onko peli loppunut.
     */
    public boolean peliLoppunut() {
        return onkoVoitettu() || peliHavitty.get();
    }

    /**
     * poistaLippu(Ruutu ruutu) poistaa lipun annetusta ruudusta. Jos lippua ei
     * ole ruudussa niin mitään ei tapahdu.
     *
     * @param ruutu josta lippu poistetaan.
     */
    void poistaLippu(Ruutu ruutu) {
        if (!ruutu.onLiputettu()) {
            return;
        }
        ruutu.poistaLippu();
        miinojaJaljella.set(miinojaJaljella.get() + 1);
    }

    /**
     * asetaLippu asettaa lipun annettuun ruutuun. Jos lippu on jo asetettu
     * mitään ei tapahdu.
     *
     * @param ruutu mihin lippu asetetaan.
     */
    void asetaLippu(Ruutu ruutu) {
        if (ruutu.onLiputettu()) {
            return;
        }
        ruutu.asetaLippu();
        miinojaJaljella.set(miinojaJaljella.get() - 1);
    }
}
