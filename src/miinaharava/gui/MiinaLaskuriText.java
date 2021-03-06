package miinaharava.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * MiinaLaskuriText näyttää Miinaharava-pelin jäljellä olevien miinojen
 * lukumäärän.
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Komponentin perustoiminnallisuus ja rakenne valmis.
 *
 */
public class MiinaLaskuriText extends HBox {

    private final Label miinaMaaraTeksti;
    private final ImageView miinaKuva;

    public MiinaLaskuriText(Image ikoni) {
        super(5);

        // Alustetaan kuvakomponentti.
        miinaKuva = new ImageView(ikoni);

        // Alustetaan tekstikomponentti.
        miinaMaaraTeksti = new Label();
        miinaMaaraTeksti.setFont(Font.font("Verdana", 22));
        miinaMaaraTeksti.setPrefWidth(50);

        super.getChildren()
                .addAll(miinaKuva, miinaMaaraTeksti);
    }

    /**
     * asetaMiinojenMaara() päivittää Miinaharava-pelin jäljellä olevan miinojen
     * määrän näkyville.
     *
     * @param miinojenMaara
     */
    public void asetaMiinojenMaara(int miinojenMaara) {
        miinaMaaraTeksti.setText(String.format("%d", miinojenMaara));
    }
}
