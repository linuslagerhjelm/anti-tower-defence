/*
 * File: Runner.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package controller.game;

public class Runner {
    public static void main(String[] args) {
        Game game = ((args.length > 0) ? new Game(args[0]) : new Game());
    }
}
