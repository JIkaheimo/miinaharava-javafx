package miinaharava.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *
 * @author Jaakko Ikäheimo
 */
public class MiinaText extends HBox {

    private final Label miinaMaaraTeksti;
    private final ImageView miinaKuva;

    public MiinaText(Image ikoni) {
        super(5);

        miinaKuva = new ImageView(ikoni);

        miinaMaaraTeksti = new Label();
        miinaMaaraTeksti.setFont(Font.font("Verdana", 22));
        miinaMaaraTeksti.setPrefWidth(50);

        super.getChildren().addAll(miinaKuva, miinaMaaraTeksti);
    }

    public void asetaMiinojenMaara(int miinojenMaara) {
        miinaMaaraTeksti.setText(String.format("%d", miinojenMaara));
    }
}
