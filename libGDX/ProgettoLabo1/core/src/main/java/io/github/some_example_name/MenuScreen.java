package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {

    private final MainGame game;
    private BitmapFont font;
    private String[] livelli = {"Livello 1", "Livello 2", "Livello 3"};
    private int selezione = 0;

    public MenuScreen(MainGame game) {
        this.game = game;
        font = new BitmapFont();
        font.getData().setScale(3);
    }

    @Override
    public void render(float dt) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selezione = (selezione - 1 + livelli.length) % livelli.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selezione = (selezione + 1) % livelli.length;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game, selezione + 1));
        }

        game.batch.begin();
        font.draw(game.batch, "SELEZIONA LIVELLO", 100, 500);

        for (int i = 0; i < livelli.length; i++) {
            String testo;
            if (i == selezione) {
                testo = "> " + livelli[i];
            } else {
                testo = "  " + livelli[i];
            }
            font.setColor(i == selezione ? Color.YELLOW : Color.WHITE);
            font.draw(game.batch, testo, 100, 380 - i * 80);
        }
        game.batch.end();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() { font.dispose(); }
}
