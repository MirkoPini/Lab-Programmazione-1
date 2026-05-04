package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private Texture pipeDown;
    private Texture pipeUp;
    private SpriteBatch batch;
    private BitmapFont font;
    private Rectangle flappyBounds;
    private Rectangle groundBounds;
    private Rectangle pipeDownBounds;
    private Rectangle pipeUpBounds;

    private Sound jumpSound;
    private Sound fallSound;
    private Sound gameoverSound;
    private Music bgMusic;

    final float GRAVITY = -500;
    final float FLAP_FORCE = 250;

    private float velY = 0;
    private float flappyY = 200;
    private float flappyX = 50;

    private float pipeX = 200;
    private float pipeDY = 47;
    private int pipeW = 52;
    private int pipeDH = 180;
    private int pipeUH = 180;
    private float pipeUY = 450;
    private int minW = 20;
    private int maxW = 350;

    private int life = 3;
    private int point = 0;
    private boolean GameOver = false;

    @Override
    public void create() {
        flappyTex = new Texture("flappy.png");
        flappyBounds = new Rectangle(flappyX, flappyY, 56, 40);

        groundBounds = new Rectangle (0,0,Gdx.graphics.getWidth(),50);

        background = new Texture("background.png");

        pipeDown = new Texture("pipeDown.png");
        pipeUp = new Texture("pipeUp.png");
        pipeDownBounds = new Rectangle(pipeX, pipeDY, pipeW, pipeDH);
        pipeUpBounds = new Rectangle(pipeX, pipeUY, pipeW, pipeUH);

        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(1.2f);

        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sfx/jump.mp3"));
        fallSound = Gdx.audio.newSound(Gdx.files.internal("sfx/fall.wav"));
        gameoverSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gameover.mp3"));
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mp3"));
        bgMusic.setVolume(0.5f);
        bgMusic.play();
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
                long jumpId = jumpSound.play();
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

            pipeX -= 2.5 + point/5;

            if (pipeX + 52 <= 0) {
                int numero = (int)(Math.random() * (maxW - minW + 1)) + minW;
                pipeDownBounds.height = numero;
                pipeUpBounds.height = 550 - 180 - numero;
                pipeX = xD;
                point += 1;
            }

            if(flappyBounds.overlaps(pipeDownBounds) || flappyBounds.overlaps(pipeUpBounds) || flappyBounds.overlaps(groundBounds)){
                life -= 1;
                flappyY = 180;
                flappyX = 50;
                velY = 0;
                pipeX = 200;
                long fallId = fallSound.play();
            }

            if(life <= 0){
                GameOver = true;
                long gameoverId = gameoverSound.play();
                bgMusic.pause();
                resetPipes();
            }

        }

        //System.out.println(dt + " ; " + velY + " ; " + flappyY);
        pipeUY = 550 - pipeUpBounds.height;
        pipeUpBounds.y = pipeUY;
        flappyBounds.y = flappyY;
        pipeDownBounds.x = pipeX;
        pipeUpBounds.x = pipeX;

        ScreenUtils.clear(0,0,0,1);
        batch.begin();
        batch.draw(background, 0, 0, 350, 550);
        batch.draw(pipeDown, pipeX, pipeDY, pipeDownBounds.width, pipeDownBounds.height);
        batch.draw(pipeUp, pipeX, pipeUY, pipeUpBounds.width, pipeUpBounds.height);
        batch.draw(flappyTex, flappyX, flappyY, 56, 40);
        font.draw(batch, "Vita: " + life, 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Punti: " + point, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);
        if(GameOver){
            font.draw(batch, "GameOver", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                ReStartGame();
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        flappyTex.dispose();
        background.dispose();
        pipeDown.dispose();
        pipeUp.dispose();
        font.dispose();
        jumpSound.dispose();
        fallSound.dispose();
        bgMusic.dispose();
    }

    private void ReStartGame(){
        flappyY = 180;
        flappyX = 50;
        life = 3;
        point = 0;
        flappyBounds.y = flappyY;
        velY = 0;
        GameOver = false;
        bgMusic.play();
    }

    private void resetPipes(){
        pipeX = 200;
        pipeDownBounds.height = 180;
        pipeUpBounds.height = 180;
    }
}
