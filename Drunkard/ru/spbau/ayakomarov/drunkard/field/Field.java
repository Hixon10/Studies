package ru.spbau.ayakomarov.drunkard.field;

import ru.spbau.ayakomarov.drunkard.object.ObjectInCell;

public abstract class Field {

    public final int width;
    public final int height;
    public final Cell[][] cells;
    public final int countDirects;

    public Field(int w, int h, int countDirects) {
        this.countDirects = countDirects;
        width=w;
        height=h;
        cells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    abstract public boolean isFreeCell(int x, int y);
    //abstract public Iterator<Integer> iterator();
    abstract public boolean isBoundaries(int x, int y);
    abstract public Cell getNeighbor( int i, int j, int direction);
    abstract public int getNeighborX( int i, int j, int direction);
    abstract public int getNeighborY( int i, int j, int direction);
    abstract public int directRevers(int direction);
    abstract public int getNavigable(int sx, int ex, int sy, int ey);

    public void setObject(int x, int y, ObjectInCell obj){
        cells[x][y].object = obj;
    }
    public ObjectInCell getObject(int x, int y){
        return cells[x][y].object;
    }

    public Cell getCell(int x, int y){
        return cells[x][y];
    }

    abstract public void view();

}
