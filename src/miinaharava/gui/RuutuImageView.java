package miinaharava.gui;

import javafx.scene.image.ImageView;
import miinaharava.Vakiot;
import miinaharava.kontrollerit.LippuListener;
import miinaharava.mallit.Ruutu;

/**
 *
 * @author Jaakko Ikäheimo
 */
public class RuutuImageView extends ImageView {

    private LippuListener lippuListener;

    public RuutuImageView(Ruutu ruutu) {
        super(Vakiot.TIILI_KUVA);
        setFitWidth(30);
        setFitHeight(30);

        // Päivitetään ruudun kuva kääntäessä.
        ruutu.ruutuKaannettyProp().addListener((o, v, onKaannetty) -> {
            if (onKaannetty) {
                if (!ruutu.onMiinoitettu()) {
                    this.setImage(Vakiot.NUMERO_KUVAT[ruutu.naapuriMiinojenLkm()]);
                    this.setOpacity(0.7);
                } else {
                    this.setImage(Vakiot.MIINA_KUVA);
                }

            } else {
                this.setImage(Vakiot.TIILI_KUVA);
            }
        });

        // Päivitetään lipun kuva asettaessa.
        lippuListener = new LippuListener(this, ruutu.ruutuLiputettuProp());
    }
}
