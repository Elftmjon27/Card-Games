/*
	Name: Jonathan
	Class Name: Hand
	General Purpose: Serve as the collection of cards that correspond to the player
*/

public class Hand {
    private String name; // name of player that has this hand
    private Card[] cards; // collection of cards
    private int amount = 0;
    private Deck deck; // reference to the deck where the cards come from

    public Hand() { // default constructor
        name = "Player";
        deck = new Deck();
        cards = new Card[12];
        createHand(5, 0);
    }

    /* Meaning of parameters:
    int m is the max amount of cards that a hand can hold (necessary b/c hand is an array of fixed size)
    int n is the amount of cards that the hand starts with
    String s is the name of who's hand this is
    Deck d is the deck where the cards stored in the hand comes from
    */
    public Hand(int m, int n, String s, Deck d) { // specialized constructor
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        
        cards = new Card[m];
        createHand(n, 0);
    }

    public Hand(int m, int n, String s, Deck d, Card c) { // Specialized Hand with specific Card
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        
        cards = new Card[m];
        cards[0] = c;
        amount++;
        System.out.print("Split hand is made w/ first card = ");
        cards[0].print();
        createHand(n, 1); 
    }

    // Creates the hand
	private void createHand(int size, int start) {
        if (deck.getAmountAvail() - size < 0) { // Checks whether the deck has enough cards before creating hand
            throw new IndexOutOfBoundsException("Not enough cards in deck");
        }
        if (start == 1) {
            System.out.println("start = " + start + " & size = " + size);
        }

		for (int i = start; i < size; i++) {
            drawCard();
		}
	}

    // draws a singular card
    public void drawCard() {
        Card c = deck.getCardRandom();
        cards[amount] = c;
        amount++;
    }

    // removes specific card from the hand
    public void removeCard(Card c) {
        boolean adjust = false;
        amount--;
        for (int i = 0; i < amount; i++) {
            if (cards[i].equals(c) && !adjust) {
                cards[i] = null;
                adjust = true;
            }
            if (adjust) {
                if (i != amount) {
                    cards[i] = cards[i + 1];
                } else {
                    cards[i] = null;
                }
            }
        }
    }

    // Shuffles the cards in hand
    public void shuffle() {
        Card temp;
        int r;
        for (int i = 0; i < amount; i++) { // swaps every card with some other random card
            r = (int) (Math.random()*amount);
            temp = cards[i];
            cards[i] = cards[r];
            cards[r] = temp;
        }
    }

    // Returns the card at index i
    public Card getCard(int i) {
        return cards[i];
    }

    // Returns the Deck the hand comes from
    public Deck getDeck() {
        return deck;
    }

    // returns the number of cards in the hand
    public int getLength() {
        return amount;
    }

    // returns the name of the owner of the hand
    public String getName() {
        return name;
    }

    // prints out the cards in the hand
    public void print() {
        for (int i = 0; i < amount; i++) {
            cards[i].print();
        }
    }

    // prints out card at index i
    public void printCard(int i) {
        cards[i].print();
    }
}
