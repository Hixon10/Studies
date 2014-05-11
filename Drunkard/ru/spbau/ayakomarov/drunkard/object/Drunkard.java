package ru.spbau.ayakomarov.drunkard.object;

import ru.spbau.ayakomarov.drunkard.field.Cell;
import ru.spbau.ayakomarov.drunkard.field.Field;

import java.util.Random;


public class Drunkard extends  ObjectMove {

    private static final Random random = new Random();
    public static enum DrunkardType {
        FRESH,
        SLEEPING,
        RECUMBENT
    }

    public DrunkardType type;
    private Bottle bottle = new Bottle();

    public Drunkard(int x, int y, Field f) {
        this.coordX = x;
        this.coordY = y;
        type = DrunkardType.FRESH;
        this.field = f;
    }

    @Override
    public void doStep(){

        if( this.type != DrunkardType.FRESH ) {
            return;
        }



        Cell nowCell = this.field.cells[this.coordX][this.coordY];

        int directStep = random.nextInt(4);

        if( changeCell(directStep) ) {

            if( bottle != null && random.nextInt(30)==15) {
                nowCell.object = bottle;
                this.bottle = null;
            } else {
                nowCell.object = null;
            }
        }

    }

    @Override
    public char view(){
        switch (this.type) {
            case FRESH:
                return 'D';
            case SLEEPING:
                return 'Z';
            case RECUMBENT:
                return '&';
        }
        return '?';
    }

    @Override
    public void reactionBarrier(IObjectMove object) {
         object.meetBarrier(this);
    }

    @Override
    public void meetBarrier(Column object){
        this.type = DrunkardType.SLEEPING;
    }

    @Override
    public void meetBarrier(Drunkard object){
        if( object.type == DrunkardType.SLEEPING ) {
            this.type = DrunkardType.SLEEPING;
        }
    }

    @Override
    public void meetBarrier(Bottle object){
        this.type = DrunkardType.RECUMBENT;
    }

    @Override
    public void meetBarrier(Light object){
        // nothing
    }

    @Override
    public void meetBarrier(SectorPolice object){
        // nothing
    }

    @Override
    public void meetBarrier(Beggar object){

    }

    @Override
    public void meetBarrier(Policeman object){

    }

}
