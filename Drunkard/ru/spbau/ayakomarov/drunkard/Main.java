package ru.spbau.ayakomarov.drunkard;


import ru.spbau.ayakomarov.drunkard.field.LiveContainer;
import ru.spbau.ayakomarov.drunkard.field.Field;
import ru.spbau.ayakomarov.drunkard.object.*;

import java.util.concurrent.TimeUnit;

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

        Field field = new Field(width, height);
        field.cells[columnX][columnY].object = new Column();
        Light light = new Light(lightX, lightY, field);
        field.cells[lightX][lightY].object = light;

        LiveContainer livecontainer = new LiveContainer();


        SectorPolice sectorPolice = new SectorPolice(sectorPoliceX, sectorPoliceY, field, livecontainer, light);
        Bar bar = new Bar(field, livecontainer);
        ReceiverGlass receiverGlass = new ReceiverGlass(field, livecontainer);

        for (int k = 1; k < manySteps; k++)  {

            livecontainer.doStep();
            //TimeUnit.MILLISECONDS.sleep(pause);

            // clear console
            //System.out.print("\033[H\033[2J");

            System.out.println("");

            System.out.flush();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    if( field.cells[i][j].object != null ){
                        char ch = field.cells[i][j].object.view();
                        System.out.printf(" %c ", ch);
                    } else {
                        System.out.printf(" . ");
                    }

                }
                System.out.printf("\n");
            }

            //System.out.printf("\nstep  %d   ", k);
        }

    }


}






