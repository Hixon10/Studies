package ru.spbau.ayakomarov.drunkard.field;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class StandardField extends Field {


    public StandardField(int w, int h) {
        super(w, h, 4);
    }

    @Override
    public boolean isBoundaries(int x, int y) {
        return  (0 <= x && x < this.height) &&
                (0 <= y && y < this.width);
    }

    @Override
    public boolean isFreeCell(int x, int y) {
        return  isBoundaries(x, y) &&
                this.cells[x][y].object == null;
    }

    @Override
    public Cell getNeighbor( int i, int j, int direction) {

        if(direction == 0) {
            if(isBoundaries(i-1,j))
                return cells[i-1][j];
        }
        else if(direction == 1) {
            if(isBoundaries(i+1,j))
                return cells[i+1][j];
        }
        else if(direction == 2) {
            if(isBoundaries(i,j-1))
                return cells[i][j-1];
        }
        else {
            if(isBoundaries(i,j+1))
                return cells[i][j+1];
        }

        return null;
    }

    @Override
    public int getNeighborX( int i, int j, int direction) {
        if(direction == 0) {
                return i-1;
        }
        else if(direction == 1) {
                return i+1;
        }
        else if(direction == 2) {
                return i;
        }
        else {
                return i;
        }
    }

    @Override
    public int getNeighborY( int i, int j, int direction) {

        if(direction == 0) {
                return j;
        }
        else if(direction == 1) {
                return j;
        }
        else if(direction == 2) {
                return j-1;
        }
        else {
                return j+1;
        }
    }

    @Override
    public int directRevers(int direction){

        if(direction == 0) {
            return 1;
        }
        else if(direction == 1) {
            return 0;
        }
        else if(direction == 2) {
            return 3;
        }
        else {
            return 2;
        }
    }

    @Override
    public int getNavigable(int sx, int ex, int sy, int ey) {

        if( sx - ex == -1 && sy - ey == 0) {
            return 0; // North
        } else if(sx - ex == 1 && sy - ey == 0) {
            return 1; // South
        } else if(sx - ex == 0 && sy - ey == -1) {
            return 2; // West
        } else if(sx - ex == 0 && sy - ey == 1) {
            return 3; // East
        }
        
        return -1;
    }

    @Override
    public void view(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if( cells[i][j].object != null ){
                    char ch = cells[i][j].object.view();
                    System.out.printf(" %c ", ch);
                } else {
                    System.out.printf(" . ");
                }

            }
            System.out.printf("\n");
        }
    }
}
