package miinaharava.mallit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Lauta kuvaa Miinaharava-pelin lautaa/kenttää, missä klikattavat ruudut
 * sijaitsevat. Lauta ei itse sisällä pelin ominaisuuksia, vaan ne tapahtuvat
 * Peli-luokassa.
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Laudan perustoiminnallisuus ja rakenne toteutettu.
 * @version 1.1.0 Refaktoroi pelilogiikka omaan luokkaan ja säilytä laudalla
 * vain sen generoimiseen tarvittava tieto.
 * @version 1.2.0 Lisää apufunktioita tiettyjen ruutujen hakemiseksi.
 */
public class Lauta {

    // Laudan attribuutit
    private int rivienLkm = 0;
    private int sarakkeidenLkm = 0;
    private int miinojenLkm = 0;

    // Ruutusäiliö
    final List<Ruutu> ruudut = new ArrayList<>();

    /**
     * rivimaara() palauttaa laudan rivien lukumäärän.
     *
     * @return laudan rivien lukumäärä.
     */
    public final int rivimaara() {
        return rivienLkm;
    }

    /**
     * sarakemaara() palauttaa laudan sarakkeiden lukumäärän.
     *
     * @return laudan sarakkeiden lukumäärä.
     */
    public final int sarakemaara() {
        return sarakkeidenLkm;
    }

    /**
     * ruutumaara() palauttaa laudan ruutujen lukumäärän.
     *
     * @return laudan ruutujen lukumäärä.
     */
    public final int ruutumaara() {
        return rivienLkm * sarakkeidenLkm;
    }

    /**
     * haeRuudut() palauttaa laudan ruudut muuttumattomana listana.
     *
     * @return ruudut muuttumattomana listana.
     */
    public final List<Ruutu> haeRuudut() {
        return Collections.unmodifiableList(ruudut);
    }

    /**
     * alusta() alustaa uuden laudan annettujen parametrien pohjalta.
     *
     * @param rivienLkm laudan rivien lukumäärä
     * @param sarakkeidenLkm laudan sarakkeiden lukumäärä
     * @param miinojenLkm miinojen lukumäärä
     */
    final void alusta(int rivienLkm, int sarakkeidenLkm, int miinojenLkm) {
        // Alustetaan kentän attribuutit.
        this.rivienLkm = rivienLkm;
        this.sarakkeidenLkm = sarakkeidenLkm;
        this.miinojenLkm = miinojenLkm;

        // Varmistetaan että ruudut ovat tyhjät.
        ruudut.clear();

        // Nämä kaikki pitää tehdä erikseen ja järjestyksessä että ruutujen tila
        // alustuu oikein.
        alustaRuudut();
        asetaMiinat();
        laskeViereistenMiinojenMaara();
    }

    /**
     * alustaRuudut() alustaa laudan ruudut uutta peliä varten.
     */
    private void alustaRuudut() {
        for (int iRuutu = 0; iRuutu < ruutumaara(); iRuutu++) {
            Ruutu uusiRuutu = new Ruutu();
            ruudut.add(uusiRuutu);
        }
    }

    /**
     * asetaMiinat() arpoo ja asettaa miinat pelilaudalle uniikkeihin ruutuihin.
     */
    private void asetaMiinat() {
        // Generoidaan seedi systeemin ajasta uudelleenpelattavuuden
        // lisäämiseksi.
        Random ruutuarpoja = new Random(System.currentTimeMillis());

        // Asetetaan miinat arpomalla satunnainen ruutu, kunnes miinat on saatu
        // asetettua MIINOJEN_LKM ruutuun.
        for (int miinojaAsetettavana = miinojenLkm; miinojaAsetettavana > 0;) {

            // Arvotaan satunnainen ruutu.
            int ruutuIndeksi = ruutuarpoja.nextInt(ruutumaara());
            Ruutu arvottuRuutu = haeRuutu(ruutuIndeksi);

            // Asetetaan miina vain ruutuihin missä sitä ei jo ole.
            if (!arvottuRuutu.onMiinoitettu()) {
                arvottuRuutu.asetaMiina();
                miinojaAsetettavana--;
            }
        }
    }

