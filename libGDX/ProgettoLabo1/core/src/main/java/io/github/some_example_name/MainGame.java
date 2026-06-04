package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

public class MainGame extends ApplicationAdapter {

    private SpriteBatch batch;

    // Background terreno (scorre in loop orizzontale e verticale)
    private Texture background;
    private float bgX1, bgX2;

    private BitmapFont font;

    // Background cielo: appare sopra il terreno quando la camera sale
    private Texture backgroundSky;
    private float skyBgX1, skyBgX2;

    // Camera: coordinate MONDO dell'angolo in basso a sinistra della viewport.
    // screen = world - camera
    private float cameraX = 0f;
    private float cameraY = 0f;

    // Texture ostacoli
    private Texture cassa, lava, muro, terra, spike, coin;

    // Mondo caricato da file
    private WorldLoader worldLoader;
    private List<WorldLoader.Ostacolo> worldTiles;

    // Player
    private Player player;

    private int monete;
    private int vita = 3;

    private float timer = 0f;

    @Override
    public void create() {
        batch = new SpriteBatch();

        background    = new Texture("background.jpg");
        backgroundSky = new Texture("background_sky.png");

        bgX1    = 0f;
        bgX2    = background.getWidth();
        skyBgX1 = 0f;
        skyBgX2 = backgroundSky.getWidth();

        cassa = new Texture("ostacoli/cassa.png");
        lava  = new Texture("ostacoli/lava.png");
        muro  = new Texture("ostacoli/muro.png");
        terra = new Texture("ostacoli/terra.png");
        spike = new Texture("ostacoli/spike.png");
        coin = new Texture("ostacoli/coin.png");

        worldLoader = new WorldLoader("world.txt");
        worldTiles  = worldLoader.getOstacoloNonAria();

        // Player parte al centro schermo (posizione mondo = posizione schermo quando cameraX=0)
        float xD = Gdx.graphics.getWidth();
        player = new Player(0f, WorldLoader.PAVIMENTO);
        player.texturePlayer();

        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(3f);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        float xD = Gdx.graphics.getWidth();
        float yD = Gdx.graphics.getHeight();
        float dt = Gdx.graphics.getDeltaTime();

        // ── AGGIORNAMENTO PLAYER (input + fisica) ─────────────────────
        player.movimento(dt);

        timer += dt;

        // ── COLLISIONI CON I TILE IN COORDINATE MONDO ─────────────────
        float worldPavimento  = WorldLoader.PAVIMENTO;
        boolean sopraQualcosa = false;

        for (WorldLoader.Ostacolo tile : worldTiles) {
            if (!collisione(player.playerX, player.playerY, Player.WIDTH, Player.HEIGHT, tile.worldX,  tile.worldY,   tile.width,   tile.height)) continue;

            float tileTop    = tile.worldY + tile.height;
            float tileRight  = tile.worldX + tile.width;

            // Calcola l'overlap su ogni lato
            float overlapTop    = tileTop                       - player.playerY;          // player entra dal basso
            float overlapBottom = (player.playerY + Player.HEIGHT) - tile.worldY;          // player scende dall'alto
            float overlapLeft   = tileRight                     - player.playerX;          // player viene da destra
            float overlapRight  = (player.playerX + Player.WIDTH) - tile.worldX;          // player viene da sinistra

            float minOverlapY = Math.min(overlapTop, overlapBottom);
            float minOverlapX = Math.min(overlapLeft, overlapRight);

            if(tile.tipo == WorldLoader.COIN){
                tile.worldX = -9999f;
                tile.worldY = -9999f;
                monete += 1;
                continue;
            }

            if (minOverlapY < minOverlapX) {
                // ── Collisione verticale ──────────────────────────────
                if (overlapTop < overlapBottom) {
                    // Player atterra sopra il tile (cade dall'alto)
                    sopraQualcosa = true;

                    if((tile.tipo == WorldLoader.SPIKE || tile.tipo == WorldLoader.LAVA) && timer >= 2f ){
                        player.vita -= 1;
                        timer = 0;
                    }

                    if (tileTop > worldPavimento) worldPavimento = tileTop;
                } else {
                    // Player colpisce il tile dal basso (soffitto)
                    player.playerY = tile.worldY - Player.HEIGHT;
                    player.velY   = 0f;
                }
            } else {
                // ── Collisione orizzontale: blocca il player di lato ──
                if (overlapRight < overlapLeft) {
                    // Player arriva da sinistra → spingi a sinistra
                    player.playerX = tile.worldX - Player.WIDTH;
                } else {
                    // Player arriva da destra → spingi a destra
                    player.playerX = tileRight;
                }
            }
        }

        // Clamp al pavimento
        float pavimento = sopraQualcosa ? worldPavimento : WorldLoader.PAVIMENTO;
        if (player.playerY < pavimento) {
            player.playerY = pavimento;
            player.velY   = 0f;
        }

        // ── CAMERA: il player resta al centro dello schermo (X e Y) ───
        float prevCameraX = cameraX;
        float prevCameraY = cameraY;

        cameraX = Math.max(0f, player.playerX - (xD / 2f - Player.WIDTH));
        cameraY = Math.max(0f, player.playerY - (yD / 2f - Player.HEIGHT / 2f));

        float deltaCameraX = cameraX - prevCameraX;
        float deltaCameraY = cameraY - prevCameraY;

        // ── SCROLL BACKGROUND ─────────────────────────────────────────
        // Terreno: loop orizzontale (sia sinistra che destra)
        float bgW = background.getWidth();
        bgX1 -= deltaCameraX;
        bgX2 -= deltaCameraX;
        if (bgX1 + bgW < 0)  bgX1 = bgX2 + bgW;
        if (bgX2 + bgW < 0)  bgX2 = bgX1 + bgW;
        if (bgX1 > xD)       bgX1 = bgX2 - bgW;
        if (bgX2 > xD)       bgX2 = bgX1 - bgW;

        // Cielo: loop orizzontale (sia sinistra che destra)
        float skyW = backgroundSky.getWidth();
        skyBgX1 -= deltaCameraX;
        skyBgX2 -= deltaCameraX;
        if (skyBgX1 + skyW < 0)  skyBgX1 = skyBgX2 + skyW;
        if (skyBgX2 + skyW < 0)  skyBgX2 = skyBgX1 + skyW;
        if (skyBgX1 > xD)        skyBgX1 = skyBgX2 - skyW;
        if (skyBgX2 > xD)        skyBgX2 = skyBgX1 - skyW;

        // Posizioni Y sullo schermo
        float bgScreenY = -cameraY;                              // terreno scende quando la camera sale
        float skyStartY = background.getHeight() - cameraY;     // cielo inizia dove finisce il terreno

        // ── DISEGNO ───────────────────────────────────────────────────
        batch.begin();

        // 1. Cielo: riempie l'area sopra il background del terreno.
        //    Looppa verticalmente se la camera è salita molto.
        float skyH = backgroundSky.getHeight();
        for (float skyY = skyStartY; skyY < yD; skyY += skyH) {
            batch.draw(backgroundSky, skyBgX1, skyY);
            batch.draw(backgroundSky, skyBgX2, skyY);
        }

        // 2. Terreno: disegnato sopra il cielo nella zona bassa
        if (bgScreenY + background.getHeight() > 0) {
            batch.draw(background, bgX1, bgScreenY);
            batch.draw(background, bgX2, bgScreenY);
        }

        // 3. Tile del mondo (world → screen tramite camera)
        for (WorldLoader.Ostacolo tile : worldTiles) {
            float screenX = tile.worldX - cameraX;
            float screenY = tile.worldY - cameraY;

            // Culling: salta tile non visibili
            if (screenX + tile.width  < 0 || screenX > xD) continue;
            if (screenY + tile.height < 0 || screenY > yD) continue;

            switch (tile.tipo) {
                case WorldLoader.CASSA: batch.draw(cassa, screenX, screenY, tile.width, tile.height); break;
                case WorldLoader.LAVA:  batch.draw(lava,  screenX, screenY, tile.width, tile.height); break;
                case WorldLoader.MURO:  batch.draw(muro,  screenX, screenY, tile.width, tile.height); break;
                case WorldLoader.SPIKE: batch.draw(spike, screenX, screenY, tile.width, tile.height); break;
                case WorldLoader.TERRA: batch.draw(terra, screenX, screenY, tile.width, tile.height); break;
                case WorldLoader.COIN: batch.draw(coin, screenX, screenY, tile.width, tile.height); break;
            }
        }

        font.draw(batch, "COIN: " + monete, Gdx.graphics.getWidth() - 180, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "VITA: " + player.vita, 20, Gdx.graphics.getHeight() - 20);

        // 4. Player (conversione world → screen)
        player.disegna(batch, player.playerX - cameraX, player.playerY - cameraY);

        batch.end();
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

    private boolean collisione(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
}
