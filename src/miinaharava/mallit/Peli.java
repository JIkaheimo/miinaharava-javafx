package miinaharava.mallit;

import javafx.beans.property.*;

/**
 * Peli tarjoaa rajapinnan Miinaharava-pelille.
 *
 * SALLITUT INTERAKTIOT:
 * <ul>
 * <li>uusi peli
 * <li>käännä ruutu
 * <li>lipun asetus/poisto ruudusta
 * </ul>
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Pelin perustoiminnallisuus ja rakenne valmis.
 * @version 1.1.0 Siirrä Lauta-luokalta pelilogiikka Peli-luokkaan.
 * @version 1.2.0 Lisää propertyt pelin tilan seuraamiseksi.
 */
public class Peli {

    private Vaikeustaso vaikeustaso;

    private final Lauta lauta = new Lauta();

    private final BooleanProperty peliHavitty
            = new SimpleBooleanProperty(false);

    private final BooleanProperty peliVoitettu
            = new SimpleBooleanProperty(false);

    private final IntegerProperty miinojaJaljella
            = new SimpleIntegerProperty(0);

    /**
     * Pelin konstruktori
     *
     * @param vaikeustaso
     */
    public Peli(Vaikeustaso vaikeustaso) {
        asetaVaikeustaso(vaikeustaso);
    }

    /**
     * peliVoitettuProp() palauttaa luettavan propertyn, millä pystytään
     * seuraamaan onko peli voitettu.
     *
     * @return pelin voiton tilan property.
     */
    public final ReadOnlyBooleanProperty peliVoitettuProp() {
        return peliVoitettu;
    }

    /**
     * peliHavittyProp() palauttaa luettavan propertyn, millä pysytään
     * seuraamaan onko peli hävitty.
     *
     * @return pelin häviön tilan propery.
     */
    public final ReadOnlyBooleanProperty peliHavittyProp() {
        return peliHavitty;
    }

    /**
     * miinojaJaljellaProp() palauttaa laudan miinojen - lippujen lukumäärän
     * propertyn.
     *
     * @return
     */
    public final ReadOnlyIntegerProperty miinojaJaljellaProp() {
        return miinojaJaljella;
    }

    /**
     * asetaVaikeustaso() asettaa pelin vaikeustason.
     *
     * @param vaikeustaso
     */
    public final void asetaVaikeustaso(Vaikeustaso vaikeustaso) {
        this.vaikeustaso = vaikeustaso;
    }

    /**
     * haeLauta() palauttaa pelilaudan.
     *
     * @return pelin lauta
     */
    public final Lauta haeLauta() {
        return lauta;
    }

    /**
     * uusiPeli() aloittaa uuden pelin alustamalla pelilaudan ja muuttujat.
     */
    public final void uusiPeli() {
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
    public final void kaannaRuutu(Ruutu kaannettavaRuutu) {

        // Tarkistetaan onko ruutu käännettävissä ja pelin tila.
        if (onkoLoppunut() || !kaannettavaRuutu.onKaannettavissa()) {
            return;
        }

        // Haetaan ruudun indeksi ja lasketaan sen perusteella sen sijainti laudalla.
        int indeksi = lauta.ruudut.indexOf(kaannettavaRuutu);
        int rivi = indeksi / lauta.sarakemaara();
        int sarake = indeksi % lauta.sarakemaara();

        kaannaRuutu(rivi, sarake);
    }

    /**
     * Kääntää annetussa koordinaatissa sijaitsevan ruudun pelilaudalla. Jos
     * ruutu ei sijaitse laudalla, mitään ei tapahdu.
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
    public final void vaihdaLippuaRuudussa(Ruutu ruutu) {

        // Tarkistetaan pystyykö lipun tilaa vaihtamaan.
        if (!ruutu.lippuVoidaanVaihtaa()) {
            return;
        }

        // Vaihdetaan lipun tilaa.
        if (ruutu.onLiputettu()) {
            poistaLippu(ruutu);
        } else {
            asetaLippu(ruutu);
        }
    }

    /**
     * poistaLippu(Ruutu ruutu) poistaa lipun annetusta ruudusta. Jos lippua ei
     * ole ruudussa tai peli ei ole käynnissä niin mitään ei tapahdu.
     *
     * @param ruutu josta lippu poistetaan.
     */
    private void poistaLippu(Ruutu ruutu) {
        if (onkoLoppunut() || !ruutu.onLiputettu()) {
            return;
        }
        ruutu.poistaLippu();
        miinojaJaljella.set(miinojaJaljella.get() + 1);
    }

    /**
     * asetaLippu asettaa lipun annettuun ruutuun. Jos lippu on jo asetettu tai
     * peli ei ole käynnissä niin mitään ei tapahdu.
     *
     * @param ruutu mihin lippu asetetaan.
     */
    private void asetaLippu(Ruutu ruutu) {
        if (onkoLoppunut() || ruutu.onLiputettu()) {
            return;
        }
        ruutu.asetaLippu();
        miinojaJaljella.set(miinojaJaljella.get() - 1);
    }

    /**
     * kaannaMiinat() kääntää kaikki ruudut missä on miina.
     */
    private void kaannaMiinat() {
        lauta.kaantamattomatMiinallisetRuudut()
                .forEach((ruutu) -> ruutu.kaanna());
    }

    /**
     * liputaMiinat() asettaa lipun kaikkiin ruutuihin missä on miina.
     */
    private void liputaMiinat() {
        lauta.kaantamattomatMiinallisetRuudut()
                .forEach((ruutu) -> ruutu.asetaLippu());
    }

    /**
     * onkoVoitettu() palauttaa tiedon siitä onko peli voitettu, eli onko kaikki
     * miinoittamattomat ruudu käännetty onnistuneesti.
     *
     * @return onko peli voitettu
     */
    public final boolean onkoVoitettu() {
        // Haetaan käännettyjen, miinoittamattomien ruutujen lukumäärä
        int kaannettyjaMiinoittamattomia
                = lauta.kaannetytMiinattomatRuudut().size();

        return (lauta.ruutumaara() - vaikeustaso.miinojenLkm) == kaannettyjaMiinoittamattomia;
    }

    /**
     * haviaPeli() suorittaa pelin häviön yhteydessä tehtävät toimenpiteet.
     */
    private void haviaPeli() {
        kaannaMiinat();
        peliHavitty.set(true);
    }

    /**
     * voitaPeli() suorittaa pelin voiton yhteydessä tehtävät toimenpiteet.
     */
    private void voitaPeli() {
        liputaMiinat();
        peliVoitettu.set(true);
    }

    /**
     * onkoLoppunut() palauttaa tiedon siitä onko peli loppunut.
     *
     * @return onko peli loppunut.
     */
    public final boolean onkoLoppunut() {
        return peliVoitettu.get() || peliHavitty.get();
    }

}