    /**
     * laskeViereistenMiinojenMaara() laskee jokaiselle ruudulle sen
     * naapuriruutujen miinojen lukumäärän, ja tallettaa sen ruutuun ylös.
     */
    private void laskeViereistenMiinojenMaara() {

        // Iteroidaan kaikkien ruutujen läpi.
        for (int iRivi = 0; iRivi < rivienLkm; iRivi++) {
            for (int iSarake = 0; iSarake < sarakkeidenLkm; iSarake++) {

                int miinojaNaapuriRuudussa = 0;

                // Haetaan naapuriruudut.
                Ruutu naapurit[] = {
                    haeRuutu(iRivi - 1, iSarake - 1), // ylävasen
                    haeRuutu(iRivi - 1, iSarake), // yläkeski
                    haeRuutu(iRivi - 1, iSarake + 1), // yläoikea
                    haeRuutu(iRivi, iSarake - 1), // vasen
                    haeRuutu(iRivi, iSarake + 1), // oikea
                    haeRuutu(iRivi + 1, iSarake - 1), // alavasen
                    haeRuutu(iRivi + 1, iSarake), // alakeski
                    haeRuutu(iRivi + 1, iSarake + 1) // alaoikea
                };

                // Lasketaan nappuriruutujen miinat.
                for (Ruutu naapuri : naapurit) {
                    if (naapuri != null && naapuri.onMiinoitettu()) {
                        miinojaNaapuriRuudussa += 1;
                    }
                }

                // Otetaan miinojen lukumäärä ylös.
                haeRuutu(iRivi, iSarake)
                        .asetaNaapuriMiinojenLkm(miinojaNaapuriRuudussa);
            }
        }
    }

    /**
     * haeRuutu() hakee ja palauttaa ruudun annetun indeksin perusteella. Jos
     * ruutu ei sijaitse laudalla, palautetaan null.
     *
     * @param indeksi haettavan ruudun indeksi
     * @return ruutu annetusta indeksistä
     */
    final Ruutu haeRuutu(int indeksi) {
        // Palautetaan null jos haetaan laudan ulkopuolelta.
        if (!(indeksi >= 0 && indeksi < ruutumaara())) {
            return null;
        }

        return ruudut.get(indeksi);
    }

    /**
     * haeRuutu() hakee ja palauttaa ruudun annetun rivi- ja sarakeindeksin
     * perusteella. Jos ruutu ei sijaitse laudalla, palautetaan null.
     *
     * @param rivinIndeksi haettavan ruudun sarakeindeksi
     * @param sarakkeenIndeksi haettavan ruudun rivi-indeksi
     * @return ruutu annetusta rivi- ja sarakeindeksistä
     */
    final Ruutu haeRuutu(int rivinIndeksi, int sarakkeenIndeksi) {
        if (rivinIndeksi >= rivienLkm
                || sarakkeenIndeksi >= sarakkeidenLkm
                || rivinIndeksi < 0
                || sarakkeenIndeksi < 0) {
            return null;
        }

        int indeksi = sarakkeidenLkm * rivinIndeksi + sarakkeenIndeksi;
        return haeRuutu(indeksi);
    }

    /**
     * kaantamattomatMiinallisetRuudut() hakee ja palauttaa kaikki laudan
     * kääntämättömät, miinalliset ruudut.
     *
     * @return kääntämättömät, miinalliset ruudut.
     */
    final List<Ruutu> kaantamattomatMiinallisetRuudut() {
        return ruudut
                .stream()
                .filter((ruutu) -> ruutu.onMiinoitettu() && !ruutu.onKaannetty())
                .collect(Collectors.toList());
    }

    /**
     * kaannetytMiinattomatRuudut() hakee ja palauttaa kaikki laudan käännetyt,
     * miinattomat ruudut.
     *
     * @return käännetyt, miinattomat ruudut.
     */
    final List<Ruutu> kaannetytMiinattomatRuudut() {
        return ruudut
                .stream()
                .filter(ruutu -> !ruutu.onMiinoitettu() && ruutu.onKaannetty())
                .collect(Collectors.toList());
    }
}
