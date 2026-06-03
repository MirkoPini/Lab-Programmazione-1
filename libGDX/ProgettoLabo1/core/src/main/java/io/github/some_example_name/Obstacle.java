package io.github.some_example_name;

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
        if (getComposizione()[0] < 1) {
            Random random = new Random();
            int lunghezza = getComposizione().length;

            setComposizione(3, 0);
            setComposizione(3, lunghezza - 1);

            for (int i = 1; i < lunghezza - 1; i++) {
                int numeroPrecedente = getComposizione()[i - 1];
                int numero;
                int tentativi = 0;

                do {
                    int min = 1;
                    int max = 5;
                    numero = random.nextInt(max - min + 1) + min;
                    tentativi++;

                    if (tentativi > 100) break;

                } while (
                    numero == numeroPrecedente ||
                        ((numeroPrecedente == 2 || numeroPrecedente == 4) && (numero == 2 || numero == 4))
                );
                setComposizione(numero, i);
            }
        }
    }

    @Override
    void popolaAltezzaOstacoli() {
        if(getAltezzaOstacoli()[0] < 1) {
            for (int i = 0; i < getAltezzaOstacoli().length; i++) {
                Random random = new Random();

                float min = 120;
                float max = 200;

                float numero = min + random.nextFloat() * (max - min);

                setAltezzaOstacoli(numero, i);
            }
        }
    }
}
