import io.github.some_example_name.Collision;
import org.junit.jupiter.api.Assertions;import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CollisionUtilsTest {

    @Test
    void nessunContatto_separatiSullX() {
        Assertions.assertFalse(Collision.collisione(0, 0, 10, 10,   20, 0, 10, 10));
    }

    @Test
    void nessunContatto_separatiSullaY() {
        Assertions.assertFalse(Collision.collisione(0, 0, 10, 10,   0, 20, 10, 10));
    }

    @Test
    void sovrapposizione_parzialeSullX() {
        Assertions.assertTrue(Collision.collisione(0, 0, 15, 10,   10, 0, 15, 10));
    }

    @Test
    void sovrapposizione_unoContenutoNellAltro() {
        Assertions.assertTrue(Collision.collisione(0, 0, 100, 100,   20, 20, 10, 10));
    }

    @Test
    void bordoEsatto_destraConSinistra_nonCollide() {
        Assertions.assertFalse(Collision.collisione(0, 0, 10, 10,   10, 0, 10, 10));
    }

    @Test
    void bordoEsatto_altoConBasso_nonCollide() {
        Assertions.assertFalse(Collision.collisione(0, 0, 10, 10,   0, 10, 10, 10));
    }
}
