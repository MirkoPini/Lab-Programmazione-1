package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Disegnabile {
    void disegna(SpriteBatch batch, float screenX, float screenY);
    void dispose();
}
