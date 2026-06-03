package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGamePRIMO extends ApplicationAdapter {

    private SpriteBatch batch;

    // Setup background
    private Texture background;
    private float bgX1 = 0;
    private float bgX2 = 1200;
    private float vel = 500;

    // Setup del player
    private Texture player;
    private Texture playerMovimento1;
    private Texture playerMovimento2;
    private Texture playerIndietro;
    private Texture playerIndietro1;
    private Texture playerIndietro2;

    private Rectangle playerBounds;

    private float playerY = 205;
    private float playerSizeX = 160;
    private float playerSizeY = 185;

    // Salto
    private float velY;

    final float GRAVITY = -2000;
    final float JUMP_FORCE = 900;

    private float pavimento = 205;

    private boolean movimentoAvanti = false;
    private boolean movimentoIndietro = false;

    private float movimentoSprite = 0;
    private float vita = 3;

    // Ostacoli
    private Texture cassa;
    private Texture lava;
    private Texture muro;
    private Texture terra;
    private Texture spike;
    private Texture checkpoint;

    private Array<Obstacle> obstacles = new Array<>();

    private boolean spawnObstacle = true;

    private Random random = new Random();

    @Override
    public void create() {

        batch = new SpriteBatch();

        background = new Texture("background.jpg");

        player = new Texture("player.png");
        playerMovimento1 = new Texture("playerMovimento1.png");
        playerMovimento2 = new Texture("playerMovimento2.png");

        playerIndietro = new Texture("playerIndietro.png");
        playerIndietro1 = new Texture("playerIndietro1.png");
        playerIndietro2 = new Texture("playerIndietro2.png");

        playerBounds = new Rectangle((Gdx.graphics.getWidth() / 2 - 160), playerY, playerSizeX, playerSizeY);

        // Sprite degli ostacoli
        cassa = new Texture("ostacoli/cassa.png");
        lava = new Texture("ostacoli/lava.png");
        muro = new Texture("ostacoli/muro.png");
        terra = new Texture("ostacoli/terra.png");
        spike = new Texture("ostacoli/spike.png");
        checkpoint = new Texture("ostacoli/checkpoint.png");
    }

    @Override
    public void render() {

        ScreenUtils.clear(0, 0, 0, 1);

        float yD = Gdx.graphics.getHeight();
        float xD = Gdx.graphics.getWidth();
        float dt = Gdx.graphics.getDeltaTime();

        // Logica del gioco
        movimentoAvanti = false;
        movimentoIndietro = false;

        movimentoSprite += 10 * dt;

        if (
            Gdx.input.isKeyPressed(Input.Keys.D) ||
                Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        ) {

            movimentoAvanti = true;
            movimentoIndietro = false;

            if (playerBounds.x >= (xD / 2 - 160)) {

                playerBounds.x = xD / 2 - 160;

                bgX1 -= vel * dt;
                bgX2 -= vel * dt;

            } else {

                playerBounds.x += vel * dt;
            }
        }

        if (
            Gdx.input.isKeyPressed(Input.Keys.A) ||
                Gdx.input.isKeyPressed(Input.Keys.LEFT)
        ) {

            movimentoAvanti = false;
            movimentoIndietro = true;

            playerBounds.x -= vel * dt;
        }

        System.out.println(velY);

        // Logica salto
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && velY > -50 && velY < 50) {
            velY = JUMP_FORCE;
        }

        velY += GRAVITY * dt;
        playerBounds.y += velY * dt;

        // Movimento background
        if (bgX1 + background.getWidth() < 0) {
            bgX1 = bgX2 + background.getWidth();
        }

        if (bgX2 + background.getWidth() < 0) {

            bgX2 = bgX1 + background.getWidth();
            //spawnObstacle = true;
        }

        // Blocca uscita dallo schermo
        if (playerBounds.x <= 0) {

            playerBounds.x = 0;
            movimentoIndietro = false;
        }

        if (playerBounds.x >= xD - 160) {

            playerBounds.x = xD - 160;
            movimentoAvanti = false;
        }

        if (playerBounds.y < pavimento) {

            playerBounds.y = pavimento;
            velY = 0;
        }

        // Logica ostacoli
        if (spawnObstacle) {

            float obstacleH = 50 + random.nextInt(100);
            float obstacleY = 205;

            obstacles.add(new Obstacle(xD + 50));
            spawnObstacle = false;
        }

        // Disegna gli sprite
        batch.begin();

        batch.draw(background, bgX1, 0);
        batch.draw(background, bgX2, 0);

        // Animazione player
        if (!movimentoAvanti && !movimentoIndietro) {

            batch.draw(player, playerBounds.x, playerBounds.y);

            movimentoSprite = 0;

        } else if (movimentoAvanti) {

            if (movimentoSprite <= 1) {

                batch.draw(playerMovimento1, playerBounds.x, playerBounds.y);

            } else if (
                (movimentoSprite > 1 && movimentoSprite <= 2) ||
                    (movimentoSprite > 3 && movimentoSprite <= 4)
            ) {

                batch.draw(player, playerBounds.x, playerBounds.y);

            } else if (movimentoSprite > 2 && movimentoSprite <= 3) {

                batch.draw(playerMovimento2, playerBounds.x, playerBounds.y);

            } else {

                movimentoSprite = 0;

                batch.draw(player, playerBounds.x, playerBounds.y);
            }

        } else if (movimentoIndietro) {

            if (movimentoSprite <= 1) {

                batch.draw(playerIndietro1, playerBounds.x, playerBounds.y);

            } else if (
                (movimentoSprite > 1 && movimentoSprite <= 2) ||
                    (movimentoSprite > 3 && movimentoSprite <= 4)
            ) {

                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);

            } else if (movimentoSprite > 2 && movimentoSprite <= 3) {

                batch.draw(playerIndietro2, playerBounds.x, playerBounds.y);

            } else {

                movimentoSprite = 0;

                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);
            }
        }

        for (int i = obstacles.size - 1; i >= 0; i--) {

            Obstacle obs = obstacles.get(i);
            obs.popolaOstacolo();
            obs.popolaAltezzaOstacoli();

            int[] elementi = obs.getComposizione();
            float[] altezzaElementi = obs.getAltezzaOstacoli();

            if (movimentoAvanti && playerBounds.x >= (xD / 2 - 160)) {
                obs.startX -= vel * dt;
            }

            float xElemento = obs.startX;
            boolean sopraElemento = false;

            for (int j = 0; j < elementi.length; j++) {

                float elemW, elemH;

                if (elementi[j] == obs.getCassa()) {
                    elemW = 138;
                    elemH = altezzaElementi[j];
                    batch.draw(cassa, xElemento, obs.startY, elemW, elemH);
                } else if (elementi[j] == obs.getLava()) {
                    elemW = 187;
                    elemH = 120;
                    batch.draw(lava, xElemento, obs.startY, elemW, elemH);
                } else if (elementi[j] == obs.getMuro()) {
                    elemW = muro.getWidth();
                    elemH = altezzaElementi[j];
                    batch.draw(muro, xElemento, obs.startY, elemW, elemH);
                } else if (elementi[j] == obs.getTerra()) {
                    elemW = terra.getWidth();
                    elemH = altezzaElementi[j];
                    batch.draw(terra, xElemento, obs.startY, elemW, elemH);
                } else if (elementi[j] == obs.getSpike()) {
                    elemW = 193;
                    elemH = 120;
                    batch.draw(spike, xElemento, obs.startY, elemW, elemH);
                } else {
                    xElemento += 0;
                    continue;
                }

                if (collisione(playerBounds.x, playerBounds.y, playerSizeX, playerSizeY, xElemento, obs.startY, elemW, elemH)) {

                    float playerBottom = playerBounds.y;
                    float obstacleTop  = obs.startY + elemH;

                    float overlapY = playerBottom - (obstacleTop - elemH);

                    //Stringa generata con AI
                    float overlapX = Math.min((playerBounds.x + playerSizeX) - xElemento, (xElemento + elemW) - playerBounds.x);

                    if (velY <= 0 && playerBottom <= obstacleTop && playerBottom >= obstacleTop - 20) {
                        sopraElemento = true;
                        pavimento = obstacleTop;
                    } else if (overlapX < playerSizeX / 2f) {
                        sopraElemento = true;
                        pavimento = obstacleTop;
                    }
                }

                xElemento += elemW;
            }

            batch.draw(checkpoint, xElemento, obs.startY);

            if (!sopraElemento) {
                pavimento = 205;
            }

            if(obs.startX < -1400 && obstacles.size < 2){
                spawnObstacle = true;
            }

            if (obs.startX < -2100) {
                obstacles.removeIndex(i);
            }

        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();

        player.dispose();
        playerMovimento1.dispose();
        playerMovimento2.dispose();

        playerIndietro.dispose();
        playerIndietro1.dispose();
        playerIndietro2.dispose();

        cassa.dispose();
        lava.dispose();
        muro.dispose();
        spike.dispose();
        terra.dispose();
    }

    public boolean collisione(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {

        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
}
