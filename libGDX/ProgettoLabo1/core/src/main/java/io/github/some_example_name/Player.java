package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player extends Entita{

    public static final float WIDTH = 160;
    public static final float HEIGHT = 185;
    public static final float SPEED = 400;
    public static final float GRAVITY = -2000;
    public static final float JUMP_FORCE = 900;;

    private boolean avanti = false;
    private boolean indietro = false;
    private float timerAnimazione = 0;
    private Sound footSound;
    private Sound footSound1;
    private boolean suonoCamminata;
    private boolean suonoCamminata1 = true;
    private Sound jumpSound;

    private Texture fermo;
    private Texture movimentoAvanti1;
    private Texture movimentoAvanti2;
    private Texture fermoIndietro;
    private Texture movimentoIndietro1;
    private Texture movimentooIndietro2;


    public Player(float startPlayerX, float startPlayerY) {
        super(startPlayerX, startPlayerY, 3);
        footSound = Gdx.audio.newSound(Gdx.files.internal("sfx/footstep.mp3"));
        footSound1 = Gdx.audio.newSound(Gdx.files.internal("sfx/footstep1.mp3"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sfx/jump.mp3"));
    }

    public void texturePlayer() {
        fermo = new Texture("player/player.png");
        movimentoAvanti1 = new Texture("player/playerMovimento1.png");
        movimentoAvanti2 = new Texture("player/playerMovimento2.png");
        fermoIndietro = new Texture("player/playerIndietro.png");
        movimentoIndietro1 = new Texture("player/playerIndietro1.png");
        movimentooIndietro2 = new Texture("player/playerIndietro2.png");
    }

    @Override
    public void movimento(float dt) {
        avanti = false;
        indietro = false;

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            avanti = true;
            playerX += SPEED * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            indietro = true;
            playerX -= SPEED * dt;
        }

        if (playerX < 0) {
            playerX = 0;
        }

        if (avanti || indietro) {
            timerAnimazione += 10 * dt;
            if (timerAnimazione > 4) {
                timerAnimazione = 0;
                suonoCamminata = true;
            }
        } else {
            timerAnimazione = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && velY == 0) {
            velY = JUMP_FORCE;
            long jumpId = jumpSound.play();
        }

        velY   += GRAVITY * dt;
        playerY += velY    * dt;
    }

    @Override
    public void disegna(SpriteBatch batch, float screenX, float screenY) {
        if (!avanti && !indietro) {
            batch.draw(fermo, screenX, screenY);
            return;
        }

        Texture frame1;
        Texture frameI;
        Texture frame2;

        if (avanti) {
            frame1 = movimentoAvanti1;
            frameI = fermo;
            frame2 = movimentoAvanti2;
        } else {
            frame1 = movimentoIndietro1;
            frameI = fermoIndietro;
            frame2 = movimentooIndietro2;
        }

        if (timerAnimazione <= 1) {
            batch.draw(frame1, screenX, screenY);
        } else if (timerAnimazione <= 2) {
            batch.draw(frameI, screenX, screenY);
        } else if (timerAnimazione <= 3) {
            batch.draw(frame2, screenX, screenY);
        } else {
            batch.draw(frameI, screenX, screenY);
        }

        if(suonoCamminata){
            if(suonoCamminata1) {
                long footId = footSound.play();
                suonoCamminata = false;
                suonoCamminata1 = false;
            } else{
                long foot1Id = footSound1.play();
                suonoCamminata = false;
                suonoCamminata1 = true;
            }
        }
    }

    @Override
    public void dispose() {
        fermo.dispose();
        movimentoAvanti1.dispose();
        movimentoAvanti2.dispose();
        fermoIndietro.dispose();
        movimentoIndietro1.dispose();
        movimentooIndietro2.dispose();
        footSound.dispose();
    }


    public boolean isAvanti() {
        return avanti;
    }

    public boolean isIndietro() {
        return indietro;
    }
}
