package com.cyborck.gameOfLive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    private static final int MAX_SIZE = 1000;
    private static final int CELL_OFFSET = 2;
    private static final int CELL_SIZE = ( MAX_SIZE - CELL_OFFSET * ( GameOfLive.SIZE + 1 ) ) / GameOfLive.SIZE;
    private static final int REAL_SIZE = ( GameOfLive.SIZE + 1 ) * CELL_OFFSET + GameOfLive.SIZE * CELL_SIZE;

    static {
        System.out.println( REAL_SIZE );
    }

    private final GameOfLive gameOfLive;

    public GUI ( GameOfLive gameOfLive ) {
        super();
        this.gameOfLive = gameOfLive;

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setUndecorated( true );
        addKeyListener( this );
        addMouseListener( this );
        addMouseMotionListener( this );

        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent ( Graphics g ) {
                super.paintComponent( g );

                boolean[][] cells = gameOfLive.getCells();

                g.setColor( new Color( 245, 113, 37 ) ); //orange
                for ( int i = 0; i < cells.length; i++ )
                    for ( int j = 0; j < cells[ i ].length; j++ )
                        if ( cells[ i ][ j ] ) {
                            int y = i * CELL_SIZE + ( i + 1 ) * CELL_OFFSET;
                            int x = j * CELL_SIZE + ( j + 1 ) * CELL_OFFSET;

                            //draw rectangle for every living cell
                            g.fillRect( x, y, CELL_SIZE, CELL_SIZE );
                        }

                repaint();
            }
        };
        drawPanel.setBackground( new Color( 43, 43, 44 ) ); //dark gray
        drawPanel.setPreferredSize( new Dimension( REAL_SIZE, REAL_SIZE ) );
        add( drawPanel );

        pack();
        setLocationRelativeTo( null );
        setVisible( true );
    }

    @Override
    public void keyReleased ( KeyEvent e ) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_ESCAPE -> System.exit( 0 );
            case KeyEvent.VK_SPACE -> gameOfLive.runOrExitSimulation();
            case KeyEvent.VK_ENTER -> gameOfLive.update();
            case KeyEvent.VK_C -> gameOfLive.clear();
        }
    }

    @Override
    public void mouseClicked ( MouseEvent e ) {
        int cellX = e.getX() / ( CELL_SIZE + CELL_OFFSET );
        int cellY = e.getY() / ( CELL_SIZE + CELL_OFFSET );

        gameOfLive.changeCellAt( cellX, cellY );
    }

    @Override
    public void mouseDragged ( MouseEvent e ) {
        int cellX = e.getX() / ( CELL_SIZE + CELL_OFFSET );
        int cellY = e.getY() / ( CELL_SIZE + CELL_OFFSET );

        gameOfLive.setCell( cellX, cellY, true );
    }

    //not used
    @Override
    public void keyTyped ( KeyEvent e ) {}

    @Override
    public void keyPressed ( KeyEvent e ) {}

    @Override
    public void mousePressed ( MouseEvent e ) {}

    @Override
    public void mouseReleased ( MouseEvent e ) {}

    @Override
    public void mouseEntered ( MouseEvent e ) {}

    @Override
    public void mouseExited ( MouseEvent e ) {}

    @Override
    public void mouseMoved ( MouseEvent e ) {}
}
