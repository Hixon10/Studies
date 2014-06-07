package ru.spbau.ayakomarov.drunkard.field;

public class HexagonalField extends Field{

    public HexagonalField(int w, int h) {
        super(w+1, h, 6);
    }

    @Override
    public boolean isBoundaries(int x, int y) {
        if(x % 2 == 0) {
            return  (0 <= x && x < this.height) && (0 <= y && y < this.width-1);
        } else {
            return  (0 <= x && x < this.height) && (0 <= y && y < this.width);
        }
    }

    @Override
    public boolean isFreeCell(int x, int y) {
        return  isBoundaries(x, y) &&
                this.cells[x][y].object == null;
    }

    @Override
    public Cell getNeighbor( int i, int j, int direction) {

        if(direction == 0) {
            if(isBoundaries(i-1,j-i%2))
                return cells[i-1][j - i%2];
        }
        else if(direction == 1) {
            if(isBoundaries(i+1,j+1-i%2))
                return cells[i+1][j+1-i%2];
        }
        else if(direction == 2) {
            if(isBoundaries(i,j-1))
                return cells[i][j-1];
        }
        else if(direction == 3) {
            if(isBoundaries(i,j+1))
                return cells[i][j+1];
        }
        else if(direction == 4) {
            if(isBoundaries(i-1,j+1-i%2))
                return cells[i-1][j+1-i%2];
        }
        else {
            if(isBoundaries(i+1,j-i%2))
                return cells[i+1][j-i%2];
        }
        return null;
    }

    @Override
    public int getNeighborX( int i, int j, int direction) {
        if(direction == 0) {
            return i-1;
        }
        else if(direction == 1) {
            return i+1;
        }
        else if(direction == 2) {
            return i;
        }
        else if(direction == 3) {
            return i;
        }
        else if(direction == 4) {
            return i-1;
        }
        else {
            return i+1;
        }
    }

    @Override
    public int getNeighborY( int i, int j, int direction) {
        if(direction == 0) {
            return j-i%2;
        }
        else if(direction == 1) {
            return j+1-i%2;
        }
        else if(direction == 2) {
            return j-1;
        }
        else if(direction == 3) {
            return j+1;
        }
        else if(direction == 4) {
            return j+1-i%2;
        }
        else {
            return j-i%2;
        }
    }

    @Override
    public int directRevers(int direction){

        if(direction == 0) {
            return 1;
        }
        else if(direction == 1) {
            return 0;
        }
        else if(direction == 2) {
            return 3;
        }
        else if(direction == 3) {
            return 2;
        }
        else if(direction == 4) {
            return 5;
        }
        else {
            return 4;
        }
    }

    @Override
    public int getNavigable(int sx, int ex, int sy, int ey) {

        if(sx - ex == 0 && sy - ey == -1) {
            return 2; // West
        } else if(sx - ex == 0 && sy - ey == 1) {
            return 3; // East
        }

        if(ex%2==0) {
            if( sx - ex == -1 && sy - ey == 0) {
                return 0; // up-left
            } else if(sx - ex == 1 && sy - ey == 0) {
                return 5; // down-left
            } else if(sx - ex == -1 && sy - ey == 1) {
                return 4; // up-right
            } else if(sx - ex == 1 && sy - ey == 1) {
                return 1; // down-right
            }
        } else {
            if( sx - ex == -1 && sy - ey == 0) {
                return 4; // up-right
            } else if(sx - ex == 1 && sy - ey == 0) {
                return 1; // down-right
            } else if(sx - ex == -1 && sy - ey == -1) {
                return 0; // up-left
            } else if(sx - ex == 1 && sy - ey == -1) {
                return 5; // down-left
            }
        }

        return -1;
    }

    @Override
    public void view() {
        String space = "";

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                if(i%2==0 && j == width-1) continue;
                if(i%2==0 && j==0) space = "  ";
                else space = "";

                if( cells[i][j].object != null ){
                    char ch = cells[i][j].object.view();
                    System.out.printf(space + " %c ", ch);
                } else {
                    System.out.printf(space + " . ");
                }

            }
            System.out.printf("\n");
        }
    }


}
