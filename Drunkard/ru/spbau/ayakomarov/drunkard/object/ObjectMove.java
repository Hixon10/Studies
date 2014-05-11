package ru.spbau.ayakomarov.drunkard.object;

import ru.spbau.ayakomarov.drunkard.field.Cell;



public abstract class ObjectMove extends ObjectInCell implements IObjectMove , IObjectLive  {

    protected boolean changeCell( Integer directStep ) {

        Cell nowCell =  this.field.cells[this.coordX][this.coordY];
        int newX, newY;
        if( directStep == 0) {          // North
            newX = this.coordX - 1;
            newY = this.coordY;
        }
        else if(directStep == 1) {      // South
            newX = this.coordX + 1;
            newY = this.coordY;
        }
        else if(directStep == 2) {      // West
            newX = this.coordX;
            newY = this.coordY - 1;
        } else {                        // East
            newX = this.coordX;
            newY = this.coordY + 1;
        }
        if( (0 <= newX && newX < this.field.width) && (0 <= newY && newY < this.field.height) ) {

            Cell newСell = this.field.cells[newX][newY];

            if ( newСell.object == null ) {
                // we must go
                nowCell.object = null;

                this.coordX = newX;
                this.coordY = newY;
                newСell.object = this;

                return true;

            } else {
                newСell.object.reactionBarrier(this);
            }
        }

        return  false;
    }
}
