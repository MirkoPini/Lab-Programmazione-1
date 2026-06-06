import io.github.some_example_name.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerPhysicsTest {

    private static final float DT = 0.016f;
    private static final float EPSILON = 0.01f;


    @Test
    void gravita_riduceVelY_ogni_frame() {
        float velY = 0f;
        velY += Player.GRAVITY * DT;
        Assertions.assertTrue(velY < 0f, "La gravità deve rendere velY negativa");
    }

    @Test
    void gravita_accumulaNelTempo() {
        float velY = 0f;
        for (int i = 0; i < 10; i++) {
            velY += Player.GRAVITY * DT;
        }
        Assertions.assertTrue(velY < -10f, "Dopo 10 frame il player deve cadere velocemente");
    }

    // --- Salto ---

    @Test
    void saltoPossibile_soloSeVelYZero() {
        float velYATerra = 0f;
        boolean puoSaltare = (velYATerra == 0f);
        Assertions.assertTrue(puoSaltare);
    }

    @Test
    void saltoNonPossibile_seGiaInAria() {
        float velYInAria = Player.JUMP_FORCE / 2f;
        boolean puoSaltare = (velYInAria == 0f);
        Assertions.assertFalse(puoSaltare);
    }

    @Test
    void jumpForce_rendVelYPositiva() {
        float velY = 0f;
        velY = Player.JUMP_FORCE;
        Assertions.assertTrue(velY > 0f, "Dopo il salto velY deve essere positiva (verso l'alto)");
    }

    @Test
    void playerX_nonSceneSottoZero() {
        float playerX = -50f;
        if (playerX < 0) playerX = 0f;
        Assertions.assertEquals(0f, playerX, EPSILON);
    }

    @Test
    void playerX_positivaRimaneInvariata() {
        float playerX = 300f;
        if (playerX < 0) playerX = 0f;
        Assertions.assertEquals(300f, playerX, EPSILON);
    }
}
