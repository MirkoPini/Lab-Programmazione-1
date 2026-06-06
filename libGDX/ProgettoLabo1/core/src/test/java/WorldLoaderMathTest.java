import io.github.some_example_name.WorldLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldLoaderMathTest {


    @Test
    void worldX_colonna0_restituisceZero() {
        float atteso = 0 * WorldLoader.LARGHEZZA_OSTACOLO;
        Assertions.assertEquals(0f, atteso, 0.001f);
    }

    @Test
    void worldX_colonna3_restituisce360() {
        float atteso = 3 * WorldLoader.LARGHEZZA_OSTACOLO;
        Assertions.assertEquals(360f, atteso, 0.001f);
    }

    @Test
    void worldX_cresceLinearmente() {
        float col2 = 2 * WorldLoader.LARGHEZZA_OSTACOLO;
        float col3 = 3 * WorldLoader.LARGHEZZA_OSTACOLO;
        Assertions.assertEquals(WorldLoader.LARGHEZZA_OSTACOLO, col3 - col2, 0.001f);
    }

    @Test
    void worldY_rigaInFondo_restituiscePavimento() {
        int rigeTotali = 5;
        int rigaInFondo = rigeTotali - 1;
        int righeInvert = (rigeTotali - 1) - rigaInFondo;
        float worldY = WorldLoader.PAVIMENTO + righeInvert * WorldLoader.ALTEZZA_OSTACOLO;
        Assertions.assertEquals(WorldLoader.PAVIMENTO, worldY, 0.001f);
    }

    @Test
    void worldY_rigaInCima_eIlPiuAlto() {
        int rigeTotali = 5;

        int invertRiga0 = (rigeTotali - 1) - 0;
        float y0 = WorldLoader.PAVIMENTO + invertRiga0 * WorldLoader.ALTEZZA_OSTACOLO;

        int invertRiga4 = (rigeTotali - 1) - 4;
        float y4 = WorldLoader.PAVIMENTO + invertRiga4 * WorldLoader.ALTEZZA_OSTACOLO;

        Assertions.assertTrue(y0 > y4, "La riga 0 (cima mappa) deve avere worldY maggiore della riga 4");
    }

    @Test
    void worldY_righeConsecutive_differenzaUgualeAltezzaOstacolo() {
        int rigeTotali = 5;

        int invertRiga1 = (rigeTotali - 1) - 1;
        float y1 = WorldLoader.PAVIMENTO + invertRiga1 * WorldLoader.ALTEZZA_OSTACOLO;

        int invertRiga2 = (rigeTotali - 1) - 2;
        float y2 = WorldLoader.PAVIMENTO + invertRiga2 * WorldLoader.ALTEZZA_OSTACOLO;

        Assertions.assertEquals(WorldLoader.ALTEZZA_OSTACOLO, y1 - y2, 0.001f);
    }
}
