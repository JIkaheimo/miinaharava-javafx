/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miinaharava.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *
 * @author J4sK4
 */
public class MiinaText extends HBox {

    private final Label miinaLabel;
    private final ImageView miinaImageView;

    public MiinaText(Image ikoni) {
        super(5);
        miinaImageView = new ImageView(ikoni);

        miinaLabel = new Label();
        miinaLabel.setFont(Font.font("Verdana", 22));
        miinaLabel.setPrefWidth(50);

        super.getChildren().addAll(miinaImageView, miinaLabel);
    }

    public void asetaMiinojenMaara(int miinojenMaara) {
        miinaLabel.setText(String.format("%d", miinojenMaara));
    }
}
