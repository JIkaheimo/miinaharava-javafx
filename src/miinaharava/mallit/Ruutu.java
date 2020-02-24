package miinaharava.mallit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Ruutu kuvaa Miinaharava-pelin yhtä peliruutua, minkä pystyy kääntämään tai
 * liputtamaan. Pelin tulisi vain muokata itse ruudun tilaa...
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Ruudun perustoiminnallisuus ja rakenne toteutettu.
 * @version 1.1.0 Lisää proeprtyt tilan kuuntelemiseksi.
 */
public class Ruutu {

    /**
     * Ruudun tilamuuttujat
     */
    private boolean ruutuMiinoitettu = false;

    private int naapuriMiinojenLkm = 0;

    private final BooleanProperty ruutuKaannetty
            = new SimpleBooleanProperty(false);

    private final BooleanProperty ruutuLiputettu
            = new SimpleBooleanProperty(false);

    /**
     * asetaMiina() asettaa miinan ruutuun, nakkaa virheen jos miina on jo
     * asetettu. Virhetilanteen voi välttää tarkistamalla onko ruutu jo
     * miinoitettu onMiinoitetu()-metodilla.
     */
    void asetaMiina() {
        if (ruutuMiinoitettu) {
            throw new Error("Ruutu:asetaMiina, Miina on jo asetettu!");
        }

        ruutuMiinoitettu = true;
    }

    /**
     * onkoMiinoitettu() palauttaa tiedon siitä, onko ruutu miinoitettu.
     *
     * @return onko ruutu miinoitettu
     */
    public boolean onMiinoitettu() {
        return ruutuMiinoitettu;
    }

    /**
     * asetaNaapuriMiinojenLkm() asettaa ruudun naapuriruutujen miinojen
     * lukumäärän.
     *
     * @param miinojenLkm naapuriruutujen miinojen lukumäärä
     */
    void asetaNaapuriMiinojenLkm(int miinojenLkm) {
        naapuriMiinojenLkm = miinojenLkm;
    }

    /**
     * naapuriMiinojenLkm() palauttaa tiedon ruudun naapurissa sijaitsevien
     * miinojen lukumäärästä.
     *
     * @return naapurissa sijaitsevien miinojen lukumäärä
     */
    public int naapuriMiinojenLkm() {
        return naapuriMiinojenLkm;
    }

    /**
     * kaanna() kääntää ruudun ja nakkaa virheen jos jo käännettyä tai
     * liputettua ruutua yritetään kääntää. Käännön oikeellisuus voidaan testata
     * onKaannettavissa()-metodilla.
     */
    void kaanna() {
        if (!onKaannettavissa()) {
            throw new Error("Ruutu:kaanna, Ruutua ei pystytä kääntämään!");
        }
        ruutuKaannetty.set(true);
    }

    /**
     * ruutuKaannetty() palauttaa tiedon siitä onko ruutu jo käännetty.
     *
     * @return onko ruutu käännetty
     */
    public boolean onKaannetty() {
        return ruutuKaannetty.get();
    }

    /**
     * ruutuKaannettyProp() palauttaa luettavan propertyn, millä pystytään
     * seuraamaan ruudun käännön tilaa.
     *
     * @return ruudun käännön tilan property
     */
    public ReadOnlyBooleanProperty ruutuKaannettyProp() {
        return ruutuKaannetty;
    }

    /**
     * asetaLippu() asettaa lipun ruutuun. Nakkaa virheen jos lippu yritetään
     * asettaa jo liputettuun tai käännettyyn ruutuun. Oikeellisuus voidaan
     * testata onKaannetty() ja lippuVoidaanVaihtaa()-metodeilla
     */
    void asetaLippu() {
        if (onLiputettu()) {
            throw new Error("Ruutu:asetaLippu, Ruutuun on jo asetettu lippu!");
        } else if (onKaannetty()) {
            throw new Error("Ruutu:asetaLippu, Käännettyyn ruutuun ei voi asettaa lippua!");
        }
        ruutuLiputettu.set(true);
    }

    /**
     * poistaLippu() ottaa poistaa lipun ruudusta. Nakkaa virheen jos lippu
     * yritetään poistaa liputtamattomasta tai käännetystä ruudusta.
     * Oikeellisuus voidaan testata onKaannetty() ja
     * lippuVoidaanVaihtaa()-metodeilla
     */
    void poistaLippu() {
        if (!onLiputettu()) {
            throw new Error("Ruutu:poistaLippu, Liputtamattomasta ruudusta ei voida poistaa lippua!");
        } else if (onKaannetty()) {
            throw new Error("Ruutu:poistaLippu, Käännetystä ruudusta ei voida poistaa lippua!");
        }
        ruutuLiputettu.set(false);
    }

    /**
     * ruutuLiputettu() palauttaa totuusarvona tiedon siitä onko ruutu
     * liputettu.
     *
     * @return onko ruutu liputettu
     */
    public boolean onLiputettu() {
        return ruutuLiputettu.get();
    }

    /**
     * ruutuLiputettuProp() palauttaa luettavan propertyn, millä pystytään
     * seuraamaan ruudun liputuksen tilaa.
     *
     * @return ruudun liputuksen tilan property
     */
    public ReadOnlyBooleanProperty ruutuLiputettuProp() {
        return ruutuLiputettu;
    }

    /**
     * onKaannettavissa() palauttaa ruudun tiedon siitä onko ruutu
     * käännettävissä.
     *
     * @return ruudun käännettävyyden tila
     */
    public boolean onKaannettavissa() {
        return !onLiputettu() && !onKaannetty();
    }

    /**
     * lippuVoidaanVaihtaa() palauttaa tiedon siitä pysytäänkö lippu asettamaan
     * tai poistamaan.
     *
     * @return voidaanko lippu vaihtaa
     */
    boolean lippuVoidaanVaihtaa() {
        return !onKaannetty();
    }

    /**
     * miinojaNaapurissa() palauttaa tiedon onko naapuriruuduissa yhtään miinaa.
     *
     * @return onko naapurissa miinoja
     */
    boolean miinojaNaapurissa() {
        return naapuriMiinojenLkm > 0;
    }
}
