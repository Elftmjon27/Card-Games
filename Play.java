import java.util.Scanner;

public class Play {
    // If going to add new games, you must update String[] options && games method 
    final String[] options = {"Blackjack"};

    public static void main(String[] args) {
        System.out.println("What card game would you like to play?");
        System.out.println("We have the following games:")
        for (String s : options) {
            System.out.println(s);
        }
        System.out.println("\n");

        Scanner sc = new Scanner(System.in);
        String input = nextLine();

        int index = -1;
        for (int i = 0; i < options.length; i++) {
            if (input.toLowerCase().contains(options[i].toLowerCase())) {
                playing = true;
                index = i;
                i = options.length;
                games(index);
            }
        }
        
        if (index == -1) {
            // If can't find game, program ends
            System.out.println("Thank you for visiting. Sorry we didn't have the game you wanted.")
            return;
        }

        System.out.println("\n\nWould you like to play again or choose a different game?");
        System.out.println("Enter \"Play again\", \"Different game,\" or \"End\"");
        input = nextLine();

        if (input.toLowerCase()contains("play again")) {
            games(index);
        } else if (input.toLowerCase()contains("different game")) {
            main(args);
        } else {
            return;
        }
    }

    public static void games(int n) {
        if (n == 0) {
            Blackjack b = new Blackjack();
        }
    }
}