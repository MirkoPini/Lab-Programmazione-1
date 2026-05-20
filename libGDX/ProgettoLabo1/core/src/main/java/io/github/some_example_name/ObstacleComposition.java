package io.github.some_example_name;

import java.util.ArrayList;

abstract class ObstacleComposition {
    private int[] composizione;
    private float[] altezzaOstacoli;

    //Ostacoli
    final int cassa = 1;
    final int lava = 2;
    final int muro = 3;
    final int spike = 4;
    final int terra = 5;

    public ObstacleComposition(){
        composizione = new int[15];
        altezzaOstacoli = new float[15];
    }

    public int[] getComposizione() {
        return composizione;
    }

    public void setComposizione(int elemento, int index) {
        composizione[index] = elemento;
    }

    public float[] getAltezzaOstacoli() {
        return altezzaOstacoli;
    }

    public void setAltezzaOstacoli(float elemento, int index) {
        altezzaOstacoli[index] = elemento;
    }

    public int getCassa() {
        return cassa;
    }

    public int getLava() {
        return lava;
    }

    public int getMuro() {
        return muro;
    }

    public int getSpike() {
        return spike;
    }

    public int getTerra() {
        return terra;
    }

    abstract void popolaOstacolo();

    abstract void popolaAltezzaOstacoli();
}
