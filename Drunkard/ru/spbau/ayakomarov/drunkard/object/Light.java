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

    boolean haveDrunkard(int x, int y, int depth) {

        if(depth > 3) return false;

        for(int direct=0; direct < field.countDirects; direct++) {
            int nx = field.getNeighborX(x,y,direct);
            int ny = field.getNeighborY(x, y, direct);

            if( field.isBoundaries(nx,ny) ) {
                if( isSleepDrunkard(nx, ny) ) {
                    drunkardX = nx;
                    drunkardY = ny;
                    return true;
                }
                if( haveDrunkard(nx, ny, depth+1) ) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean haveDrunkard() {
        return haveDrunkard(coordX, coordY, 0);
    }

    boolean isSleepDrunkard(int nx, int ny){
        return field.getObject(nx, ny) != null &&
                (field.getObject(nx, ny).view() == 'Z' || field.getObject(nx, ny).view() == '&');
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
