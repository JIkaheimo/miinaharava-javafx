package miinaharava;

import javafx.scene.image.Image;
import miinaharava.mallit.Vaikeustaso;

/**
 * Miinaharava-pelin vakiot koottuna yhteen paikkaan.
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Vaikeustasot ja tiilikuvat lisätty.
 * @version 1.1.0 Loput kuvat lisätty.
 */
public abstract class Vakiot {

    private final static String ASSET_PATH = "/assets/";

    public final static Image MIINA_KUVA
            = new Image(ASSET_PATH + "bomb.png");

    public final static Image TIILI_KUVA
            = new Image(ASSET_PATH + "tile.png");

    public final static Image LIPPU_KUVA
            = new Image("/assets/flag.png");

    public static final Image NUMERO_KUVAT[] = {
        new Image(ASSET_PATH + "0.png"),
        new Image(ASSET_PATH + "1.png"),
        new Image(ASSET_PATH + "2.png"),
        new Image(ASSET_PATH + "3.png"),
        new Image(ASSET_PATH + "4.png"),
        new Image(ASSET_PATH + "5.png"),
        new Image(ASSET_PATH + "6.png"),
        new Image(ASSET_PATH + "7.png"),
        new Image(ASSET_PATH + "8.png"),};

    public final static Vaikeustaso VAIKEUSTASOT[] = {
        new Vaikeustaso("Aloittelija", 9, 10),
        new Vaikeustaso("Haastava", 16, 40),
        new Vaikeustaso("Ammattilainen", 16, 30, 99)
    };

}
