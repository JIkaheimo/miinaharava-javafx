/**
 * Pelin assetit/grafiikat: https://itch.io
 */
package miinaharava;

import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class Miinaharava extends Application implements ChangeListener<Boolean> {

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
        Lauta lauta = peli.haeLauta();

        int rivi = 0;
        int sarake = 0;

        // Tyhjennetään ruudukko.
        ruudukko.tyhjenna();

        // Lisätään ruudut ruudukkoon.
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

        // Luodaan tässä pelin ylävalikko ja alustetaan sen toiminnot
        // Luodaan pelin yläpalkki
        HBox ylapalkki = new HBox(20);

        // Luodaan ajastinteksti
        ajastinTeksti = new AjastinText();
        ajastinTeksti.aloita();

        // Luodaan miinateksti
        MiinaLaskuriText miinaTeksti = new MiinaLaskuriText(Vakiot.MIINA_KUVA);

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

        asettelu.getChildren().addAll(valikko, ylapalkki, ruudukkoAsettelu);
        asettelu.setSpacing(20);

        Scene nakyma = new Scene(asettelu, 1000, 600);
        nakyma.setFill(Color.STEELBLUE);

        peli.peliVoitettuProp().addListener(this);
        peli.peliHavittyProp().addListener(this);

        nollaaTila();

        ikkuna.setScene(nakyma);
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
        ajastinTeksti.aloita();
    }

    /**
     * Hoidetaan pelin voitto- ja häviötila.
     *
     * @param seurattu
     * @param vanhaTila
     * @param uusiTila
     */
    @Override
    public void changed(ObservableValue<? extends Boolean> seurattu, Boolean vanhaTila, Boolean uusiTila) {
        Alert ilmoitus;

        if (!uusiTila) {
            return;
        }

        if (seurattu.equals(peli.peliVoitettuProp())) {
            ilmoitus = new Alert(Alert.AlertType.INFORMATION, "Voitit pelin! Haluatko pelata uudestaan?", ButtonType.YES, ButtonType.NO);
        } else if (seurattu.equals(peli.peliHavittyProp())) {
            ilmoitus = new HavioAlert();
        } else {
            throw new Error("Jokin meni vikaan...");
        }

        ajastinTeksti.pysayta();
        Optional<ButtonType> valinta = ilmoitus.showAndWait();

        if (valinta.isPresent() && valinta.get() == ButtonType.YES) {
            nollaaTila();
        }
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

class HavioAlert extends Alert {

    public HavioAlert() {
        super(AlertType.CONFIRMATION);
        setHeaderText("Hävisit pelin!");
        setContentText("Haluatko pelata uudestaan?");
        getButtonTypes().clear();
        getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
    }
}

class VoittoAlert extends Alert {

    public VoittoAlert() {
        super(AlertType.CONFIRMATION);
        setHeaderText("Voitit pelin!");
        setContentText("Haluatko pelata uudestaan?");
        getButtonTypes().clear();
        getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
    }

}
