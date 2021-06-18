package com.cyborck.gameOfLive;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLive {
    public static final int SIZE = 100;
    public static final float TICK_PERIOD = 1 / 30f; //seconds

    private boolean[][] cells;
    private boolean run;

    public GameOfLive () {
        cells = new boolean[ SIZE ][ SIZE ];
        clear();
        run = false;
    }

    public void clear () {
        //set every cell to false (dead)
        for ( boolean[] cell: cells )
            Arrays.fill( cell, false );
    }

    public void update () {
        boolean[][] nextCells = new boolean[ SIZE ][ SIZE ];

        for ( int y = 0; y < cells.length; y++ )
            for ( int x = 0; x < cells[ y ].length; x++ ) {
                //count living neighbours for each cell
                int neighbours = 0;

                for ( int y2 = -1; y2 <= 1; y2++ )
                    for ( int x2 = -1; x2 <= 1; x2++ )
                        //if cell isn't the current cell
                        if ( !( y2 == 0 && x2 == 0 ) ) {
                            int currentY = y + y2;
                            int currentX = x + x2;

                            //if cell is out of bounds, check cell on other side
                            if ( currentY < 0 ) currentY += SIZE;
                            else if ( currentY >= SIZE ) currentY -= SIZE;
                            if ( currentX < 0 ) currentX += SIZE;
                            else if ( currentX >= SIZE ) currentX -= SIZE;

                            if ( cells[ currentY ][ currentX ] ) neighbours++;
                        }

                nextCells[ y ][ x ] = neighbours == 3 || ( cells[ y ][ x ] && neighbours == 2 );
            }

        cells = nextCells;
    }

    private void simulate () {
        run = true;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run () {
                if ( run ) update();
                else cancel();
            }
        }, 0, ( long ) ( TICK_PERIOD * 1000 ) );
    }

    public void runOrExitSimulation () {
        if ( run ) run = false;
        else simulate();
    }

    public void changeCellAt ( int x, int y ) {
        if ( x >= 0 && x < SIZE && y >= 0 && y < SIZE )
            cells[ y ][ x ] = !cells[ y ][ x ];
    }

    public void setCell ( int x, int y, boolean alive ) {
        if ( x >= 0 && x < SIZE && y >= 0 && y < SIZE )
            cells[ y ][ x ] = alive;
    }

    public boolean[][] getCells () {
        return cells;
    }
}
