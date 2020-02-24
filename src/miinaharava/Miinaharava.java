/**
 * Pelin assetit/grafiikat: https://itch.io
 * Apuja: https://en.wikipedia.org/wiki/Microsoft_Minesweeper
 */
package miinaharava;

import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import miinaharava.gui.*;
import miinaharava.mallit.*;

public class Miinaharava extends Application {

    private final Peli peli = new Peli(Vakiot.VAIKEUSTASOT[0]);
    private AjastinText ajastinTeksti;
    private LautaNakyma ruudukko;

    private void asetaVaikeustaso(Vaikeustaso vaikeustaso) {
        peli.asetaVaikeustaso(vaikeustaso);
        nollaaTila();
    }

    /**
     * paivitaRuudukko() päivittää pelin laudan ruudukon sen ruutumallin
     * perusteella.
     */
    private void paivitaRuudukko() {
        // Tyhjennetään ruudukko
        ruudukko.tyhjenna();

        Lauta lauta = peli.haeLauta();

        int rivi = 0;
        int sarake = 0;

        for (Ruutu ruutu : lauta.haeRuudut()) {
            ruudukko.lisaaRuutu(ruutu, rivi, sarake);
            sarake += 1;
            if (sarake == lauta.sarakemaara()) {
                rivi += 1;
                sarake = 0;
            }
        }
    }

    @Override
    public void start(Stage ikkuna) {

        VBox asettelu = new VBox();
        ValikkoMenuBar valikko = alustaValikko();
        peli.peliVoitettuProp().addListener((o, v, onkoVoitettu) -> {

            if (!onkoVoitettu) {
                return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Voitit pelin! Haluatko pelata uudestaan?", ButtonType.YES, ButtonType.NO);
        });

        peli.peliHavittyProp().addListener((o, v, onkoHavitty) -> {

            if (!onkoHavitty) {
                return;
            }
            ajastinTeksti.pysayta();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Hävisit pelin! :( Haluatko pelata uudestaan?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> valinta = alert.showAndWait();

            if (valinta.isPresent() && valinta.get() == ButtonType.YES) {
                nollaaTila();
            }
        });

        // Luodaan tässä pelin ylävalikko ja alustetaan sen toiminnot
        // Luodaan pelin yläpalkki
        HBox ylapalkki = new HBox(20);

        // Luodaan ajastinteksti
        ajastinTeksti = new AjastinText();
        ajastinTeksti.aloita();

        // Luodaan miinateksti
        MiinaText miinaTeksti = new MiinaText(Vakiot.MIINA_KUVA);

        // Päivitetään teksti miinojen määrän muuttuessa.
        peli.miinojaJaljellaProp()
                .addListener((o, v, miinojaJaljella) -> {
                    miinaTeksti.asetaMiinojenMaara((int) miinojaJaljella);
                });

        ylapalkki.getChildren().addAll(ajastinTeksti, miinaTeksti);
        ylapalkki.setAlignment(Pos.CENTER);

        ruudukko = new LautaNakyma(peli);

        HBox ruudukkoAsettelu = new HBox(ruudukko);
        ruudukkoAsettelu.setAlignment(Pos.CENTER);
        paivitaRuudukko();

        asettelu.getChildren().addAll(valikko, ylapalkki, ruudukkoAsettelu);
        asettelu.setSpacing(20);

        Scene nakyma = new Scene(asettelu, 1000, 600);
        nakyma.setFill(Color.STEELBLUE);

        ikkuna.setScene(nakyma);
        ikkuna.setResizable(false);
        ikkuna.show();
    }

    private ValikkoMenuBar alustaValikko() {
        ValikkoMenuBar valikko = new ValikkoMenuBar();

        // Lisätään valittavat vaikeustasot.
        for (Vaikeustaso vaikeustaso : Vakiot.VAIKEUSTASOT) {
            valikko.lisaaVaikeustaso(vaikeustaso);
        }

        // Asetetaan ensimmäinen vaikeustaso valituksi.
        valikko.asetaValittuVaikeustaso(Vakiot.VAIKEUSTASOT[0]);

        // Asetetaan käsittelijä vaikeustason vaihdolle.
        valikko.asetaVaikeustasoCallback(this::asetaVaikeustaso);

        // Asetetaan käsittelijä uudelle pelille.
        valikko.asetaUusiPeliCallback((e) -> {
            nollaaTila();
        });

        return valikko;
    }

    private void nollaaTila() {
        ajastinTeksti.nollaa();
        peli.uusiPeli();
        paivitaRuudukko();
    }

    /**
     * Ohjelman käynnistymetodi
     *
     * @param argumentit
     */
    public static void main(String[] argumentit) {
        launch(argumentit);
    }

}
