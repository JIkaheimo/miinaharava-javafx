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

    private final Peli peliMalli;

    public LautaNakyma(Peli peli) {
        this.peliMalli = peli;
    }

    /**
     * tyhjenna() tyhjentää pelin graafisen ruudukon.
     */
    public void tyhjenna() {
        // Poistetaan vaan kaikki lapsinodet.
        getChildren().removeAll(getChildren());
    }

    /**
     * lisaaRuutu() lisää uuden ruutunäkymän
     *
     * @param ruutu
     * @param rivi
     * @param sarake
     */
    public void lisaaRuutu(Ruutu ruutu, int rivi, int sarake) {
        RuutuNakyma ruutuNakyma = new RuutuNakyma(ruutu);
        ruutuNakyma.setOnMouseClicked(this);
        ruutuNakyma.setX(sarake * 30 + 5);
        ruutuNakyma.setY(rivi * 30 + 5);

        getChildren().add(ruutuNakyma);
    }

    /**
     * Tällä hoidetaan ruutumallien päivitys klikatun ruudun perusteella.
     *
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        MouseButton painettuNappi = event.getButton();

        // Haetaan klikatun ruudun malli
        RuutuNakyma klikattuRuutu = (RuutuNakyma) event.getSource();
        Ruutu ruutuMalli = klikattuRuutu.ruutuMalli;

        if (painettuNappi == MouseButton.SECONDARY) {
            peliMalli.vaihdaLippuaRuudussa(ruutuMalli);

        } else if (painettuNappi == MouseButton.PRIMARY) {
            peliMalli.kaannaRuutu(ruutuMalli);
        }
    }
}
