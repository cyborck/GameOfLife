package com.cyborck.gameOfLive;

public class Main {
    public static void main ( String[] args ) {
        GameOfLife gameOfLife = new GameOfLife();
        new GUI( gameOfLife );
    }
}
