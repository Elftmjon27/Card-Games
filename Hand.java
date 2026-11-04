import java.util.ArrayList;

public class Hand {
    private int size;
    private String name;
    private ArrayList<Card> cards = new ArrayList<>(); // bad name, can't think of a better one
    // cards is an arraylist b/c regular arrays' property of immutable annoying when trying to add cards to hands
    private Deck deck; // another bad name

    public Hand() { // default constructor
        size = 5;
        name = "Player";
        deck = new Deck();
        createHand(0);
    }

    public Hand(int n, String s, Deck d) { // specialized constructor
        size = n;
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        createHand(0);
    }

    public Hand(int n, String s, Deck d, Card c) { // Specialized Hand with specific Card
        size = n;
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        cards.add(c);
        createHand(1);
    }

    // Creates the hand
	private void createHand(int s) {
        if (deck.getAmountAvail() - size < 0) { // Checks whether the deck has enough cards before creating hand
            throw new IndexOutOfBoundsException("Not enough cards in deck");
        }

		for (int i = s; i < size; i++) {
            drawCard();
		}
	}

    // draws a singular card
    public void drawCard() {
        Card c = deck.getCardRandom();
        cards.add(c);
    }

    // removes specific card from the hand
    public void removeCard(Card c) {
        cards.remove(c);
        size--;
    }

    // Shuffles the cards in hand
    public void shuffle() {
        Card temp;
        int r;
        for (int i = 0; i < size; i++) { // swaps every card with some other random card
            r = (int) (Math.random()*size);
            temp = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, temp);
        }
    }

    // prints out the cards in the hand
    public void print() {
        for(Card c: cards) {
            c.print();
        }
    }

    // prints out card at index i
    public void printCard(int i) {
        cards.get(i).print();
    }

    // Returns the card at index i
    public Card getCard(int i) {
        return cards.get(i);
    }

    // Returns the Deck the hand comes from
    public Deck getDeck() {
        return deck;
    }

    // returns the number of cards in the hand
    public int getLength() {
        return size;
    }

    // updates the size of the hand (use when adding or removing cards to the hand)
    public void updateSize(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Size can't be negative");
		}
        size = n;
    }

    // returns the name of the owner of the hand
    public String getName() {
        return name;
    }
}
