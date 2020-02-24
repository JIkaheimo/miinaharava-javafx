package miinaharava.gui;

import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import miinaharava.Vakiot;
import miinaharava.kontrollerit.RuutuKontrolleri;
import miinaharava.mallit.Ruutu;

/**
 *
 * @author Jaakko Ikäheimo
 */
public class RuutuNakyma extends ImageView {

    public Ruutu ruutuMalli;
    private final RuutuKontrolleri kontrolleri;
    private RuutuSiirtyma ruutuSiirtyma = new RuutuSiirtyma(this);

    public RuutuNakyma(Ruutu ruutuMalli) {
        super(Vakiot.TIILI_KUVA);
        this.ruutuMalli = ruutuMalli;

        setFitWidth(30);
        setFitHeight(30);

        kontrolleri = new RuutuKontrolleri(ruutuMalli, this);
        kontrolleri.alusta();

        imageProperty().addListener((observable) -> {
            ruutuSiirtyma.siirry();
        });
    }
}

class RuutuSiirtyma {

    private final FadeTransition siirtyma;

    public RuutuSiirtyma(RuutuNakyma ruutuNakyma) {
        siirtyma = new FadeTransition();
        siirtyma.setNode(ruutuNakyma);
        siirtyma.setDuration(new Duration(200));
        siirtyma.setCycleCount(1);
        siirtyma.setFromValue(1);
        siirtyma.setToValue(0.7);
    }

    public void siirry() {
        siirtyma.play();
    }
}
