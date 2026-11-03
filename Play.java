import java.util.Scanner;

// Only purpose of class is to play card games

public class Play {
    // If going to add new games, you must update String[] options && games method 
    final static String[] options = {"Blackjack"};

    public static void main(String[] args) {
        System.out.println("We have the following games:");
        for (String s : options) {
            System.out.println(s);
        }
        System.out.print("\nEnter the game you want to play: ");

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().toLowerCase();

        int index = -1;
        for (int i = 0; i < options.length; i++) {
            if (input.contains(options[i].toLowerCase())) {
                index = i;
                i = options.length;
                games(index, sc);
            }
        }
        
        if (index == -1) {
            // If can't find game, program ends
            System.out.println("Thank you for visiting. Sorry we didn't have the game you wanted.");
            return;
        }

        again(sc, args, index);
    }

    public static void games(int n, Scanner sc) {
        if (n == 0) {
            Blackjack b = new Blackjack(sc);
        }
    }

    public static void again(Scanner sc, String[] args, int index) {
        System.out.println("\n\nWould you like to play again or choose a different game?");
        System.out.println("Enter \"Play again\", \"Different game,\" or \"End\"");
        String input = sc.nextLine().toLowerCase();
        System.out.println("\n");

        if (input.contains("play") || input.contains("again")) {
            games(index, sc);
            again(sc, args, index);
        } else if (input.contains("different") || input.contains("game")) {
            main(args);
        } else {
            System.out.println("Thank you for playing");
            return;
        }
    }
}