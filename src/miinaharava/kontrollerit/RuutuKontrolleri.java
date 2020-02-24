package miinaharava.kontrollerit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import miinaharava.Vakiot;
import miinaharava.gui.RuutuNakyma;
import miinaharava.mallit.Ruutu;

/**
 * RuutuKontrolleri hoitaa kommunikoinnin Miinaharava-pelin ruudun mallin ja
 * käyttöliittymäkomponentin välillä.
 *
 * @author Jaakko Ikäheimo
 */
public class RuutuKontrolleri implements ChangeListener<Boolean> {

    /**
     * Attribuutit
     */
    private final Ruutu ruutuMalli;
    private final RuutuNakyma ruutuNakyma;

    /**
     * RuutuKontrolleri-luokan konstruktori, missä yhdistetään ruudun malli ja
     * näkymä.
     *
     * @param ruutuMalli
     * @param ruutuNakyma
     */
    public RuutuKontrolleri(Ruutu ruutuMalli, RuutuNakyma ruutuNakyma) {
        this.ruutuMalli = ruutuMalli;
        this.ruutuNakyma = ruutuNakyma;
    }

    /**
     * alusta() alustaa ruutumallin propertyjen listenerit.
     */
    public void alusta() {
        ruutuMalli.ruutuKaannettyProp().addListener(this);
        ruutuMalli.ruutuLiputettuProp().addListener(this);
    }

    /**
     *
     * @param observable
     * @param vanhaTila
     * @param uusiTila
     */
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean vanhaTila, Boolean uusiTila) {

        if (observable.equals(ruutuMalli.ruutuKaannettyProp())) {
            // ruutumallin kääntötila muuttunut
            paivitaKaanto(uusiTila);

        } else if (observable.equals(ruutuMalli.ruutuLiputettuProp())) {
            // ruutumallin lipputila muuttunut
            paivitaLippu(uusiTila);
        }
    }

    /**
     * paivitaKaanto() päivittää ruudun näkymän kuvan ruudun käännön ja tilan
     * perusteella.
     *
     * @param onkoKaannetty
     */
    private void paivitaKaanto(boolean onkoKaannetty) {
        if (onkoKaannetty) {
            if (!ruutuMalli.onMiinoitettu()) {
                ruutuNakyma.setImage(Vakiot.NUMERO_KUVAT[ruutuMalli.naapuriMiinojenLkm()]);
            } else {
                ruutuNakyma.setImage(Vakiot.MIINA_KUVA);
            }
        } else {
            ruutuNakyma.setImage(Vakiot.TIILI_KUVA);
        }
    }

    /**
     * paivitaLippu() päivittää ruudun näkymän kuvan lipun asetuksen
     * perusteella.
     *
     * @param onkoLiputettu
     */
    private void paivitaLippu(boolean onkoLiputettu) {
        if (onkoLiputettu) {
            ruutuNakyma.setImage(Vakiot.LIPPU_KUVA);
        } else {
            ruutuNakyma.setImage(Vakiot.TIILI_KUVA);
        }
    }
}
