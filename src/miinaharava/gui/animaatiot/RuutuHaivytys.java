package miinaharava.gui.animaatiot;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import miinaharava.gui.RuutuNakyma;

/**
 * RuutuHaivytys suorittaa animaation ruudun sisään- ja uloshäivyttämiseksi
 * vuorotellen.
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Luokan perustoiminnallisuus ja rakenne valmis.
 * @version 1.1.0 Lisää ominaisuus animaation vuorottaiseen vaihteluun.
 */
public class RuutuHaivytys {

    public static final double ALKULAPINAKYVYYS = 1;
    public static final double LOPPULAPINAKYVYYS = 0.8;

    private boolean onkoHaihtuva = false;
    private final FadeTransition siirtyma;

    /**
     * RuutuHaivytys lisää annettuun ruutunäkymään häivytysanimaation, mitä
     * pystytään kutsumaan suorita()-metodin avulla.
     *
     * @param ruutuNakyma
     */
    public RuutuHaivytys(RuutuNakyma ruutuNakyma) {
        siirtyma = new FadeTransition();
        siirtyma.setNode(ruutuNakyma);

        // Alustetaan FadeTransition.
        siirtyma.setDuration(new Duration(400));
        siirtyma.setCycleCount(1);
        siirtyma.setFromValue(ALKULAPINAKYVYYS);
        siirtyma.setToValue(LOPPULAPINAKYVYYS);

        // Vaihdetaan häivytysanimaation tyyliä aina sen jälkeen.
        siirtyma.setOnFinished((event) -> {

            if (onkoHaihtuva) {
                siirtyma.setFromValue(ALKULAPINAKYVYYS);
                siirtyma.setToValue(LOPPULAPINAKYVYYS);
            } else {
                siirtyma.setFromValue(LOPPULAPINAKYVYYS);
                siirtyma.setToValue(ALKULAPINAKYVYYS);
            }

            onkoHaihtuva = !onkoHaihtuva;
        });
    }

    public void suorita() {
        siirtyma.play();
    }
}
