package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

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

    private float playerX = 50;
    private float playerY = 205;
    private float playerSizeX = 160;
    private float playerSizeY = 185;

    private boolean movimentoAvanti = false;
    private boolean movimentoIndietro = false;
    private int movimentoSprite = 0;

    private float vita = 3;




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

        playerBounds = new Rectangle(playerX, playerY, playerSizeX, playerSizeY);
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
        movimentoSprite += 1;
        playerBounds.x = xD / 2 - 160;

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            movimentoAvanti = true;
            movimentoIndietro = false;
            bgX1 -= vel * dt;
            bgX2 -= vel * dt;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            movimentoAvanti = false;
            movimentoIndietro = true;
            playerBounds.x -= vel * dt;
        }


        if (bgX1 + background.getWidth() < 0) {
            bgX1 = bgX2 + background.getWidth();
        }

        if (bgX2 + background.getWidth() < 0) {
            bgX2 = bgX1 + background.getWidth();
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
            if(movimentoSprite <= 10){
                batch.draw(playerMovimento1, playerBounds.x, playerBounds.y);
            } else if((movimentoSprite > 10 && movimentoSprite <= 20) || (movimentoSprite > 30 && movimentoSprite <= 40)){
                batch.draw(player, playerBounds.x, playerBounds.y);
            } else if(movimentoSprite > 20 && movimentoSprite <= 30){
                batch.draw(playerMovimento2, playerBounds.x, playerBounds.y);
            } else {
                movimentoSprite = 0;
                batch.draw(player, playerBounds.x, playerBounds.y);
            }
        } else if(movimentoIndietro){
            if(movimentoSprite <= 10){
                batch.draw(playerIndietro1, playerBounds.x, playerBounds.y);
            } else if((movimentoSprite > 10 && movimentoSprite <= 20) || (movimentoSprite > 30 && movimentoSprite <= 40)){
                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);
            } else if(movimentoSprite > 20 && movimentoSprite <= 30){
                batch.draw(playerIndietro2, playerBounds.x, playerBounds.y);
            } else {
                movimentoSprite = 0;
                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);
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
