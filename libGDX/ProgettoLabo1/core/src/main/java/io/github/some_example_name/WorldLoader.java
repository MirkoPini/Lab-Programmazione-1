package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * Carica la mappa del mondo da un file .txt.
 *
 * Formato del file:
 *   - Ogni riga di testo = una riga di tile nella mappa
 *   - La PRIMA riga del file = la riga PIÙ IN ALTO del mondo
 *   - L'ULTIMA riga del file = il pavimento (y = 205)
 *   - Ogni carattere = un tile largo TILE_SIZE pixel
 *
 * Valori dei tile:
 *   0 = aria (nessun ostacolo)
 *   1 = cassa
 *   2 = lava
 *   3 = muro
 *   4 = spike
 *   5 = terra
 */
public class WorldLoader {

    /** Larghezza in pixel di ogni tile nel mondo */
    public static final float TILE_SIZE = 120f;

    /** Altezza in pixel di ogni tile (uguale alla larghezza per semplicità) */
    public static final float TILE_HEIGHT = 120f;

    /** Y del pavimento di base (uguale a playerY di default) */
    public static final float BASE_Y = 205f;

    // Costanti dei tile (stessi valori di ObstacleComposition)
    public static final int ARIA  = 0;
    public static final int CASSA = 1;
    public static final int LAVA  = 2;
    public static final int MURO  = 3;
    public static final int SPIKE = 4;
    public static final int TERRA = 5;

    /** Griglia dei tile: [riga][colonna]. Riga 0 = in cima, ultima riga = pavimento. */
    private int[][] griglia;

    /** Numero di righe e colonne della mappa */
    private int righe;
    private int colonne;

    /**
     * Carica la mappa dal file indicato (percorso relativo alla cartella assets).
     * Esempio: WorldLoader loader = new WorldLoader("world.txt");
     */
    public WorldLoader(String nomeFile) {
        caricaMappa(nomeFile);
    }

    private void caricaMappa(String nomeFile) {
        FileHandle file = Gdx.files.internal(nomeFile);
        String contenuto = file.readString();

        // Dividi per righe, ignora righe vuote
        String[] linee = contenuto.split("\\r?\\n");
        List<String> righeValide = new ArrayList<>();
        for (String linea : linee) {
            if (!linea.trim().isEmpty()) {
                righeValide.add(linea);
            }
        }

        righe = righeValide.size();
        // Prendi la lunghezza massima tra tutte le righe
        colonne = 0;
        for (String linea : righeValide) {
            if (linea.length() > colonne) colonne = linea.length();
        }

        griglia = new int[righe][colonne];

        for (int r = 0; r < righe; r++) {
            String linea = righeValide.get(r);
            for (int c = 0; c < colonne; c++) {
                if (c < linea.length()) {
                    char ch = linea.charAt(c);
                    int valore = Character.getNumericValue(ch);
                    // Valori validi: 0-5
                    griglia[r][c] = (valore >= ARIA && valore <= TERRA) ? valore : ARIA;
                } else {
                    griglia[r][c] = ARIA;
                }
            }
        }

        Gdx.app.log("WorldLoader", "Mappa caricata: " + righe + " righe x " + colonne + " colonne");
    }

    /**
     * Restituisce il tipo di tile alla posizione griglia [riga][colonna].
     * Riga 0 = in cima alla mappa, riga (righe-1) = pavimento.
     */
    public int getTile(int riga, int colonna) {
        if (riga < 0 || riga >= righe || colonna < 0 || colonna >= colonne) return ARIA;
        return griglia[riga][colonna];
    }

    /**
     * Converte la colonna della griglia in coordinata X del mondo (in pixel).
     * La colonna 0 corrisponde a X = 0.
     */
    public float getWorldX(int colonna) {
        return colonna * TILE_SIZE;
    }

    /**
     * Converte la riga della griglia in coordinata Y del mondo (in pixel).
     * L'ultima riga (pavimento) corrisponde a Y = BASE_Y.
     * Le righe superiori aumentano in Y.
     */
    public float getWorldY(int riga) {
        // L'ultima riga è il pavimento (BASE_Y), le righe superiori salgono
        int righeInvert = (righe - 1) - riga;
        return BASE_Y + righeInvert * TILE_HEIGHT;
    }

    /**
     * Larghezza totale del mondo in pixel.
     */
    public float getLarghezzaMondo() {
        return colonne * TILE_SIZE;
    }

    /**
     * Numero di colonne della mappa.
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Numero di righe della mappa.
     */
    public int getRighe() {
        return righe;
    }

    /**
     * Restituisce la lista di tutti i WorldTile non-aria con le loro
     * coordinate mondo. Usata da MainGame per costruire i rettangoli
     * di collisione e disegnare gli sprite.
     */
    public List<WorldTile> getTileNonAria() {
        List<WorldTile> lista = new ArrayList<>();
        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                int tipo = griglia[r][c];
                if (tipo != ARIA) {
                    float wx = getWorldX(c);
                    float wy = getWorldY(r);
                    lista.add(new WorldTile(tipo, wx, wy, TILE_SIZE, TILE_HEIGHT));
                }
            }
        }
        return lista;
    }

    // -------------------------------------------------------
    // Classe interna: rappresenta un singolo tile nel mondo
    // -------------------------------------------------------

    public static class WorldTile {
        public final int tipo;   // CASSA, LAVA, MURO, SPIKE, TERRA
        public float worldX;     // X nel mondo (non nella schermata)
        public final float worldY;
        public final float width;
        public final float height;

        public WorldTile(int tipo, float worldX, float worldY, float width, float height) {
            this.tipo   = tipo;
            this.worldX = worldX;
            this.worldY = worldY;
            this.width  = width;
            this.height = height;
        }
    }
}
