package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

public class MainGame extends ApplicationAdapter {

    private SpriteBatch batch;

    // Background
    private Texture background;
    private float bgX1 = 0;
    private float bgX2 = 1200;
    private final float vel = 500;

    // Player
    private Texture player;
    private Texture playerMovimento1;
    private Texture playerMovimento2;
    private Texture playerIndietro;
    private Texture playerIndietro1;
    private Texture playerIndietro2;

    private Rectangle playerBounds;

    private final float playerSizeX = 160;
    private final float playerSizeY = 185;

    // Salto
    private float velY;
    final float GRAVITY    = -2000;
    final float JUMP_FORCE = 900;

    // Pavimento dinamico: sale quando il player è sopra un tile
    private float pavimento = WorldLoader.BASE_Y;

    private boolean movimentoAvanti   = false;
    private boolean movimentoIndietro = false;
    private float movimentoSprite     = 0;
    private float vita                = 3;

    // Texture ostacoli
    private Texture cassa;
    private Texture lava;
    private Texture muro;
    private Texture terra;
    private Texture spike;

    // ---- MONDO DA FILE ----
    private WorldLoader worldLoader;

    /**
     * Offset di scorrimento del mondo: quando il player cammina a destra,
     * worldOffsetX aumenta, spostando tutti i tile verso sinistra.
     */
    private float worldOffsetX = 0f;

    /** Lista statica dei tile non-aria caricati dal file. */
    private List<WorldLoader.WorldTile> worldTiles;

    @Override
    public void create() {

        batch = new SpriteBatch();

        background = new Texture("background.jpg");

        player         = new Texture("player.png");
        playerMovimento1 = new Texture("playerMovimento1.png");
        playerMovimento2 = new Texture("playerMovimento2.png");
        playerIndietro  = new Texture("playerIndietro.png");
        playerIndietro1 = new Texture("playerIndietro1.png");
        playerIndietro2 = new Texture("playerIndietro2.png");

        cassa     = new Texture("ostacoli/cassa.png");
        lava      = new Texture("ostacoli/lava.png");
        muro      = new Texture("ostacoli/muro.png");
        terra     = new Texture("ostacoli/terra.png");
        spike     = new Texture("ostacoli/spike.png");

        // Carica il mondo dal file txt (metti world.txt nella cartella assets)
        worldLoader = new WorldLoader("world.txt");
        worldTiles  = worldLoader.getTileNonAria();

        float xD = Gdx.graphics.getWidth();
        playerBounds = new Rectangle(xD / 2f - playerSizeX, WorldLoader.BASE_Y, playerSizeX, playerSizeY);
    }

    @Override
    public void render() {

        ScreenUtils.clear(0, 0, 0, 1);

        float xD = Gdx.graphics.getWidth();
        float dt = Gdx.graphics.getDeltaTime();

        // ------- INPUT -------
        movimentoAvanti   = false;
        movimentoIndietro = false;
        movimentoSprite  += 10 * dt;

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movimentoAvanti   = true;
            movimentoIndietro = false;

            if (playerBounds.x >= xD / 2f - playerSizeX) {
                // Player fermo al centro: scorri il mondo
                playerBounds.x = xD / 2f - playerSizeX;
                worldOffsetX  += vel * dt;

                // Scorri anche il background
                bgX1 -= vel * dt;
                bgX2 -= vel * dt;
            } else {
                playerBounds.x += vel * dt;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movimentoAvanti   = false;
            movimentoIndietro = true;

            // Muovi il player (e il mondo) indietro solo se c'è spazio
            if (worldOffsetX > 0) {
                worldOffsetX -= vel * dt;
                bgX1 += vel * dt;
                bgX2 += vel * dt;
            } else {
                playerBounds.x -= vel * dt;
            }
        }

        // ------- SALTO -------
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && velY > -50 && velY < 50) {
            velY = JUMP_FORCE;
        }

        velY += GRAVITY * dt;
        playerBounds.y += velY * dt;

        // ------- BACKGROUND LOOP -------
        if (bgX1 + background.getWidth() < 0) bgX1 = bgX2 + background.getWidth();
        if (bgX2 + background.getWidth() < 0) bgX2 = bgX1 + background.getWidth();

        // ------- LIMITI SCHERMO -------
        if (playerBounds.x <= 0) {
            playerBounds.x    = 0;
            movimentoIndietro = false;
        }
        if (playerBounds.x >= xD - playerSizeX) {
            playerBounds.x  = xD - playerSizeX;
            movimentoAvanti = false;
        }

