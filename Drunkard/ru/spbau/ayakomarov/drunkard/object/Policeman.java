package ru.spbau.ayakomarov.drunkard.object;

import com.sun.jmx.remote.internal.ArrayQueue;
import ru.spbau.ayakomarov.drunkard.field.Cell;
import ru.spbau.ayakomarov.drunkard.field.Field;
import ru.spbau.ayakomarov.drunkard.moveAlgorithm.MoveToGoal;

import java.util.Stack;

public class Policeman extends  ObjectMove {

    SectorPolice sectorPolice;
    private MoveToGoal moveToGoal;
    Stack<Integer> navigation;
    int goalX;
    int goalY;
    public boolean isHaveDrunkard = false;
    public boolean inField = false;
    private int countWait;
    final private int limitWait = 15;


    Policeman(int x, int y, Field field1_, SectorPolice sectorPolice_) {

        this.coordX = x;
        this.coordY = y;
        this.field = field1_;
        this.sectorPolice = sectorPolice_;
        this.moveToGoal = new MoveToGoal(field1_);
        countWait = 0;
    }

    public void giveInstructions( Stack<Integer> navigation, int goalX, int goalY ) {

        this.navigation = navigation;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    @Override
    public void doStep(){

        if( !inField ) {
            return;
        }

        if( isBackHome() ) {

            isHaveDrunkard = false;
            field.setObject(coordX, coordY, null);
            inField = false;
            sectorPolice.isHavePoliceman = true;

            return;
        }

        if( navigation == null && inField) {
            countWait++;
            if(countWait <= limitWait) {
                navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
                return;
            }
            isHaveDrunkard = true;
            goalX = sectorPolice.coordX;
            goalY = sectorPolice.coordY;
            countWait = 0;
            return;
        }

        if( navigation == null || navigation.isEmpty()) {
            return;
        }

        Integer directStep = navigation.pop();
        changeCell(directStep);

    }

    private boolean isBackHome(){
        return coordX == sectorPolice.coordX && coordY == sectorPolice.coordY &&
                isHaveDrunkard == true;
    }

    /////////////////////////////////////////////////

    @Override
    public char view() {
        return 'P';
    }

    @Override
    public void reactionBarrier(IObjectMove object) {
        object.meetBarrier(this);
    }

    @Override
    public void meetBarrier(Column object){
        navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }

    @Override
    public void meetBarrier(Drunkard object){

        if( object.coordX == goalX && object.coordY == goalY &&
                (object.view() == 'Z' || object.view() == '&') &&
                 !isHaveDrunkard ) {

            field.setObject(goalX, goalY, null);

            goalX = sectorPolice.coordX;
            goalY = sectorPolice.coordY;

            isHaveDrunkard = true;
        }

        navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }

    @Override
    public void meetBarrier(Bottle object){
        navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }

    @Override
    public void meetBarrier(Light object){
        navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }

    @Override
    public void meetBarrier(SectorPolice object){
        navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }

    @Override
    public void meetBarrier(Beggar object){
        navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }

    @Override
    public void meetBarrier(Policeman object){
        navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }
}