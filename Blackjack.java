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
Hand(
        If dealer busts, all remaining players win
        If tie, then bet is returned
        Dealer collects bet if you lose
    */

    final String[] words = {"first", "second", "third", "fourth"}; // final String used in giving options, stored up here to save memory
	final int maxHandSize = 11; // maximum number of cards possible for a hand to have

    private int totalPlayers;
    private double[] bets;
    private Deck d;
    private Hand[][] hands;
    private Hand dealer;

    // Gathers info about game (player count, players' hands, bets, etc)
    public Blackjack(Scanner sc) {
        totalPlayers = getPlayerCount(sc);
        
        bets = new double[totalPlayers];
        for (int b = 0; b < totalPlayers; b++) {
            bets[b] = calculateBets(sc, b + 1);
        }

        d = new Deck(new String[]{"Clubs", "Spades", "Hearts", "Diamonds"}, 
        new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}, 
        new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11});
        // The deck is large enough that here, we don't need to worry about whether the deck is nearing empty (at max, 14 cards dealt)

        hands = new Hand[totalPlayers][4]; // creating players' hands
        for (int i = 0; i < totalPlayers; i++) {
            hands[i][0] = new Hand(maxHandSize, 2, "Player " + (i + 1), d);
        }

        dealer = new Hand(maxHandSize, 2, "The Dealer", d); // creating dealer's hand

        game(sc);
    }

   // Main method, the game itself
    public void game(Scanner sc) {
        System.out.println("\n");
        for (int i = 0; i < totalPlayers; i++) { // prints players' hands
            System.out.println("------------------------------------------------");
            printHand(hands[i][0]);
            System.out.println("------------------------------------------------");
        }

        System.out.println("------------------------------------------------");
        System.out.println("The Dealer's cards:"); // prints dealer's hand
        dealer.printCard(0);
        System.out.println("Unknown");
        System.out.println("Known Points: " + dealer.getCard(0).getPoint() + "");
        System.out.println("------------------------------------------------");


        for (int j = 0; j < totalPlayers; j++) { // giving every player options on their turn
            System.out.println("------------------------------------------------");
            options(sc, j + 1, hands[j][0], -1);
            System.out.println("------------------------------------------------");
        }

        System.out.println("\n------------------------------------------------");
        System.out.println("Dealer's Turn:");
        dealer.printCard(0);
        printHand(dealer);

        boolean escape = true;
        int index = 2;
        while (calculatePoints(dealer) < 17 && escape) { // dealer hitting until reaching 17
            if (dealer.getDeck().getAmountAvail() != 0) {
                System.out.println("\nhit");
                dealer.drawCard();
                dealer.printCard(index);
                index++;
            } else {
                escape = false;
            }
        }
        System.out.println("Dealer's Final Points: " + calculatePoints(dealer) + "\n");

        if (calculatePoints(dealer) > 21) {
            System.out.println("The Dealer has busted");
        }

        printOutcomes(calculatePoints(dealer));
    }

    // Gets the number of players
    private int getPlayerCount(Scanner sc) {
        System.out.print("How many people are playing? (Enter an integer between 0 and 6): ");

        String input;
        boolean repeat = true;
        int n = 0;

        while (repeat) {
            input = sc.nextLine();
            try {
                n = Integer.parseInt(input);
                repeat = false;
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer like 1 or 2");
                return getPlayerCount(sc);
            }
        }

        if (n < 0) {
            System.out.println("You can't have negative players");
            return getPlayerCount(sc);
        } else if (n == 0) {
            System.out.println("You can't have zero players");
            return getPlayerCount(sc);
        } else if (n > 6) {
            System.out.println("You can't have more than 6 players");
            return getPlayerCount(sc);
        }
        return n;
    }

    // returns the bets of player n
    private double calculateBets(Scanner sc, int n) {
        System.out.print("Player " + n + ", how much money do you want to bet: $");

        String input;
        boolean repeat = true;
        double m = 0;

        while (repeat) {
            input = sc.nextLine();
            try {
                m = Double.parseDouble(input);
                repeat = false;
            } catch (NumberFormatException e) {
                System.out.println("Could you enter an integer like 1 or 2");
                return calculateBets(sc, n);
            }
        }

        if (m < 0) {
            System.out.println("You can't bet negative money");
            return calculateBets(sc, n);
        }

         return m;
    }

    // returns the points in Hand h
    private int calculatePoints(Hand h) {
        int sum = 0;
        int index = -1;
        for (int i = 0; i < h.getLength(); i++) {
            if (h.getCard(i).getPoint() == 11) {
                index = i;
            }
            sum += h.getCard(i).getPoint();
        }

        if (index != -1 && sum > 21) {
            h.getCard(index).updatePoint(1);
            return calculatePoints(h);
        } else {
            return sum;
        }
    }

    // Prints out the cards in the hand along with total point value
    private void printHand(Hand h) {
        System.out.println(h.getName() + " has the following cards:");
        h.print();
        System.out.println("Total Points: " + calculatePoints(h));
    }

    // Offer the options to players
    private void options(Scanner sc, int n, Hand h, int i) { // i is to account whether player has split or playing regularly
		printHand(h);

        if (i == -1) {
            System.out.println("\nPlayer " + n + ", what would you like to do?");
        } else {
            System.out.println("\nPlayer " + n + ", what would you like to do with your " + words[i] + " hand?");
        }
        
        System.out.println("Here are your options: ");
        System.out.println("Hit\nStand\nDouble Down\nSplit\nAction Guide\n\n");

       chooseOptions(sc, n, h, i); 
    }

    // Runs the code for each option
    private void chooseOptions (Scanner sc, int n, Hand h, int i) {
        String input = sc.nextLine().toLowerCase();

        if (input.contains("hit")) {
            if (h.getDeck().getAmountAvail() != 0) {
                h.drawCard();

                h.getCard(h.getLength() - 1).print();
                System.out.println("Current Points: " + calculatePoints(h) + "\n");

                if (calculatePoints(h) > 21) {
                    System.out.println("\nUnfortunately, your total points (" + calculatePoints(h) + ") is over 21, so you have lost $" + bets[n - 1]);
                    h.discardHand();
                    return;
                }
            } else {
                System.out.println("\nUnfortunately, the deck is out of cards");
            }
            chooseOptions(sc, n, h, i);
        } else if (input.contains("stand")) {
            System.out.println("Final Total: " + calculatePoints(h));
        } else if (input.contains("double") || input.contains("down")) {
            if (h.getDeck().getAmountAvail() != 0) {
                h.drawCard();
                printHand(h);

                bets[n - 1] *= 2;

                if (calculatePoints(h) > 21) {
                    System.out.println("\nUnfortunately, your total points (" + calculatePoints(h) + ") is over 21, so you have lost $" + bets[n - 1]);
                    h.discardHand();
                    return;
                }

                h.getCard(h.getLength() - 1).print();
                System.out.println("\nYour new bet is $" + bets[n - 1] + " & your final total points are " + calculatePoints(h));
            } else {
                System.out.println("\nUnfortunately, the deck is out of cards\n");
                chooseOptions(sc, n, h, i);
            }
        } else if (input.contains("split")) {
            if (h.getLength() == 2 && h.getCard(0).getPoint() == h.getCard(1).getPoint() && h.getDeck().getAmountAvail() >= 2) {
                // only allow two identital valued cards & only 3 splits allowed (checks if last hand slot for player empty) & whether deck has enough cards
                if (hands[n - 1][3] != null) { // checks whether player is on 4th split
                    System.out.println("\nYou cannot split more than 4 times\n");
                    chooseOptions(sc, n, h, i);
                } else {
                    Card temp = h.getCard(0);
                    h.removeCard(temp);
                    h.drawCard();
                    
                    int index;
                    if (hands[n - 1][1] == null) { // first split
                        index = 1;
                    } else if (hands[n - 1][2] == null) { // second split
                        index = 2;
                    } else { // third (last) split
                        index = 3;
                    }

                    hands[n - 1][index] = new Hand(maxHandSize, 2, "Player " + n, d, temp);
                    System.out.println("------------------------------------------------");
                    options(sc, n, hands[n - 1][index], index);

                    System.out.println("------------------------------------------------");
                    options(sc, n, h, i);
                }
            } else {
                System.out.println("\nUnfortunately, do you not meet the requirements to split or the deck doesn't have enough cards to split\n");
                chooseOptions(sc, n, h, i);
            }

        } else if (input.contains("action") || input.contains("guide")) {
            System.out.println("\nHit: You draw another card to add to your total");
            System.out.println("Stand: You end your turn and keep your current total");
            System.out.println("Double Down: You double your bet, take your final card, and end your turn");
            System.out.println("Split: If you only have two identital valued cards, you can split them into two hands and \nplace a second bet equal to the first\n");
            options(sc, n, h, i);
        } else {
            System.out.println("\nEnter valid option");
            chooseOptions(sc, n, h, i);
        }
    }

    // prints out the results of the game
    private void printOutcomes(int dealerScore) {
        int state; // 0 for win, 1 for tie, 2 for loss
        int n;

        for (int i = 0; i < totalPlayers; i++) {
            state = 2;
            if (hands[i][1] != null) {
                for (int j = 0; j < 4; j++) {
                    if (hands[i][j] != null) {
                        n = calculatePoints(hands[i][j]);
                        int currentState = 2;

                        if (n <= 21) { // only checks for valid decks
                            if(dealerScore > 21 || n > dealerScore) { // wins if dealer busts or less than dealer's score (not busted)
                                currentState = 0;
                            } else if (n == dealerScore) { // ties if they are the same (previous condition checks if dealer has busted)
                                currentState = 1;
                            }
                        }

                        if (currentState < state) {
                            state = currentState;
                        }

                        if (state == 0) {
                            j = 4; // ends loop early
                        }
                    } else { // ends loop early if next slot that holds references to hands is null (meaning no next hand to check)
                        j = 4;
                    }
                }
            } else {
                n = calculatePoints(hands[i][0]);
                if (n > 21 || (n < dealerScore && dealerScore <= 21)) {
                    state = 2;
                } else if (n == dealerScore) {
                    state = 1;
                } else {
                    state = 0;
                }
            }

            if (state == 2) {
                System.out.println("Player " + (i + 1) + " loses $" + bets[i]);
            } else if (state == 1) {
                System.out.println("Player " + (i + 1) + " ties and keeps $" + bets[i]);
            } else {
                System.out.println("Player " + (i + 1) + " wins $" + bets[i]);
            }
            state = 2;
        }
    }

    // Instructions / Rules of the game
    private void instructions() {
        System.out.println("Welcome to Blackjack\n");
        System.out.println("You are competing against the dealer with the goal of building a hand closer to 21 than the dealer without going over");
        System.out.println("Numbered cards are valued the same as their number, the face cards are worth 10, and aces are worth either 11 or 1");
        System.out.println("Each player recevies two cards and on their turn, players can either hit, stand, double down, or split");
        System.out.println("If your hand has over 21 points, you lose your bet");
        System.out.println("If your score is greater than the dealer or the dealer gets over 21, then you win");
        System.out.println("If your score is the same as the dealer, you lose nothing");
        System.out.println("\n");
    }
}
