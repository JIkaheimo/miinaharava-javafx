package miinaharava.gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import miinaharava.mallit.Peli;
import miinaharava.mallit.Ruutu;

/**
 * LautaNakyma sisältää Miinaharava-pelin laudan tai ruudukon graafisen
 * esityksen.
 *
 * @author Jaakko Ikäheimo
 */
public class LautaNakyma extends Group implements EventHandler<MouseEvent> {

    // Pidetään muistissa pelimalli suoritettavia interaktioita varten.
    private final Peli peliMalli;

    /**
     * LautaNakyma-luokan konstruktori
     *
     * @param peli
     */
    public LautaNakyma(Peli peli) {
        this.peliMalli = peli;
    }

    /**
     * tyhjenna() tyhjentää ruutunäkymät.
     */
    public final void tyhjenna() {
        // Poistetaan vain kaikki lapsinodet.
        getChildren().removeAll(getChildren());
    }

    /**
     * lisaaRuutu() lisää uuden ruutunäkymän lautanäkymään.
     *
     * @param ruutumalli
     * @param rivi
     * @param sarake
     */
    public final void lisaaRuutu(Ruutu ruutumalli, int rivi, int sarake) {

        // Luodaan uusi ruutunäkymä.
        RuutuNakyma ruutunakyma = new RuutuNakyma(ruutumalli);

        // Allokoidaan klikkauskäsittely tälle komponentille.
        ruutunakyma.setOnMouseClicked(this);

        // Asetetaan ruudun sijainti.
        ruutunakyma.setX(sarake * 30 + 5);
        ruutunakyma.setY(rivi * 30 + 5);

        // Lisätään ruutunäkymä komponentin lapseksi.
        getChildren().add(ruutunakyma);
    }

    /**
     * Käsittelijä ruutunäkymissä tapahtuville klikkaustapahtumille.
     *
     * @param event metadaa klikkaustapahtumasta.
     */
    @Override
    public final void handle(MouseEvent event) {
        // Haetaan klikattu nappi.
        MouseButton painettuNappi = event.getButton();

        // Haetaan klikatun ruudun malli
        RuutuNakyma klikattuRuutu = (RuutuNakyma) event.getSource();
        Ruutu ruutuMalli = klikattuRuutu.haeMalli();

        // Oikealla hiirellä vaihdetaan lipun tilaa ruudussa.
        if (painettuNappi == MouseButton.SECONDARY) {
            peliMalli.vaihdaLippuaRuudussa(ruutuMalli);

        } // Vasemmalla hiirellä käännetään ruutu.
        else if (painettuNappi == MouseButton.PRIMARY) {
            peliMalli.kaannaRuutu(ruutuMalli);
        }
    }
}
