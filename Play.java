import java.util.Scanner;

// Only purpose of class is to play card games

public class Play {
    // If going to add new games, you must update String[] options && games method 
    final static String[] options = {"Blackjack"};

    public static void main(String[] args) {
        System.out.println("What card game would you like to play?");
        System.out.println("We have the following games:");
        for (String s : options) {
            System.out.println(s);
        }
        System.out.println("\n");

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().toLowerCase();

        int index = -1;
        for (int i = 0; i < options.length; i++) {
            if (input.contains(options[i].toLowerCase())) {
                index = i;
                i = options.length;
                System.out.println("\n");
                games(index, sc);
            }
        }
        
        if (index == -1) {
            // If can't find game, program ends
            System.out.println("Thank you for visiting. Sorry we didn't have the game you wanted.");
            return;
        }

        System.out.println("\n\nWould you like to play again or choose a different game?");
        System.out.println("Enter \"Play again\", \"Different game,\" or \"End\"");
        input = sc.nextLine().toLowerCase();

        if (input.contains("play") || input.contains("again")) {
            games(index, sc);
        } else if (input.contains("different") || input.contains("game")) {
            main(args);
        } else {
            System.out.println("Thank you for playing");
            return;
        }
    }

    public static void games(int n, Scanner sc) {
        if (n == 0) {
            Blackjack b = new Blackjack(sc);
        }
    }
}