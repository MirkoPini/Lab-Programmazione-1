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

    private boolean movimentoAvanti = false;
    private boolean movimentoIndietro = false;
    private float movimentoSprite = 0;

    private float vita = 3;

    //Ostacoli
    private Texture obstacleTexture;

    private Array<Obstacle> obstacles = new Array<>();
    private float spawnTimer = 0;
    private float spawnInterval = 2.5f;
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

        obstacleTexture = new Texture("libgdx.png");
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

        if(playerBounds.y < 205){
            playerBounds.y = 205;
        }

        //Logica Ostacoli

        spawnTimer += dt;
        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;
            spawnInterval = 1.5f + random.nextFloat() * 2f;

            float obstacleH = 50 + random.nextInt(100);
            float obstacleY = 205;
            obstacles.add(new Obstacle(obstacleTexture, xD + 50, obstacleY, 60, obstacleH));
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
            if(movimentoAvanti) {
                obs.bounds.x -= vel * dt;
            }

            if (obs.bounds.x + obs.bounds.width < 0) {
                obstacles.removeIndex(i);
                continue;
            }

            batch.draw(obs.texture, obs.bounds.x, obs.bounds.y,
                obs.bounds.width, obs.bounds.height);


            if (obs.bounds.overlaps(playerBounds)) {
                vita--;
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
    }
}
