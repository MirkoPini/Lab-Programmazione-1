package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class WorldLoader {

    public static final float LARGHEZZA_OSTACOLO = 120;

    public static final float ALTEZZA_OSTACOLO = 120;

    public static final float PAVIMENTO = 205f;

    public static final int ARIA  = 0;
    public static final int CASSA = 1;
    public static final int LAVA  = 2;
    public static final int MURO  = 3;
    public static final int SPIKE = 4;
    public static final int TERRA = 5;
    public static final int COIN = 6;
    public static final int DOOR = 7;

    private int[][] griglia_mappa;

    private int righe;
    private int colonne;

    public WorldLoader(String nomeFile) {
        caricaMappa(nomeFile);
    }

    private void caricaMappa(String nomeFile) {
        try {
            FileHandle file = Gdx.files.internal(nomeFile);
            String contenuto = file.readString();

            String[] linee = contenuto.split("\\r?\\n");
            List<String> righeValide = new ArrayList<>();

            for (String linea : linee) {
                if (!linea.trim().isEmpty()) {
                    righeValide.add(linea);
                }
            }

            righe = righeValide.size();
            colonne = 0;
            for (String linea : righeValide) {
                if (linea.length() > colonne) {
                    colonne = linea.length();
                }
            }

            griglia_mappa = new int[righe][colonne];

            //Ciclo generato da AI
            for (int r = 0; r < righe; r++) {
                String linea = righeValide.get(r);
                for (int c = 0; c < colonne; c++) {
                    if (c < linea.length()) {
                        char ch = linea.charAt(c);
                        int valore = Character.getNumericValue(ch);
                        griglia_mappa[r][c] = (valore >= ARIA && valore <= DOOR) ? valore : ARIA;
                    } else {
                        griglia_mappa[r][c] = ARIA;
                    }
                }
            }
        }catch (GdxRuntimeException e){
            System.out.println("Impossibile leggere il file");
        }

    }

    public int getOstacolo(int riga, int colonna) {
        if (riga < 0 || riga >= righe || colonna < 0 || colonna >= colonne) {
            return ARIA;
        }
        return griglia_mappa[riga][colonna];
    }

    public float getWorldX(int colonna) {
        return colonna * LARGHEZZA_OSTACOLO;
    }

    public float getWorldY(int riga) {
        int righeInvert = (righe - 1) - riga;
        return PAVIMENTO + righeInvert * ALTEZZA_OSTACOLO;
    }

    public float getLarghezzaMondo() {
        return colonne * LARGHEZZA_OSTACOLO;
    }

    public int getColonne() {
        return colonne;
    }

    public int getRighe() {
        return righe;
    }

    public List<Ostacolo> getOstacoloNonAria() {
        List<Ostacolo> lista = new ArrayList<>();
        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                int tipo = griglia_mappa[r][c];
                if (tipo != ARIA) {
                    float wx = getWorldX(c);
                    float wy = getWorldY(r);
                    lista.add(new Ostacolo(tipo, wx, wy, LARGHEZZA_OSTACOLO, ALTEZZA_OSTACOLO));
                }
            }
        }
        return lista;
    }


    public static class Ostacolo {
        public final int tipo;
        public float worldX;
        public float worldY;
        public final float width;
        public final float height;

        public Ostacolo(int tipo, float worldX, float worldY, float width, float height) {
            this.tipo   = tipo;
            this.worldX = worldX;
            this.worldY = worldY;
            this.width  = width;
            this.height = height;
        }
    }
}
