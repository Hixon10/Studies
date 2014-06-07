package ru.spbau.ayakomarov.drunkard.moveAlgorithm;

import ru.spbau.ayakomarov.drunkard.field.Cell;
import ru.spbau.ayakomarov.drunkard.field.Field;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class MoveToGoal {

    class VisitCell {
        public int x;
        public int y;
        public VisitCell prev;
        public int dist;
        public int color;
    }

    public Field field;
    public final VisitCell[][] visitField;

    public MoveToGoal( Field field) {

        this.field = field;
        visitField = new VisitCell[this.field.height][this.field.width];
        for(int i=0 ; i!= field.height; i++) {
            for (int j=0 ; j!= field.width; j++) {

                visitField[i][j] = new VisitCell();
            }
        }
        clean();
    }

    private void clean() {

        for(int i=0 ; i!= field.height; i++) {
            for (int j=0 ; j!= field.width; j++) {

                visitField[i][j].x = i;
                visitField[i][j].y = j;
                visitField[i][j].prev = null;
                visitField[i][j].dist = -1; // inf
                visitField[i][j].color = 0; // white
            }
        }
    }


    public Stack<Integer> getWay( int startX, int startY, int goalX, int goalY) {

        if( startX == goalX && startY == goalY) {
            return new Stack<>();
        }

        clean();

        visitField[startX][startY].color = 1; // gray
        visitField[startX][startY].dist = 0;

        Queue<VisitCell> queue= new LinkedList<VisitCell>();

        queue.add( visitField[startX][startY]);

        while( ! queue.isEmpty() ) {

            VisitCell u = queue.poll();
            int x;
            int y;

            for(int step =0; step < field.countDirects ;step++) {

                x = field.getNeighborX(u.x, u.y, step);
                y = field.getNeighborY(u.x, u.y, step);

                if( x == goalX && y == goalY) {
                    // !!!
                    VisitCell v = visitField[x][y];
                    v.color = 2; // black
                    v.dist = u.dist + 1;
                    v.prev = u;
                    queue.add(v);

                    ////////////////////
                    Stack<Integer> navigation =  new Stack<Integer>();

                    VisitCell cur = v;

                    while ( cur.prev != null ) {

                        navigation.push(field.getNavigable(cur.x, cur.prev.x, cur.y, cur.prev.y));
                        cur = cur.prev;
                    }

                    return navigation;

                } else if ( isFreeCell(x,y)) {

                    VisitCell v = visitField[x][y];
                    v.color = 1; // gray
                    v.dist = u.dist + 1;
                    v.prev = u;
                    queue.add(v);
                }
            }

            u.color = 2; // black
        }

        return null; // if not way
    }


    boolean isFreeCell(int x, int y) {

        return  field.isFreeCell(x, y) &&
                visitField[x][y].color == 0;

    }

}
