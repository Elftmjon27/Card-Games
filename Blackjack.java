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
        If tie, then bet is returned
        Dealer collects bet if you lose
    */

    final String[] actions = {"Hit", "Stand", "Double Down", "Split", "Action Guide"};
    final String[] words = {"first", "second", "third", "fourth"};

    private int totalPlayers;
    double[] bets;
    Deck d;
    Hand[][] hands;
    boolean[][] active;
    Hand dealer;

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

        hands = new Hand[totalPlayers][4]; // creating players' hands
        for (int i = 0; i < totalPlayers; i++) {
            hands[i][0] = new Hand(2, "Player", d);
        }
        active = new boolean[totalPlayers][4]; // array that records whether a player has completed the turns on their hands (true if they haven't)
        for (int j = 0; j < totalPlayers; j++) {
            active[j][0] = true;
        }

        dealer = new Hand(2, "Dealer", d); // crearing dealer's hand

        game(sc);
    }

   // Main method, the game itself
    public void game(Scanner sc) {

        System.out.println("\n------------------------------------------------");
        System.out.println("The Dealer's cards:"); // prints dealer's hand
        dealer.printCard(0);
        System.out.println("Unknown\n");
        System.out.println("Known Points: " + dealer.getCard(0).getPoint() + "\n");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < totalPlayers; i++) { // prints players' hands
            System.out.println("------------------------------------------------");
            printHand(i + 1, hands[i][0]);
            System.out.println("------------------------------------------------");
        }

        for (int j = 0; j < totalPlayers; j++) { // giving every player options on their turn
            System.out.println("------------------------------------------------");
            options(sc, j + 1, hands[j][0]);
            System.out.println("------------------------------------------------");
        }

        System.out.println("\n------------------------------------------------");
        System.out.println("Dealer's Turn:");
        dealer.printCard(0);
        dealer.printCard(1);

        boolean escape = true;
        int index = 2;
        while (calculatePoints(dealer) < 17 && escape) { // dealer hitting until reaching 17
            if (dealer.getDeck().getAmountAvail() != 0) {
                System.out.println("\nhit");
                dealer.drawCard();
                dealer.updateSize(dealer.getLength() + 1);
                dealer.printCard(index);
                index++;
            } else {
                escape = false;
            }
        }
        System.out.println("Dealer's Final Points: " + calculatePoints(dealer) + "\n");



        if (calculatePoints(dealer) > 21) {
            System.out.println("The Dealer has busted");
            printDealerBustedWinners();
        } else {
            printDealerSurvived(calculatePoints(dealer));
        }
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

    // Prints out player p's hand called h
    private void printHand(int p, Hand h) {
        System.out.println("Player " + p + " has the following cards:");
        h.print();
        System.out.println("Total Points: " + calculatePoints(h) + "\n");
    }

    // Offer the options players have in their turns
    private void options (Scanner sc, int n, Hand h) {
        printHand(n, h);
        System.out.println("Player " + n + ", what would you like to do?");
        System.out.println("Here are your options: ");
        // final String[] actions = {"Hit", "Stand", "Double Down", "Split", "Action Guide"};
        for (String a : actions) {
            System.out.println(a);
        }
        System.out.println("\n");

       chooseOptions(sc, n, h); 
    }

    // Offer the options to players who have split their deck
    private void options(Scanner sc, int n, Hand h, int i) {
		printHand(n, h);
        System.out.println("Player " + n + ", what would you like to do with your " + words[i] + " hand?");
        System.out.println("Here are your options: ");
        for (String a : actions) {
            System.out.println(a);
        }
        System.out.println("\n");

       chooseOptions(sc, n, h); 
    }

    // Runs the code for each option
    private void chooseOptions (Scanner sc, int n, Hand h) {
        String input = sc.nextLine().toLowerCase();

        if (input.contains("hit")) {
            if (h.getDeck().getAmountAvail() != 0) {
                h.drawCard();
                h.updateSize(h.getLength() + 1);
                System.out.println("\n");

                if (calculatePoints(h) > 21) {
                    System.out.println("\nUnfortunately, your total points (" + calculatePoints(h) + ") is over 21, so you have lost $" + bets[n - 1]);
                    return;
                }
            } else {
                System.out.println("\nUnfortunately, the deck is out of cards");
            }
            options(sc, n, h);
        } else if (input.contains("stand")) {
            System.out.println("\nFinal Total: " + calculatePoints(h));
        } else if (input.contains("double") || input.contains("down")) {
            if (h.getDeck().getAmountAvail() != 0) {
                h.drawCard();
                h.updateSize(h.getLength() + 1);
                System.out.println("\n");
                printHand(n, h);

                if (calculatePoints(h) > 21) {
                    System.out.println("\nUnfortunately, your total points (" + calculatePoints(h) + ") is over 21, so you have lost $" + 2*bets[n - 1]);
                    return;
                }

				bets[n - 1] *= 2;
                System.out.println("\nYour new bet is $" + bets[n - 1] + " & your final total points are " + calculatePoints(h));
            } else {
                System.out.println("\nUnfortunately, the deck is out of cards\n");
                options(sc, n, h);
            }
        } else if (input.contains("split")) {
            if (h.getLength() == 2 && h.getCard(0).getPoint() == h.getCard(1).getPoint() && !active[n - 1][3] && h.getDeck().getAmountAvail() >= 2) {
                // only allow two identital valued cards & only 3 splits allowed (checks if last hand slot for player empty) & whether deck has enough cards

                Card temp = h.getCard(0);
                h.removeCard(temp);
                h.drawCard();
                h.updateSize(2); 
                if (!active[n - 1][1]) { // first split
                   hands[n - 1][1] = new Hand(2, "Player", d, temp);
                   active[n - 1][1] = true;
                   options(sc, n, hands[n - 1][1], 1);
                } else if (!active[n - 1][2]) { // second split
                   hands[n - 1][2] = new Hand(2, "Player", d, temp);
                   active[n-1][2] = true;
                   options(sc, n, hands[n - 1][2], 2);
                } else { // last (third) split
                   hands[n - 1][3] = new Hand(2, "Player", d, temp);
                   active[n-1][3] = true;
                   options(sc, n, hands[n - 1][3], 3);
                }

                options(sc, n, h, 0);
            } else {
                System.out.println("\nUnfortunately, do you not meet the requirements to split or the deck doesn't have enough cards to split\n");
                options(sc, n, h);
            }

        } else if (input.contains("action") || input.contains("guide")) {
            System.out.println("\nHit: You draw another card to add to your total");
            System.out.println("Stand: You end your turn and keep your current total");
            System.out.println("Double Down: You double your bet, take your final card, and end your turn");
            System.out.println("Split: If you only have two identital valued cards, you can split them into two hands and \nplace a second bet equal to the first\n");
            options(sc, n, h);
        } else {
            System.out.println("\nEnter valid option");
            chooseOptions(sc, n, h);
        }
    }

    // Prints out the results if the dealer busts
    private void printDealerBustedWinners () {
        boolean win = false;
        for (int i = 0; i < totalPlayers; i++) {
            if (active[i][1]) {
                for (int j = 0; j < 4; j++) {
                    if (calculatePoints(hands[i][j]) <= 21 && calculatePoints(hands[i][j]) != 0) {
                        win = true;
                    }
                }
            } else {
                if (calculatePoints(hands[i][0]) <= 21) {
                    win = true;
                }
            }

            if (win) {
                System.out.println("Player " + (i + 1) + " wins $" + bets[i]);
            } else {
                System.out.println("Player " + (i + 1) + " loses $" + bets[i]);
            }
            win = false;
        }
    }

    // Prints out the results if the dealer doesn't bust
    private void printDealerSurvived (int dealerScore) { // d = dealer's score
        int state = 2; // 0 for win, 1 for tie, 2 for loss
        int n;

        for (int i = 0; i < totalPlayers; i++) {
            if (active[i][1]) {
                for (int j = 0; j < 4; j++) {
                    n = calculatePoints(hands[i][j]);
                    if (n > 21 && state == 2) { // if one deck tie or win, no lose
                        state = 2;
                    } else if (n == dealerScore) {
                        state = 1;
                    } else if (n > dealerScore) {
                        state = 0;
                        j = 4; // ends if one deck wins
                    }
                }
            } else {
                n = calculatePoints(hands[i][0]);
                if (n > 21) {
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
