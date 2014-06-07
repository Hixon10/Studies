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


    public SectorPolice(  Field field_, LiveContainer livecontainer_, Light light_){

        this.coordX = 3;
        this.coordY = field_.width-1;
        this.field = field_;
        this.livecontainer = livecontainer_;
        light = light_;

        this.livecontainer.addObjectLive(this);
        this.moveToGoal = new MoveToGoal(field);
        policeman = new Policeman(this.coordX, this.coordY, this.field, this);
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

    private boolean isHaveDrunkard(){
        return isHavePoliceman && light.haveDrunkard() &&
                field.getObject(coordX, coordY) == null;
    }
    @Override
    public void doStep() {

        if( isHaveDrunkard() ) {

            int drunkardX = light.getDrunkardX();
            int drunkardY = light.getDrunkardY();

            Stack<Integer> navigation = moveToGoal.getWay(coordX, coordY, drunkardX, light.drunkardY);
            policeman.giveInstructions(navigation, drunkardX, drunkardY);

            field.setObject(coordX, coordY, policeman);
            isHavePoliceman = false;
            policeman.inField = true;
        }
    }

}
