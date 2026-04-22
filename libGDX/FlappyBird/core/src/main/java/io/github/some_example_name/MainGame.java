package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends ApplicationAdapter {
    private Texture flappyTex;
    private Texture background;
    private Texture pipe;
    private SpriteBatch batch;
    private BitmapFont font;
    private Rectangle flappyBounds;
    private Rectangle groundBounds;
    private Rectangle pipeDownBounds;
    private Rectangle pipeUpBounds;

    final float GRAVITY = -700;
    final float FLAP_FORCE = 350;

    private float velY = 0;
    private float flappyY = 180;
    private float flappyX = 50;

    private float pipeX = 200;
    private float pipeY = 47;
    private int pipeW = 52;
    private int pipeDH = 200;
    private int pipeUH = 200;
    int minW = 20;
    int maxW = 320;

    private int life = 3;
    private boolean GameOver = false;

    @Override
    public void create() {
        flappyTex = new Texture("flappy.png");
        flappyBounds = new Rectangle(flappyX, flappyY, 56, 40);

        groundBounds = new Rectangle (0,0,Gdx.graphics.getWidth(),50);

        background = new Texture("background.png");

        pipe = new Texture("pipe.png");
        pipeDownBounds = new Rectangle(pipeX, pipeY, pipeW, pipeDH);

        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(1.2f);
    }

    @Override
    public void render() {
        float yD = Gdx.graphics.getHeight();
        float xD = Gdx.graphics.getWidth();
        float dt = Gdx.graphics.getDeltaTime();
        //Gestiamo la logica del gioco
        if(!GameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
                velY = FLAP_FORCE;
            }
            velY += GRAVITY * dt;

            flappyY += velY * dt;

            if (flappyY + 40 >= yD) {
                flappyY = yD - 40;
                velY = 0;
            }

            if (flappyY <= 45) {
                flappyY = 45;
                velY = -velY;
            }

            pipeX -= 2;
            if (pipeX + 52 <= 0) {
                int numero = (int)(Math.random() * (maxW - minW + 1)) + minW;
                pipeDownBounds.height = numero;
                pipeX = xD;
            }

            flappyBounds.y = flappyY;
            pipeDownBounds.x = pipeX;

            if (flappyBounds.overlaps(groundBounds)) {
                life -= 1;
                flappyY = 180;
                flappyX = 50;
                velY = 0;
            }

            if(flappyBounds.overlaps(pipeDownBounds) || flappyBounds.overlaps(pipeUpBounds)){
                GameOver = true;
            }

            if(life <= 0){
                GameOver = true;
            }


        } else if(GameOver){
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                ReStartGame();
            }
        }

        //System.out.println(dt + " ; " + velY + " ; " + flappyY);

        ScreenUtils.clear(0,0,0,1);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(pipe, pipeX, pipeY, pipeDownBounds.width, pipeDownBounds.height);
        batch.draw(flappyTex, flappyX, flappyY, 56, 40);
        font.draw(batch, "Vita: " + life, 20, Gdx.graphics.getHeight() - 20);
        if(GameOver){
            font.draw(batch, "GameOver", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        flappyTex.dispose();
        background.dispose();
        pipe.dispose();
        font.dispose();
    }

    private void ReStartGame(){
        flappyY = 180;
        flappyX = 50;
        pipeX = 200;
        life = 3;
        flappyBounds.y = flappyY;
        velY = 0;
        GameOver = false;
    }
}
