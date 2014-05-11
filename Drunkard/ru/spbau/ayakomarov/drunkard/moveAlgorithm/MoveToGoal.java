package ru.spbau.ayakomarov.drunkard.moveAlgorithm;

import ru.spbau.ayakomarov.drunkard.field.Field;

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

    public MoveToGoal( Field field_) {

        this.field = field_;
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

            for( int step = 0; step <= 3; step ++) {

                if(step == 0) {  // North
                    x = u.x - 1;
                    y = u.y ;
                } else if (step == 1) {  // South
                    x = u.x + 1;
                    y = u.y;
                } else if (step == 2) {  // West
                    x = u.x;
                    y = u.y - 1;
                } else {                // East
                    x = u.x;
                    y = u.y + 1;
                }

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

                            if(cur.x - cur.prev.x == -1 && cur.y - cur.prev.y == 0) {
                                navigation.push(0); // North
                            } else if(cur.x - cur.prev.x == 1 && cur.y - cur.prev.y == 0) {
                                navigation.push(1); // South
                            } else if(cur.x - cur.prev.x == 0 && cur.y - cur.prev.y == -1) {
                                navigation.push(2); // West
                            } else if(cur.x - cur.prev.x == 0 && cur.y - cur.prev.y == 1) {
                                navigation.push(3); // East
                            }

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
        return  (0 <= x && x < this.field.width) &&
                (0 <= y && y < this.field.height) &&
                this.field.cells[x][y].object == null &&
                visitField[x][y].color == 0;
    }

}
