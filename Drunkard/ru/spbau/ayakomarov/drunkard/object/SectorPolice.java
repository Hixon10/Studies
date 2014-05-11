package ru.spbau.ayakomarov.drunkard.object;

import ru.spbau.ayakomarov.drunkard.field.LiveContainer;
import ru.spbau.ayakomarov.drunkard.field.Field;
import ru.spbau.ayakomarov.drunkard.moveAlgorithm.MoveToGoal;

import java.util.Stack;


public class SectorPolice extends ObjectInCell implements IObjectLive{

    LiveContainer livecontainer;
    Field field;
    MoveToGoal moveToGoal;
    boolean isHavePoliceman = true;
    Policeman policeman;
    Light light;


    public SectorPolice( int x, int y, Field field_, LiveContainer livecontainer_, Light light_){

        this.coordX = x;
        this.coordY = y;
        this.field = field_;
        this.livecontainer = livecontainer_;
        light = light_;

        this.livecontainer.addObjectLive(this);
        this.moveToGoal = new MoveToGoal(field);
        policeman = new Policeman(x, y, this.field, this);
        livecontainer.addObjectLive(policeman);

    }

    @Override
    public char view() {
        return 'S';
    }

    @Override
    public void reactionBarrier(IObjectMove object) {
        object.meetBarrier(this);
    }


    @Override
    public void doStep() {

        if( isHavePoliceman && light.haveDrunkard() &&
                field.cells[coordX][coordY].object == null ) {

            int drunkardX = light.getDrunkardX();
            int drunkardY = light.getDrunkardY();

            Stack<Integer> navigation = moveToGoal.getWay(coordX, coordY, drunkardX, light.drunkardY);



            policeman.giveInstructions(navigation, drunkardX, drunkardY);
            this.field.cells[coordX][coordY].object = policeman;
            isHavePoliceman = false;
            policeman.inField = true;

        }
    }

}
