import java.util.Scanner;

public class Blackjack {
    /*
        Card Values: 
            Face Cards = 10
            Ace = 1 or 11 (default 11, only 1 if 11 causes hand to exceed 21)
            Rest is their number value

        Players make bet
        Players get two cards face-up
        Dealer get two cards (one face up and one face down)

        Players chose to hit, stand, double down, or split

        At end, dealer reveals face down card and must hit until reaching 17 or higher

        If dealer busts, all remaining players win
        If player's first wto cards are an ace and a 10-value card, then 3:2 win (win 3 for every 2 dollars waged)
        If tie, then bet is returned
        Dealer collects bet if you lose
    */

   // Main method, the game itself
    public void game() {
        Scanner sc = new Scanner(System.in);
        int totalPlayers = getPlayerCount(sc);
        
        Deck d = new Deck({"Clubs", "Spades", "Hearts", "Diamonds"}, {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"});
        
    }

    // Gets the number of players
    private int getPlayerCount(Scanner sc) {
        System.out.println("How many people are playing? (Enter an integer)");

        String input;
        boolean repeat = true;
        int n;

        while (repeat) {
            input = sc.nextLine();
            try {
                n = Integer.parseInt(input);
                repeat = false;
            } catch (NumberFormatException e) {
                System.out.println("Could you enter an integer like 1 or 2");
                System.out.println("How many people are playing? (Enter an integer)");
            }
        }

        if (n < 0) {
            System.out.println("You can't have negative players");
            return getPlayerCount();
        } else if (n == 0) {
            System.out.println("You can't have zero players");
            return getPlayerCount();
        }
        return n;
    }

    // INCOMPLETE
    private void instructions() {
        System.out.println("Welcome to Blackjack\n");
        System.out.println("You are competing against the dealer with the goal of building a hand closer to 21 than the dealer without going over");
        System.out.println();
    }
}
