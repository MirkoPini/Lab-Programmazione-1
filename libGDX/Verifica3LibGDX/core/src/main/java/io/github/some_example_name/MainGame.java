package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends ApplicationAdapter {
    private Texture yoshi;
    private SpriteBatch batchA;
    private SpriteBatch batchB;
    private SpriteBatch batchC;

    private int velXA = 300;
    private int velYA = 300;

    private int velXB = 300;
    private int velYB = 300;

    private int velXC = 200;
    private int velYC = 200;

    private float spostamentoXA = 50;
    private float spostamentoYA = 240;
    private float spostamentoXB = 320;
    private float spostamentoYB = 400;
    private float spostamentoXC = (float) Math.random() * 590;
    private float spostamentoYC = (float) Math.random() * 430;

    private boolean collisione = false;

    @Override
    public void create() {
        yoshi = new Texture("yoshi.jpg");
        batchA = new SpriteBatch();
        batchB = new SpriteBatch();
        batchC = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0,0,0,1);

        float delta = Gdx.graphics.getDeltaTime();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        //Yoshi A
        if(!collisione) {
            spostamentoXA += velXA * delta;
            if (spostamentoXA < 0 || spostamentoXA + 60 > width) {
                velXA = -velXA;
            }

            //Yoshi B
            spostamentoYB += velYB * delta;
            if (spostamentoYB < 0 || spostamentoYB + 60 > height) {
                velYB = -velYB;
            }

            //Yoshi C
            spostamentoYC += velYC * delta;
            spostamentoXC += velXC * delta;
            if (spostamentoYC < 0 || spostamentoYC + 60 > height) {
                velYC = -velYC;
            }
            if (spostamentoXC < 0 || spostamentoXC + 60 > width) {
                velXC = -velXC;
            }
        } else if(collisione){
            //Yoshi B
            spostamentoXB += velXB * delta;
            if (spostamentoXB < 0 || spostamentoXB + 60 > width) {
                velXB = -velXB;
            }

            //Yoshi A
            spostamentoYA += velYA * delta;
            if (spostamentoYA < 0 || spostamentoYA + 60 > height) {
                velYA = -velYA;
            }

            //Yoshi C
            spostamentoYC += velYC * delta;
            spostamentoXC += velXC * delta;
            if (spostamentoYC < 0 || spostamentoYC + 60 > height) {
                velYC = -velYC;
            }
            if (spostamentoXC < 0 || spostamentoXC + 60 > width) {
                velXC = -velXC;
            }
        }

        //Collisione Yoshi A e Yoshi B
        if(spostamentoXA == spostamentoXB && spostamentoYA ==  spostamentoYB){
            System.out.println("Collisione AB");
            collisione = !collisione;
        }

        //Collisione Yoshi C
        if((spostamentoXC == spostamentoXA && spostamentoYC == spostamentoYA) || (spostamentoXC == spostamentoXB && spostamentoYC == spostamentoYB)){
            System.out.println("Collisione C");
            spostamentoXC = (float) Math.random() * 590;
            spostamentoYC = (float) Math.random() * 430;
        }

        batchA.begin();
        batchB.begin();
        batchC.begin();

        batchA.draw(yoshi, spostamentoXA , spostamentoYA, 60, 60);
        batchB.draw(yoshi, spostamentoXB, spostamentoYB, 60, 60);
        batchC.draw(yoshi, spostamentoXC, spostamentoYC, 60, 60);


        batchA.end();
        batchB.end();
        batchC.end();


    }

    @Override
    public void dispose() {
        batchA.dispose();
        batchB.dispose();
        batchC.dispose();
        yoshi.dispose();
    }
}
