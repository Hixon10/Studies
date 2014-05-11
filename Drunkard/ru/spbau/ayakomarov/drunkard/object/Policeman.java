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
    public boolean inField = true;


    Policeman(int x, int y, Field field1_, SectorPolice sectorPolice_) {

        this.coordX = x;
        this.coordY = y;
        this.field = field1_;
        this.sectorPolice = sectorPolice_;
        this.moveToGoal = new MoveToGoal(field1_);
    }

    public void giveInstructions( Stack<Integer> navigation_, int goalX_, int goalY_ ) {

        this.navigation = navigation_;
        this.goalX = goalX_;
        this.goalY = goalY_;
    }

    @Override
    public void doStep(){

        if( !inField ) {
            return;
        }


        if( coordX == sectorPolice.coordX && coordY == sectorPolice.coordY &&
                isHaveDrunkard == true) {

            isHaveDrunkard = false;
            field.cells[coordX][coordY].object = null;
            inField = false;
            sectorPolice.isHavePoliceman = true;

            return;
        }

        if( navigation == null || navigation.isEmpty() ) {
            return;
        }

        Integer directStep = navigation.pop();

        changeCell(directStep);


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

            field.cells[goalX][goalY].object = null;

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