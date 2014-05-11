package ru.spbau.ayakomarov.drunkard.field;

public class Field {

    public final int width;
    public final int height;
    public final Cell[][] cells;

    public Field(int w, int h) {
        width=w;
        height=h;
        cells = new Cell[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

}
