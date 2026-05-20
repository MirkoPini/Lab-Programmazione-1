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
public class MainGame extends ApplicationAdapter {
    private SpriteBatch batch;

    //Setup background
    private Texture background;

    private float bgX1 = 0;
    private float bgX2 = 1200;

    private float vel = 200;

    //Setup del player

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

    //Salto
    private float velY;
    final float GRAVITY = -2000;
    final float JUMP_FORCE = 900;
    private float pavimento = 205;

    private boolean movimentoAvanti = false;
    private boolean movimentoIndietro = false;
    private float movimentoSprite = 0;

    private float vita = 3;

    //Ostacoli
    private Texture cassa;
    private Texture lava;
    private Texture muro;
    private Texture terra;
    private Texture spike;

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

        //Sprite degli ostacoli
        cassa = new Texture("ostacoli/cassa.png");
        lava = new Texture("ostacoli/lava.png");
        muro = new Texture("ostacoli/muro.png");
        terra = new Texture("ostacoli/terra.png");
        spike = new Texture("ostacoli/spike.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0,0,0,1);
        float yD = Gdx.graphics.getHeight();
        float xD = Gdx.graphics.getWidth();
        float dt = Gdx.graphics.getDeltaTime();

        //Logica del gioco
        movimentoAvanti = false;
        movimentoIndietro = false;
        movimentoSprite += 10 * dt;

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            movimentoAvanti = true;
            movimentoIndietro = false;
            if(playerBounds.x >= (xD / 2 - 160)) {
                playerBounds.x = xD / 2 - 160;
                bgX1 -= vel * dt;
                bgX2 -= vel * dt;
            }else {
                playerBounds.x += vel * dt;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            movimentoAvanti = false;
            movimentoIndietro = true;
            playerBounds.x -= vel * dt;
        }

        //Logica Salto
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            velY = JUMP_FORCE;
        }

        velY += GRAVITY * dt;

        playerBounds.y += velY * dt;

        //Movimento background
        if (bgX1 + background.getWidth() < 0) {
            bgX1 = bgX2 + background.getWidth();
        }

        if (bgX2 + background.getWidth() < 0) {
            bgX2 = bgX1 + background.getWidth();
            spawnObstacle = true;
        }

        //Blocca uscita dallo schermo
        if(playerBounds.x <= 0){
            playerBounds.x = 0;
            movimentoIndietro = false;
        }

        if(playerBounds.x >= xD - 160){
            playerBounds.x = xD - 160;
            movimentoAvanti = false;
        }

        if(playerBounds.y < pavimento){
            playerBounds.y = pavimento;
            velY = 0;
        }

        //Logica Ostacoli

        if (spawnObstacle) {
            float obstacleH = 50 + random.nextInt(100);
            float obstacleY = 205;
            obstacles.add(new Obstacle(xD + 50));
        }


        //Disegna gli sprite
        batch.begin();

        batch.draw(background, bgX1, 0);
        batch.draw(background, bgX2, 0);

        //Animazione player
        if(!movimentoAvanti && !movimentoIndietro) {
            batch.draw(player, playerBounds.x, playerBounds.y);
            movimentoSprite = 0;
        } else if(movimentoAvanti){
            if(movimentoSprite <= 1){
                batch.draw(playerMovimento1, playerBounds.x, playerBounds.y);
            } else if((movimentoSprite > 1 && movimentoSprite <= 2) || (movimentoSprite > 3 && movimentoSprite <= 4)){
                batch.draw(player, playerBounds.x, playerBounds.y);
            } else if(movimentoSprite > 2 && movimentoSprite <= 3){
                batch.draw(playerMovimento2, playerBounds.x, playerBounds.y);
            } else {
                movimentoSprite = 0;
                batch.draw(player, playerBounds.x, playerBounds.y);
            }
        } else if(movimentoIndietro){
            if(movimentoSprite <= 1){
                batch.draw(playerIndietro1, playerBounds.x, playerBounds.y);
            } else if((movimentoSprite > 1 && movimentoSprite <= 2) || (movimentoSprite > 3 && movimentoSprite <= 4)){
                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);
            } else if(movimentoSprite > 2 && movimentoSprite <= 3){
                batch.draw(playerIndietro2, playerBounds.x, playerBounds.y);
            } else {
                movimentoSprite = 0;
                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);
            }
        }

        for (int i = obstacles.size - 1; i >= 0; i--) {
            Obstacle obs = obstacles.get(i);

            obs.popolaOstacolo();
            int[] elementi = obs.getComposizione();

            if(movimentoAvanti && playerBounds.x >= (xD / 2 - 160)) {
                obs.startX -= vel * dt;
            }

            float xElemento = obs.startX;
            for (int j = 0; j < elementi.length; j++) {
                if(elementi[j] == obs.getCassa()){
                    batch.draw(cassa, xElemento, obs.startY, 138, 120);
                    xElemento += 138;

                    if(collisione(player, playerBounds.x, playerBounds.y, cassa, xElemento, obs.startY)){
                        pavimento = 324;
                    } else{
                        pavimento = 205;
                    }

                } else if(elementi[j] == obs.getLava()) {
                    batch.draw(lava, xElemento, obs.startY, 187, 120);
                    xElemento += 187;

                    if (collisione(player, playerBounds.x, playerBounds.y, lava, xElemento, obs.startY)) {
                        pavimento = 324;
                    } else {
                        pavimento = 205;
                    }

                } else if(elementi[j] == obs.getMuro()){
                    batch.draw(muro, xElemento, obs.startY, muro.getWidth(), muro.getHeight());
                    xElemento += muro.getWidth();

                    if (collisione(player, playerBounds.x, playerBounds.y, muro, xElemento, obs.startY)) {
                        pavimento = 324;
                    } else {
                        pavimento = 205;
                    }

                } else if(elementi[j] == obs.getTerra()){
                    batch.draw(terra, xElemento, obs.startY, terra.getWidth(), terra.getHeight());
                    xElemento += terra.getWidth();

                    if (collisione(player, playerBounds.x, playerBounds.y, terra, xElemento, obs.startY)) {
                        pavimento = 324;
                    } else {
                        pavimento = 205;
                    }

                } else if(elementi[j] == obs.getSpike()){
                    batch.draw(spike, xElemento, obs.startY, spike.getWidth(), spike.getHeight());
                    xElemento += spike.getWidth();

                    if (collisione(player, playerBounds.x, playerBounds.y, spike, xElemento, obs.startY)) {
                        pavimento = 324;
                    } else {
                        pavimento = 205;
                    }
                }
            }

            if (obs.startX < 0 - xElemento + obs.startX) {
                obstacles.removeIndex(i);
            }
        }

        spawnObstacle = false;
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

    public boolean collisione( Texture t1, float x1, float y1, Texture t2, float x2, float y2) {

        return x1 < x2 + t2.getWidth() && x1 + t1.getWidth() > x2 && y1 < y2 + t2.getHeight() && y1 + t1.getHeight() > y2;
    }
}
