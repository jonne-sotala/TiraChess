
package tirachess;

import java.util.Scanner;

import tirachess.domain.Game;
import tirachess.util.PerformanceTester;

/**
 * The main class.
 */
public class Main {

    /**
     * The main command line program that allows the user to pick between playing the bot itself
     * or letting the computer play with itself. 
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Please choose an option (1, 2 or 3):");
        System.out.println("1) Play versus the computer.");
        System.out.println("2) Let the computer play with itself.");
        System.out.println("3) Run performance tests");

        int num;
        do {
            System.out.print("Your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please choose 1 or 2: ");
                scanner.next();
            }
            num = scanner.nextInt();
        } while (!(num == 1 || num == 2 || num == 3));

        System.out.println();
        if (num == 1) {
            Game game = new Game(scanner, true);
            game.start();
        } else if (num ==2) {
            Game game = new Game(scanner, false);
            game.start();
        } else {
            PerformanceTester pt  =new PerformanceTester();
            System.out.println("MyArrayList Test:");
            pt.testMyArrayList();
            System.out.println();

            System.out.println("MyHashMap Test: ");
            pt.testMyHashMap();
            System.out.println();

            System.out.println("Alpha-beta and Minmax comparison:");
            System.out.println();
            pt.compareAlphaBetaMinMax();
        }

        scanner.close();
    }
}
