package io.show;

import io.show.game.GameLoop;

/**
 * This just calls GameLoops' init
 */
public class Main {
    public static void main(String[] args) {
        new GameLoop().init();
    }
}