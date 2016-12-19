/*
 * File: Runner.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package controller.game;

/**
 * Entry point of the program. Handles program arguments and start/exit of the
 * game.
 */
public class Runner {
    public static void main(String[] args) {
        Game game = ((args.length > 0) ? new Game(args[0]) : new Game());
        System.exit(0);
    }
}
