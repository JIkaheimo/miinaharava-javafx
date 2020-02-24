package miinaharava.gui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import miinaharava.mallit.Peli;
import miinaharava.mallit.Ruutu;

/**
 * RuudukkoGroup sisältää Miinaharava-pelin laudan tai ruudukon graafisen
 * esityksen.
 *
 * @author J4sK4
 */
public class RuudukkoGroup extends Group {

    private final Peli peli;

    public RuudukkoGroup(Peli peli) {
        this.peli = peli;
    }

    /**
     * tyhjenna() tyhjentää pelin graafisen ruudukon.
     */
    public void tyhjenna() {
        getChildren().removeAll(getChildren());
    }

    public void lisaaRuutu(Ruutu ruutu, int rivi, int sarake) {
        RuutuImageView ruutuView = new RuutuImageView(ruutu);
        addRuutuClickHandler(ruutuView, ruutu);
        ruutuView.setX(sarake * 30 + 5);
        ruutuView.setY(rivi * 30 + 5);
        getChildren().add(ruutuView);
    }

    private void addRuutuClickHandler(Node ruutuNode, Ruutu ruutuMalli) {
        ruutuNode.setOnMouseClicked((event) -> {
            MouseButton painettuNappi = event.getButton();

            // Hiiren oikealla napilla pystytään asettamaan/poistamaan lippu.
            if (painettuNappi == MouseButton.SECONDARY) {
                peli.vaihdaLippuaRuudussa(ruutuMalli);
            } else if (painettuNappi == MouseButton.PRIMARY) {
                peli.kaannaRuutu(ruutuMalli);
            }
        });
    }
}
