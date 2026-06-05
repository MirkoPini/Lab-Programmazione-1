package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Texture background;
    private float bgX1, bgX2;
    private BitmapFont font;
    private Texture backgroundSky;
    private float skyBgX1, skyBgX2;

    private float coordinataX = 0;
    private float coordinataY = 0;

    private Texture cassa;
    private Texture lava;
    private Texture muro;
    private Texture terra;
    private Texture spike;
    private Texture coin;

    private WorldLoader worldLoader;
    private List<WorldLoader.Ostacolo> Ostacoli;

    private final MainGame game;
    private final int livello;

    private Player player;
    private int monete;
    private float timer = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();

        background    = new Texture("background.jpg");
        backgroundSky = new Texture("background_sky.png");

        bgX1 = 0;
        bgX2 = background.getWidth();
        skyBgX1 = 0;
        skyBgX2 = backgroundSky.getWidth();

        cassa = new Texture("ostacoli/cassa.png");
        lava  = new Texture("ostacoli/lava.png");
        muro  = new Texture("ostacoli/muro.png");
        terra = new Texture("ostacoli/terra.png");
        spike = new Texture("ostacoli/spike.png");
        coin = new Texture("ostacoli/coin.png");

        worldLoader = new WorldLoader("world" + livello + ".txt");
        Ostacoli = worldLoader.getOstacoloNonAria();

        float xD = Gdx.graphics.getWidth();
        player = new Player(0, WorldLoader.PAVIMENTO);
        player.texturePlayer();

        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(3);
    }

    @Override
    public void render(float dt) {
        ScreenUtils.clear(0, 0, 0, 1);

        float xD = Gdx.graphics.getWidth();
        float yD = Gdx.graphics.getHeight();
        //float dt = Gdx.graphics.getDeltaTime();

        player.movimento(dt);

        timer += dt;

        float worldPavimento  = WorldLoader.PAVIMENTO;
        boolean sopraQualcosa = false;

        for (WorldLoader.Ostacolo obs : Ostacoli) {
            if (!collisione(player.playerX, player.playerY, Player.WIDTH, Player.HEIGHT, obs.worldX,  obs.worldY,   obs.width,   obs.height)){
                continue;
            }

            float obsTop = obs.worldY + obs.height;
            float obsRight = obs.worldX + obs.width;

            //Generato da AI
            float overlapTop = obsTop - player.playerY;
            float overlapBottom = (player.playerY + Player.HEIGHT) - obs.worldY;
            float overlapLeft = obsRight - player.playerX;
            float overlapRight = (player.playerX + Player.WIDTH) - obs.worldX;

            float minOverlapY = Math.min(overlapTop, overlapBottom);
            float minOverlapX = Math.min(overlapLeft, overlapRight);
            //Fino a qui

            if(obs.tipo == WorldLoader.COIN){
                obs.worldX = -9999f;
                obs.worldY = -9999f;
                monete += 1;
                continue;
            }

            if (minOverlapY < minOverlapX) {
                if (overlapTop < overlapBottom) {
                    sopraQualcosa = true;

                    if((obs.tipo == WorldLoader.SPIKE || obs.tipo == WorldLoader.LAVA) && timer >= 2f ){
                        player.vita -= 1;
                        timer = 0;
                    }
                    if (obsTop > worldPavimento) worldPavimento = obsTop;
                } else {
                    player.playerY = obs.worldY - Player.HEIGHT;
                    player.velY   = 0f;
                }
            } else {
                if (overlapRight < overlapLeft) {
                    player.playerX = obs.worldX - Player.WIDTH;
                } else {
                    player.playerX = obsRight;
                }
            }
        }

        float pavimento = sopraQualcosa ? worldPavimento : WorldLoader.PAVIMENTO;
        if (player.playerY < pavimento) {
            player.playerY = pavimento;
            player.velY   = 0f;
        }

        float prevCoordX = coordinataX;
        float prevCoordY = coordinataY;

        coordinataX = Math.max(0f, player.playerX - (xD / 2f - Player.WIDTH));
        coordinataY = Math.max(0f, player.playerY - (yD / 2f - Player.HEIGHT / 2f));

        float deltaCoordX = coordinataX - prevCoordX;
        float deltaCoordY = coordinataY - prevCoordY;


        float bgW = background.getWidth();
        bgX1 -= deltaCoordX;
        bgX2 -= deltaCoordX;

        if (bgX1 + bgW < 0) {
            bgX1 = bgX2 + bgW;
        }
        if (bgX2 + bgW < 0) {
            bgX2 = bgX1 + bgW;
        }
        if (bgX1 > xD) {
            bgX1 = bgX2 - bgW;
        }
        if (bgX2 > xD) {
            bgX2 = bgX1 - bgW;
        }


        float skyW = backgroundSky.getWidth();
        skyBgX1 -= deltaCoordX;
        skyBgX2 -= deltaCoordX;

        if (skyBgX1 + skyW < 0) {
            skyBgX1 = skyBgX2 + skyW;
        }
        if (skyBgX2 + skyW < 0) {
            skyBgX2 = skyBgX1 + skyW;
        }
        if (skyBgX1 > xD) {
            skyBgX1 = skyBgX2 - skyW;
        }
        if (skyBgX2 > xD) {
            skyBgX2 = skyBgX1 - skyW;
        }

        float bgScreenY = -coordinataY;
        float skyStartY = background.getHeight() - coordinataY;

        batch.begin();


        float skyH = backgroundSky.getHeight();

        for (float skyY = skyStartY; skyY < yD; skyY += skyH) {
            batch.draw(backgroundSky, skyBgX1, skyY);
            batch.draw(backgroundSky, skyBgX2, skyY);
        }

        if (bgScreenY + background.getHeight() > 0) {
            batch.draw(background, bgX1, bgScreenY);
            batch.draw(background, bgX2, bgScreenY);
        }

        for (WorldLoader.Ostacolo obs : Ostacoli) {
            float screenX = obs.worldX - coordinataX;
            float screenY = obs.worldY - coordinataY;

            if (screenX + obs.width  < 0 || screenX > xD) continue;
            if (screenY + obs.height < 0 || screenY > yD) continue;

            switch (obs.tipo) {
                case WorldLoader.CASSA: batch.draw(cassa, screenX, screenY, obs.width, obs.height); break;
                case WorldLoader.LAVA:  batch.draw(lava,  screenX, screenY, obs.width, obs.height); break;
                case WorldLoader.MURO:  batch.draw(muro,  screenX, screenY, obs.width, obs.height); break;
                case WorldLoader.SPIKE: batch.draw(spike, screenX, screenY, obs.width, obs.height); break;
                case WorldLoader.TERRA: batch.draw(terra, screenX, screenY, obs.width, obs.height); break;
                case WorldLoader.COIN: batch.draw(coin, screenX, screenY, obs.width, obs.height); break;
            }
        }

        font.draw(batch, "COIN: " + monete, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "VITE: " + player.vita + "/3", 20, Gdx.graphics.getHeight() - 20);

        player.disegna(batch, player.playerX - coordinataX, player.playerY - coordinataY);

        batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        backgroundSky.dispose();
        cassa.dispose();
        lava.dispose();
        muro.dispose();
        spike.dispose();
        terra.dispose();
        coin.dispose();
        player.dispose();
    }

    //Generata con AI
    private boolean collisione(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    public GameScreen(MainGame game, int livello) {
        this.game = game;
        this.livello = livello;
    }
}
