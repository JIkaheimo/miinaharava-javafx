package miinaharava.gui;

import javafx.scene.image.ImageView;
import miinaharava.Vakiot;
import miinaharava.gui.animaatiot.RuutuHaivytys;
import miinaharava.kontrollerit.RuutuKontrolleri;
import miinaharava.mallit.Ruutu;

/**
 * RuutuNakyma toimii yhden Miinaharava-pelin ruudun näkymänä. Ruutua vasemmalla
 * hiirellää klikkaamalla se pystytään kääntämään. Oikealla hiirellä pystytään
 * asettamaan tai poistamaan lippu ruudusta.
 *
 * @author Jaakko Ikäheimo
 *
 * @version 1.0.0 Komponenti perustoiminnallisuus ja rakenne valmis.
 * @version 1.1.0 Allokoi mallin ja näkymän hallinta kontrollerille.
 * @version 1.2.0 Lisää animaatio ruudun kuvan/tilan muutokselle.
 */
public class RuutuNakyma extends ImageView {

    private final Ruutu ruutuMalli;
    private final RuutuKontrolleri kontrolleri;
    private final RuutuHaivytys animaatio = new RuutuHaivytys(this);

    public RuutuNakyma(Ruutu ruutuMalli) {
        // Asetetaan "tiili" oletuskuvaksi.
        super(Vakiot.TIILI_KUVA);

        this.ruutuMalli = ruutuMalli;

        // Allokoidaan kontrollerille näkymän päivitys mallin muutosten perusteella.
        kontrolleri = new RuutuKontrolleri(ruutuMalli, this);
        kontrolleri.alusta();

        // Näytetään häivytysanimaatio kun kuva vaihtuu,.
        imageProperty().addListener((observable) -> {
            animaatio.suorita();
        });
    }

    public final Ruutu haeMalli() {
        return ruutuMalli;
    }
}
