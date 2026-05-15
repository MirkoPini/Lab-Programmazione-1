package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
    public Rectangle bounds;
    public Texture texture;
    public boolean active;

    public Obstacle(Texture tex, float x, float y, float w, float h) {
        this.texture = tex;
        this.bounds = new Rectangle(x, y, w, h);
        this.active = true;
    }
}
