package ru.spbau.ayakomarov.drunkard.field;


import ru.spbau.ayakomarov.drunkard.object.IObjectLive;
import java.util.ArrayList;


public class LiveContainer {

    private final ArrayList<IObjectLive> objectsLive = new ArrayList<IObjectLive>();
    private final ArrayList<IObjectLive> bufObjectsLive = new ArrayList<IObjectLive>();


    public void doStep() {
        objectsLive.addAll(bufObjectsLive);
        bufObjectsLive.clear();

        for (int i=0; i < this.objectsLive.size(); i++) {
            this.objectsLive.get(i).doStep();
        }
    }

    public void addObjectLive(IObjectLive object) {
        bufObjectsLive.add(object);
    }
}
