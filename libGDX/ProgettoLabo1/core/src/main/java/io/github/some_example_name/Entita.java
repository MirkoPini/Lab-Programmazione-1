package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entita implements Disegnabile {

    public float playerX;
    public float playerY;
    public float velY = 0;
    public int vita;

    public Entita(float x, float y, int vita) {
        this.playerX = x;
        this.playerY = y;
        this.vita = vita;
    }

    public abstract void movimento(float dt);
}
