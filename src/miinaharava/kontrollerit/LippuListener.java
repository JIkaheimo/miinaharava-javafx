package miinaharava.kontrollerit;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import miinaharava.Vakiot;
import miinaharava.gui.RuutuImageView;

/**
 * LippuListener hoitaa ruudun kuvan päivittämisen sen tilan perusteella.
 *
 * @author Jaakko Ikäheimo
 */
public class LippuListener implements ChangeListener<Boolean> {

    private final RuutuImageView ruutu;

    /**
     * LippuListener-konstruktori
     *
     * @param ruutu ruudun graafinen elementti
     * @param onkoLiputettu
     */
    public LippuListener(RuutuImageView ruutu, ReadOnlyBooleanProperty onkoLiputettu) {
        this.ruutu = ruutu;
        onkoLiputettu.addListener(this);
    }

    /**
     * Päivittää ruudun kuvan sen perusteella onko se liptutettu vai ei.
     *
     * @param o
     * @param olikoLiputettu
     * @param onkoLiputettu
     */
    @Override
    public void changed(ObservableValue<? extends Boolean> o, Boolean olikoLiputettu, Boolean onkoLiputettu) {
        if (onkoLiputettu) {
            this.ruutu.setImage(Vakiot.LIPPU_KUVA);
        } else {
            this.ruutu.setImage(Vakiot.TIILI_KUVA);
        }
    }

}