        // ------- COLLISIONI CON I TILE -------
        // Prima applica la gravità, poi correggi la posizione
        boolean sopraQualcosa = false;
        float nuovoPavimento  = WorldLoader.BASE_Y;

        for (WorldLoader.WorldTile tile : worldTiles) {
            // Posizione sullo schermo del tile (dipende dall'offset del mondo)
            float screenX = tile.worldX - worldOffsetX;
            float screenY = tile.worldY;

            // Culling: salta tile fuori schermo
            if (screenX + tile.width < 0 || screenX > xD) continue;

            if (collisione(playerBounds.x, playerBounds.y, playerSizeX, playerSizeY,
                           screenX, screenY, tile.width, tile.height)) {

                float playerBottom = playerBounds.y;
                float tileTop      = screenY + tile.height;

                // Overlap orizzontale
                float overlapX = Math.min(
                    (playerBounds.x + playerSizeX) - screenX,
                    (screenX + tile.width) - playerBounds.x
                );

                // Il player atterra sopra il tile se viene da sopra
                // e l'overlap verticale è piccolo (bordo superiore del tile)
                if (velY <= 0 && playerBottom <= tileTop && playerBottom >= tileTop - 30) {
                    sopraQualcosa = true;
                    if (tileTop > nuovoPavimento) {
                        nuovoPavimento = tileTop;
                    }
                } else if (overlapX < playerSizeX / 2f) {
                    // Collisione laterale: trattata come "sopra"
                    sopraQualcosa = true;
                    if (tileTop > nuovoPavimento) {
                        nuovoPavimento = tileTop;
                    }
                }
            }
        }

        pavimento = sopraQualcosa ? nuovoPavimento : WorldLoader.BASE_Y;

        if (playerBounds.y < pavimento) {
            playerBounds.y = pavimento;
            velY = 0;
        }

        // ------- DISEGNO -------
        batch.begin();

        // Background
        batch.draw(background, bgX1, 0);
        batch.draw(background, bgX2, 0);

        // Animazione player
        disegnaPlayer();

        // Tile del mondo
        for (WorldLoader.WorldTile tile : worldTiles) {
            float screenX = tile.worldX - worldOffsetX;
            float screenY = tile.worldY;

            // Culling
            if (screenX + tile.width < -50 || screenX > xD + 50) continue;

            switch (tile.tipo) {
                case WorldLoader.CASSA:
                    batch.draw(cassa,  screenX, screenY, tile.width, tile.height);
                    break;
                case WorldLoader.LAVA:
                    batch.draw(lava,   screenX, screenY, tile.width, tile.height);
                    break;
                case WorldLoader.MURO:
                    batch.draw(muro,   screenX, screenY, tile.width, tile.height);
                    break;
                case WorldLoader.SPIKE:
                    batch.draw(spike,  screenX, screenY, tile.width, tile.height);
                    break;
                case WorldLoader.TERRA:
                    batch.draw(terra,  screenX, screenY, tile.width, tile.height);
                    break;
            }
        }

        batch.end();
    }

    private void disegnaPlayer() {
        if (!movimentoAvanti && !movimentoIndietro) {
            batch.draw(player, playerBounds.x, playerBounds.y);
            movimentoSprite = 0;
        } else if (movimentoAvanti) {
            if (movimentoSprite <= 1) {
                batch.draw(playerMovimento1, playerBounds.x, playerBounds.y);
            } else if ((movimentoSprite > 1 && movimentoSprite <= 2) ||
                       (movimentoSprite > 3 && movimentoSprite <= 4)) {
                batch.draw(player, playerBounds.x, playerBounds.y);
            } else if (movimentoSprite > 2 && movimentoSprite <= 3) {
                batch.draw(playerMovimento2, playerBounds.x, playerBounds.y);
            } else {
                movimentoSprite = 0;
                batch.draw(player, playerBounds.x, playerBounds.y);
            }
        } else {
            if (movimentoSprite <= 1) {
                batch.draw(playerIndietro1, playerBounds.x, playerBounds.y);
            } else if ((movimentoSprite > 1 && movimentoSprite <= 2) ||
                       (movimentoSprite > 3 && movimentoSprite <= 4)) {
                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);
            } else if (movimentoSprite > 2 && movimentoSprite <= 3) {
                batch.draw(playerIndietro2, playerBounds.x, playerBounds.y);
            } else {
                movimentoSprite = 0;
                batch.draw(playerIndietro, playerBounds.x, playerBounds.y);
            }
        }
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

    public boolean collisione(float x1, float y1, float w1, float h1,
                               float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
}
