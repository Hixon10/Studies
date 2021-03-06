package ru.spbau.ayakomarov.drunkard.object;

import ru.spbau.ayakomarov.drunkard.field.LiveContainer;
import ru.spbau.ayakomarov.drunkard.field.Field;


public class ReceiverGlass implements IObjectLive{

    public Field field;
    public LiveContainer livecontainer;
    boolean isFieldFree = true;

    public int startX = 4;
    public int startY = 0;

    public ReceiverGlass(Field f, LiveContainer c){
        this.field = f;
        this.livecontainer = c;
        livecontainer.addObjectLive(this);
    }

    @Override
    public void doStep() {

        if( field.getObject(startX, startY) == null && isFieldFree ) {
            Beggar beggar = new Beggar(startX, startY, this.field, this);
            field.setObject(startX, startY, beggar);
            livecontainer.addObjectLive(beggar);
            isFieldFree = false;
        }
    }

}
