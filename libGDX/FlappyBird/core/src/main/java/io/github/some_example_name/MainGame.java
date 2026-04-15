package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import sun.jvm.hotspot.gc.shared.Space;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends ApplicationAdapter {
    private Texture flappyTex;
    private Texture background;
    private Texture pipe;
    private SpriteBatch batch;
    final float GRAVITY = -500;
    final float FLAP_FORCE = 350;
    private float velY = 0;
    private float flappyY = 180;

    @Override
    public void create() {
        flappyTex = new Texture("flappy.png");
        background = new Texture("background.png");
        pipe = new Texture("pipe.png");
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        float yD = Gdx.graphics.getHeight();
        float dt = Gdx.graphics.getDeltaTime();
        //Gestiamo la logica del gioco
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()){
            velY = FLAP_FORCE;
        }
        velY += GRAVITY * dt;

        flappyY += velY * dt;

        if(flappyY + 40 >= yD){
            flappyY = yD - 40;
            velY = -velY;
        }

        if(flappyY <= 45){
            flappyY = 45;
            velY = -velY;
        }

        System.out.println(dt + " ; " + velY + " ; " + flappyY);

        ScreenUtils.clear(0,0,0,1);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(pipe, 200, 47);
        batch.draw(flappyTex, 50, flappyY, 56, 40);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        flappyTex.dispose();
        background.dispose();
        pipe.dispose();
    }
}
