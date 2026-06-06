package io.github.some_example_name;

public class Collision {

    /**
     *
     * Verifica se due rettangoli si sovrappongono
     *
     * @param x1 coordinata X del bordo sinistro del primo rettangolo (mondo)
     * @param y1 coordinata Y del bordo inferiore del primo rettangolo (mondo)
     * @param w1 larghezza del primo rettangolo in pixel
     * @param h1 altezza del primo rettangolo in pixel
     * @param x2 coordinata X del bordo sinistro del secondo rettangolo (mondo)
     * @param y2 coordinata Y del bordo inferiore del secondo rettangolo (mondo)
     * @param w2 larghezza del secondo rettangolo in pixel
     * @param h2 altezza del secondo rettangolo in pixel
     * @return se i due rettangoli si sovrappongono
     */

    //Generata con AI
    public static boolean collisione(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
}
