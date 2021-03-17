package tirachess.domain;

import java.util.Random;
import java.util.Scanner;

import tirachess.datastructures.MyArrayList;

/**
 * A class that represents a single chess game. It has two players that can be either
 * human or bots. The class takes care of determining the winner of a game, printing 
 * important information about the game and starting the match. 
 */
public class Game {
    private Position position;
    private Player whitePlayer;
    private Player blackPlayer;

    /**
     * The constructor of the class. 
     * 
     * @param scanner Scanner object that will be passed down to the possible 
     * human player.
     * @param isHumanVsBot boolean value on whether the match will be human vs bot
     * or bot vs bot. 
     */
    public Game(Scanner scanner, boolean isHumanVsBot) {
        this.position = new Position();
        if (isHumanVsBot) {
            Random r = new Random();
            boolean humanIsWhite = r.nextBoolean();
            if (humanIsWhite) {
                System.out.println("You are white.");
                this.whitePlayer = new Human(scanner);
                this.blackPlayer = new Bot();
            } else {
                System.out.println("You are black.");
                this.whitePlayer = new Bot();
                this.blackPlayer = new Human(scanner);
            }
        } else {
            Bot bot = new Bot();
            this.whitePlayer = bot;
            this.blackPlayer = bot;
        }
    }

    /**
     * A method that starts the chess game and alternates between players' turns. 
     */
    public void start() {
        System.out.println("The game starts.");
        System.out.println();
        MyArrayList<Position> moves = this.position.getMoves();
        while (!moves.isEmpty() && !this.position.threeFoldRepetition 
               && this.position.halfMoveCounter < 100) {
            this.position.print();
            this.printMoveInfo();
            if (this.position.whitesMove) {
                this.position = this.whitePlayer.play(position);
            } else {
                this.position = this.blackPlayer.play(position);
            }
            System.out.println();
            moves = this.position.getMoves();
        }
        this.position.print();
        this.printResult();

    }
    
    /**
     * A method that prints information about the current turn. 
     */
    private void printMoveInfo() {
        String color = this.position.whitesMove ? "White " : "Black ";
        if (this.position.fullMoveCounter % 10 == 1) {
            System.out.println(color + this.position.fullMoveCounter + "st move...");
        } else if (this.position.fullMoveCounter % 10 == 2) {
            System.out.println(color + this.position.fullMoveCounter + "nd move...");
        } else {
            System.out.println(color + this.position.fullMoveCounter + "th move...");
        }

    }

    /**
     * A method that prints the final result of the game after it has finished. 
     */
    private void printResult() {
        if (this.position.threeFoldRepetition) {
            System.out.println("Draw by threefold repetition.");
        } else if (this.position.halfMoveCounter >= 100) {
            System.out.println("Draw by fifty-move rule.");
        } else if (this.position.whitesMove && this.position.pieceIsAttacked(Position.WKing)) {
            System.out.println("Black won.");
        } else if (!this.position.whitesMove && this.position.pieceIsAttacked(Position.BKing)) {
            System.out.println("White won.");
        } else {
            System.out.println("Draw by stalemate.");
        }
    }
}
