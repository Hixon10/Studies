package ru.spbau.ayakomarov.drunkard.object;


import ru.spbau.ayakomarov.drunkard.field.LiveContainer;
import ru.spbau.ayakomarov.drunkard.field.Field;

public class Bar implements IObjectLive{

    public Field field;
    public LiveContainer livecontainer;

    public int startX = 0;
    public int startY = 9;
    private int count = 20;

    public Bar(Field f, LiveContainer c){
        this.field = f;
        this.livecontainer = c;
        livecontainer.addObjectLive(this);
    }

    @Override
    public void doStep() {

        if( wayIsFree() && count >= 20) {

            Drunkard drunk = new Drunkard(startX, startY, this.field);
            field.setObject(startX, startY, drunk);
            count = 0;
            livecontainer.addObjectLive(drunk);

        }
        count++;

    }

    private boolean wayIsFree(){
        return field.getObject(startX, startY) == null;
    }

}
