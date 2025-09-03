import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * GuessTheNumberGame is a console-based number guessing game in Java.
 * It demonstrates use of:
 * - Variables
 * - Loops
 * - Conditionals
 * - Functions
 * - Classes
 * - Collections (ArrayList)
 * - File I/O
 */
public class GuessTheNumberGame {
    private int targetNumber;
    private int maxRange;
    private ArrayList<Integer> guesses;
    private Scanner scanner;

    public GuessTheNumberGame() {
        scanner = new Scanner(System.in);
        guesses = new ArrayList<>();
    }

    /**
     * Starts the game loop, including replay and difficulty selection.
     */
    public void run() {
        boolean playAgain = true;
        System.out.println("Welcome to the Guess the Number Game!");

        while (playAgain) {
            selectDifficulty();
            generateRandomNumber();
            playGame();
            displayStats();
            saveGuessesToFile();

            playAgain = askToPlayAgain();
        }

        System.out.println("Thanks for playing, Promise! Goodbye.");
    }

    /**
     * Lets the user select the difficulty level.
     */
    private void selectDifficulty() {
        System.out.println("\nSelect Difficulty Level:");
        System.out.println("1. Easy (1 - 50)");
        System.out.println("2. Medium (1 - 100)");
        System.out.println("3. Hard (1 - 500)");

        int choice = 0;
        while (choice < 1 || choice > 3) {
            System.out.print("Enter choice (1-3): ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next(); // Clear invalid input
            }
        }

        switch (choice) {
            case 1:
                maxRange = 50;
                break;
            case 2:
                maxRange = 100;
                break;
            case 3:
                maxRange = 500;
                break;
        }

        System.out.println("You selected " + (choice == 1 ? "Easy" : choice == 2 ? "Medium" : "Hard") +
                           ". Guess a number between 1 and " + maxRange + ".");
    }

    /**
     * Generates a random number within the selected difficulty range.
     */
    private void generateRandomNumber() {
        Random rand = new Random();
        targetNumber = rand.nextInt(maxRange) + 1;
        guesses.clear();
    }

    /**
     * Core gameplay loop: takes guesses until the player wins.
     */
    private void playGame() {
        int attempts = 0;
        while (true) {
            int guess = getUserGuess();
            guesses.add(guess);
            attempts++;

            if (guess == targetNumber) {
                System.out.println("🎉 Congratulations, Promise! You guessed the number in " + attempts + " attempts.");
                break;
            } else if (guess < targetNumber) {
                System.out.println("Too low. Try again.");
            } else {
                System.out.println("Too high. Try again.");
            }
        }
    }

    /**
     * Prompts the user to enter a valid guess.
     */
    private int getUserGuess() {
        int guess = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter your guess: ");
            try {
                guess = scanner.nextInt();
                if (guess < 1 || guess > maxRange) {
                    System.out.println("Please enter a number between 1 and " + maxRange + ".");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.next(); // Clear invalid input
            }
        }

        return guess;
    }

    /**
     * Displays statistics about the current game.
     */
    private void displayStats() {
        int total = 0;
        int bestGuess = guesses.get(0);
        for (int guess : guesses) {
            total += guess;
            if (Math.abs(guess - targetNumber) < Math.abs(bestGuess - targetNumber)) {
                bestGuess = guess;
            }
        }

        double average = (double) total / guesses.size();
        System.out.println("\n📊 Game Stats:");
        System.out.println("Total Guesses: " + guesses.size());
        System.out.println("Average Guess Value: " + String.format("%.2f", average));
        System.out.println("Closest Guess: " + bestGuess);
    }

    /**
     * Saves the guess history to a file named guess_history.txt
     */
    private void saveGuessesToFile() {
        try {
            FileWriter writer = new FileWriter("guess_history.txt", true);
            writer.write("New Game - Max Range: " + maxRange + "\n");
            for (int i = 0; i < guesses.size(); i++) {
                writer.write("Guess " + (i + 1) + ": " + guesses.get(i) + "\n");
            }
            writer.write("Correct Answer: " + targetNumber + "\n");
            writer.write("--------------------------------------------------\n");
            writer.close();
            System.out.println("📝 Your guess history has been saved to 'guess_history.txt'.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving guess history.");
        }
    }

    /**
     * Asks the user if they want to play again.
     */
    private boolean askToPlayAgain() {
        System.out.print("\nDo you want to play again? (y/n): ");
        String response = scanner.next().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        GuessTheNumberGame game = new GuessTheNumberGame();
        game.run();
    }
}
