package ru.spbau.ayakomarov.drunkard.object;

import ru.spbau.ayakomarov.drunkard.field.Cell;



public abstract class ObjectMove extends ObjectInCell implements IObjectMove , IObjectLive  {

    protected boolean changeCell( int directStep ) {

        Cell nowCell =  field.getCell(coordX, coordY);
        int newX, newY;

        Cell newCell = field.getNeighbor(this.coordX, this.coordY, directStep);

        newX = field.getNeighborX(this.coordX, this.coordY, directStep);
        newY = field.getNeighborY(this.coordX, this.coordY, directStep);

        if( newCell != null ){
            if ( newCell.object == null ) {
                // we must go
                nowCell.object = null;

                this.coordX = newX;
                this.coordY = newY;
                newCell.object = this;

                return true;

            } else {
                newCell.object.reactionBarrier(this);
            }
        }

        return  false;
    }
}
