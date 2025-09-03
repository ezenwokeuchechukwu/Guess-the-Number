import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A simple number guessing game.
 * Demonstrates use of Java variables, loops, conditionals, functions, classes,
 * collections (ArrayList), and file I/O.
 */
public class GuessTheNumberGame {
    private int targetNumber;
    private ArrayList<Integer> guesses;
    private Scanner scanner;

    public GuessTheNumberGame() {
        Random rand = new Random();
        targetNumber = rand.nextInt(100) + 1; // Random number between 1 and 100
        guesses = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("Welcome to the Guess the Number Game!");
        System.out.println("Try to guess the number between 1 and 100.");

        int attempts = 0;
        while (true) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            guesses.add(guess);
            attempts++;

            if (guess == targetNumber) {
                System.out.println("Congratulations, Promise! You guessed the number in " + attempts + " attempts.");
                break;
            } else if (guess < targetNumber) {
                System.out.println("Too low. Try again.");
            } else {
                System.out.println("Too high. Try again.");
            }
        }

        saveGuessesToFile();
    }

    // Save guesses to a file
    private void saveGuessesToFile() {
        try {
            FileWriter writer = new FileWriter("guess_history.txt");
            writer.write("Guess History:\n");
            for (int i = 0; i < guesses.size(); i++) {
                writer.write("Guess " + (i + 1) + ": " + guesses.get(i) + "\n");
            }
            writer.close();
            System.out.println("Your guess history has been saved to guess_history.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving guess history.");
        }
    }

    public static void main(String[] args) {
        GuessTheNumberGame game = new GuessTheNumberGame();
        game.startGame();
    }
}
