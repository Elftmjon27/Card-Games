import java.util.ArrayList;

public class Hand {
    private int size;
    private String name;
    private ArrayList<Card> cards = new ArrayList<>(); // bad name, can't think of a better one
    // cards is an arraylist b/c regular arrays' property of immutable annoying when trying to add cards to hands
    private Deck deck; // another bad name

    public Hand() { // default
        size = 5;
        name = "Player";
        deck = new Deck();
        createHand(0);
    }

    public Hand(int n, String s, Deck d) { // Specialized Hand
        size = n;
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        createHand(0);
    }

    public Hand(int n, String s, Deck d, Card c) { // SPecialized Hand with specific Card
        size = n;
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        drawCard(c);
        createHand(1);
    }

    // Creates the hand
	private void createHand(int s) {
        if (deck.getAmountAvail() - size < 0) {
            System.out.println("The deck doesn't have enough cards to create a new hand");
            return;
        }

		for (int i = s; i < size; i++) {
            drawCard();
		}
	}

    // helper method to get available card
    private Card findCard() {
        if (deck.getAmountAvail() == 0) {
            throw new IndexOutOfBoundsException("The Deck is empty");
        }

        int r = (int) (Math.random()*deck.getLength());
        // As the deck gets smaller (relative to initial size), this will take longer and longer (be aware of run-time)

        while (!(deck.getAvail(r))) { // ensures card is available, only false if card not available
			r = (int) (Math.random()*deck.getLength());
		}

        deck.updateCard(r);
        return deck.getCard(r);
    }

    // adds a random card available in the deck
    public void drawCard() {
        cards.add(findCard());
    }

    // adds a specific card (doesn't check if available in the deck)
    public void drawCard(Card c) {
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
        Card c = cards.get(i);
        c.print();
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
    public void updateSize() {
        size++;
    }
}