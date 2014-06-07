package ru.spbau.ayakomarov.drunkard.object;

import ru.spbau.ayakomarov.drunkard.field.Field;
import ru.spbau.ayakomarov.drunkard.moveAlgorithm.MoveToGoal;

import java.util.Stack;


public class Beggar extends  ObjectMove {

    ReceiverGlass receiverGlass;
    private MoveToGoal moveToGoal;
    Stack<Integer> navigation;
    int goalX;
    int goalY;
    private boolean isHaveBottle = false;
    public boolean isRelax = false;
    public int count = 30;


    Beggar(int x, int y, Field field1_, ReceiverGlass receiverGlass_) {

        this.receiverGlass = receiverGlass_;
        this.coordX = x;
        this.coordY = y;
        this.field = field1_;
        this.moveToGoal = new MoveToGoal(field);
        this.goalX = 0;
        this.goalY = 14;
        this.navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
    }

    @Override
    public void doStep(){

        if( isRelax == true && ( count < 30 ||
                        ( field.cells[receiverGlass.startX][receiverGlass.startY].object != null &&
                          field.getObject(receiverGlass.startX, receiverGlass.startY).view() != 'z'))) {
            count++;
            return;
        }

        if( navigation == null) { // not way
            navigation = moveToGoal.getWay(coordX, coordY, goalX, goalY);
            return;
        }

        isRelax = false;

        if( this.isHaveBottle == true && coordX == receiverGlass.startX && coordY == receiverGlass.startY ) {

            field.setObject(receiverGlass.startX, receiverGlass.startY, null);
            isRelax = true;
            isHaveBottle = false;
            count = 0;
            goalX = 0;
            goalY = 14;
            return;
        }


        for(int direct =0; direct < field.countDirects; direct++){
            int x = field.getNeighborX(coordX, coordY, direct);
            int y = field.getNeighborY(coordX, coordY, direct);
            if(isBottle(x,y)) {
                getBottle(x,y);
            }
        }

        if( navigation.isEmpty() ) {

            changeDirect();
            return;
        }


        Integer directStep = navigation.pop();

        changeCell(directStep);

    }

    private void getBottle(int x, int y) {

        field.setObject(x, y, null);
        isHaveBottle = true;
        goalX = receiverGlass.startX;
        goalY = receiverGlass.startY;
        navigation = moveToGoal.getWay(coordX,coordY, goalX, goalY);
    }

    private boolean isBottle(int x, int y) {

        return  field.isBoundaries(x, y)
                && field.getObject(x, y) != null
                && field.getObject(x, y).view() == 'B';
    }

    private void changeDirect() {

        if( isHaveBottle ) {
            return;
        }

        if( goalY == 14) {
            goalY = 0;
        } else {
            goalY = 14;
        }
        goalX = (goalX + 1) % field.height;
        navigation = moveToGoal.getWay(coordX,coordY, goalX, goalY);
    }

    /////////////////////////////////////////////////

    @Override
    public char view() {
        return 'z';
    }

    @Override
    public void reactionBarrier(IObjectMove object) {
        object.meetBarrier(this);
    }

    @Override
    public void meetBarrier(Column object){
        changeDirect();
    }

    @Override
    public void meetBarrier(Drunkard object){
        changeDirect();
    }

    @Override
    public void meetBarrier(Bottle object){
        // it will never happen
    }

    @Override
    public void meetBarrier(Light object){
        changeDirect();
    }

    @Override
    public void meetBarrier(SectorPolice object){
        changeDirect();
    }

    @Override
    public void meetBarrier(Beggar object){
        changeDirect();
    }

    @Override
    public void meetBarrier(Policeman object){
        changeDirect();
    }

}


