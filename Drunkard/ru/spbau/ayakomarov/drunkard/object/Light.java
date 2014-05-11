package ru.spbau.ayakomarov.drunkard.object;


import ru.spbau.ayakomarov.drunkard.field.Field;

public class Light extends ObjectInCell {

    Field field;
    int drunkardX;
    int drunkardY;

    public int getDrunkardX() {
        return drunkardX;
    }

    public int getDrunkardY() {
        return drunkardY;
    }

    public Light(int x, int y, Field field_) {
        coordX = x;
        coordY = y;
        field = field_;
    }

    boolean haveDrunkard() {

        for(int i=-3; i<=3; i++) {
         for (int j=-3; j <=3; j++) {
            if(  /*StrictMath.abs(i) + StrictMath.abs(j) <= 3 &&*/
                field.cells[coordX+i][coordY+j].object != null &&
                (field.cells[coordX+i][coordY+j].object.view() == 'Z' ||
                 field.cells[coordX+i][coordY+j].object.view() == '&') ) {

                drunkardX = coordX + i;
                drunkardY = coordY + j;

                return true;
            }
         }
        }

        return false;
    }

    @Override
    public char view() {
        return 'L';
    }

    @Override
    public void reactionBarrier(IObjectMove object) {
        object.meetBarrier(this);
    }
}
