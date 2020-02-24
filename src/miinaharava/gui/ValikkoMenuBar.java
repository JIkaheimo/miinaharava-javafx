package miinaharava.gui;

import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import miinaharava.mallit.Vaikeustaso;

/**
 * ValikkoMenuBar on Miinaharava-pelin ylävalikko.
 *
 * @author Jaakko Ikäheimo
 */
public class ValikkoMenuBar extends VBox {

    private final MenuBar valikkoPalkki = new MenuBar();

    private final Menu peliMenu = new Menu("Peli");
    private final MenuItem uusiPeli = new MenuItem("Uusi peli");
    private Consumer<Void> uusiPeliCallback;

    private final Menu vaikeustasoMenu = new Menu("Vaikeustasot");
    private final ToggleGroup vaikeustasoRyhma = new ToggleGroup();
    private Consumer<Vaikeustaso> vaikeustasoCallback;

    public ValikkoMenuBar() {
        super();

        // Asetataan listener seuraamaan vaikeustasovalitsinta.
        vaikeustasoRyhma.selectedToggleProperty().addListener((observable, vanhaVaikeustasoValitsin, valittuVaikeustasoValitsin) -> {
            // Haetaan valittu vaikeustaso
            Vaikeustaso valittuVaikeustaso
                    = (Vaikeustaso) valittuVaikeustasoValitsin.getUserData();

            // Kutsutaan asetettua callbackia jos sellainen on...
            if (vaikeustasoCallback != null) {
                vaikeustasoCallback.accept(valittuVaikeustaso);
            }
        });
        valikkoPalkki.getMenus().addAll(peliMenu, vaikeustasoMenu);
        peliMenu.getItems().addAll(uusiPeli);
        getChildren().add(valikkoPalkki);
    }

    public void lisaaVaikeustaso(Vaikeustaso vaikeustaso) {
        RadioMenuItem vaikeustasoValitsin = new RadioMenuItem(vaikeustaso.nimi);
        vaikeustasoValitsin.setToggleGroup(vaikeustasoRyhma);
        vaikeustasoValitsin.setUserData(vaikeustaso);
        vaikeustasoMenu.getItems().add(vaikeustasoValitsin);
    }

    public void asetaVaikeustasoCallback(Consumer<Vaikeustaso> vaikeustasoCallback) {
        this.vaikeustasoCallback = vaikeustasoCallback;
    }

    public void asetaUusiPeliCallback(EventHandler<ActionEvent> uusiPeliCallback) {
        uusiPeli.setOnAction(uusiPeliCallback);
    }

    public void asetaValittuVaikeustaso(Vaikeustaso vaikeustaso) {
        vaikeustasoRyhma.getToggles().forEach((vaikeustasoValitsin) -> {
            if (vaikeustaso == (Vaikeustaso) vaikeustasoValitsin.getUserData()) {
                vaikeustasoValitsin.setSelected(true);
            }
        });
    }

}
