package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Obstacle extends ObstacleComposition{
    public float startX;
    public float startY;

    public Obstacle(float startX) {
        this.startX = startX;
        this.startY = 205;
    }

    @Override
    public void popolaOstacolo() {
        if(getComposizione()[0] < 1) {
            for (int i = 0; i < getComposizione().length; i++) {
                Random random = new Random();

                int min = 1;
                int max = 5;

                int numero = random.nextInt(max - min + 1) + min;

                setComposizione(numero, i);
                System.out.println(numero);
            }
            setComposizione(3, 0);
            setComposizione(3, 6);
        }
    }
}
