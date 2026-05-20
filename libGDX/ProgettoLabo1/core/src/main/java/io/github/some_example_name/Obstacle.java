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
        if(getComposizione()[0] < 1) {

            int ultimoNumero = 0;
            boolean terzoNumero = false;

            for (int i = 0; i < getComposizione().length; i++) {
                Random random = new Random();

                int min = 1;
                int max = 5;

                int numero = random.nextInt(max - min + 1) + min;

                if(numero == ultimoNumero && terzoNumero) {
                    numero += 1;
                    terzoNumero = false;
                    setComposizione(numero, i);
                }else if(numero == ultimoNumero){
                    terzoNumero = true;
                } else{
                    setComposizione(numero, i);
                }

                System.out.println(numero);
                ultimoNumero = numero;
            }
            setComposizione(3, 0);
            setComposizione(3, 6);
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
