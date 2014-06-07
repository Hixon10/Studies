package ru.spbau.ayakomarov.drunkard;


import ru.spbau.ayakomarov.drunkard.field.HexagonalField;
import ru.spbau.ayakomarov.drunkard.field.LiveContainer;
import ru.spbau.ayakomarov.drunkard.field.Field;
import ru.spbau.ayakomarov.drunkard.field.StandardField;
import ru.spbau.ayakomarov.drunkard.object.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        final int pause = 200;

        final int manySteps = 500;
        final int width = 15;
        final int height = 15;

        final int columnX = 7;
        final int columnY= 7;

        final int lightX = 3;
        final int lightY = 10;

        final int sectorPoliceX = 3;
        final int sectorPoliceY= 14;

        //Field field = new StandardField(width, height);

        Field field = new HexagonalField(width, height);
        LiveContainer livecontainer = new LiveContainer();

        field.setObject(columnX,columnY, new Column());

        Light light = new Light(lightX, lightY, field);
        field.setObject(lightX,lightY,light);

        SectorPolice sectorPolice = new SectorPolice(field, livecontainer, light);
        Bar bar = new Bar(field, livecontainer);
        ReceiverGlass receiverGlass = new ReceiverGlass(field, livecontainer);

        for (int k = 1; k < manySteps; k++)  {

            livecontainer.doStep();
            //TimeUnit.MILLISECONDS.sleep(pause);

            // clear console
            //System.out.print("\033[H\033[2J");

            System.out.println("");

            System.out.flush();

            field.view();

            //System.out.printf("\nstep  %d   ", k);
        }

    }


}






