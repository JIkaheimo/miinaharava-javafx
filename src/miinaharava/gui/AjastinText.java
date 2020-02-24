package miinaharava.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * AjastinText-komponentti päivittää itsensä näyttämään sekunteja.
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Komponentin perusominaisuudet toteutettu.
 * @version 1.1.0 Lisää oma metodi tekstin päivittämiselle ja kutsu sitä nyt
 * myös konstruktorissa ja nollatessa.
 */
public class AjastinText extends Label {

    // Vakio ajastusvali millisekunteina.
    private final int AJASTUS_VALI = 1000;

    private final Timeline ajastin;
    private float sekunnit = 0;

    public AjastinText() {
        setFont(Font.font("Verdana", 22));

        // Alustetaan Timeline päivittämään labelin tekstiä sekunnin välein.
        ajastin = new Timeline(new KeyFrame(Duration.millis(AJASTUS_VALI), event -> {
            sekunnit += AJASTUS_VALI / 1000;
            paivitaTeksti();
        }));

        // Pyöritetään Timelinea loputtomiin.
        ajastin.setCycleCount(Timeline.INDEFINITE);

        paivitaTeksti();
    }

    /**
     * Aloittaa komponentin ajastimen ja päivityksen.
     */
    public void aloita() {
        ajastin.play();
    }

    /**
     * Nollaa komponentin "ajastimen".
     */
    public void nollaa() {
        sekunnit = 0;
        paivitaTeksti();
    }

    /**
     * Pysäyttää komponentin ajastimen.
     */
    public void pysayta() {
        ajastin.stop();
    }

    /**
     * Pysäyttää ja nollaa komponentin ajastimen.
     */
    public void pysaytaJaNollaa() {
        pysayta();
        nollaa();
    }

    /**
     * Palauttaa ajastimen suorittamat sekunnit.
     *
     * @return kuluneet sekunnit
     */
    public int haeSekunnit() {
        return (int) sekunnit;
    }

    /**
     * Muuntaa sekunnit merkkijonoesitysmuotoon.
     *
     * @param formatoitavatSekunnit
     * @return sekunnit formatoituna muodossa mm:ss
     */
    private String formatoiSekunnit(float formatoitavatSekunnit) {

        int skaalatutMinuutit = (int) formatoitavatSekunnit / 60;
        int skaalatutSekunnit = (int) formatoitavatSekunnit % 60;

        return String.format("%02d:%02d", skaalatutMinuutit, skaalatutSekunnit);
    }

    /**
     * Päivittää labelin tekstin näyttämään sekunnit.
     */
    private void paivitaTeksti() {
        setText(formatoiSekunnit(sekunnit));
    }
}
