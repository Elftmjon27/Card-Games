/*
	Name: Jonathan
	Class Name: Hand
	General Purpose: Serve as the collection of cards that correspond to the player
*/

import java.util.ArrayList;
public class Hand {
	private int maxSize; // maximum cards a hand can have
    private String name; // name of player that has this hand
    private Card[] cards;
    private int amount = 0;
    private ArrayList<Card> cards = new ArrayList<>(); // bad name, can't think of a better one
    // cards is an arraylist b/c regular arrays' property of immutable annoying when trying to add cards to hands
    private Deck deck; // another bad name

    public Hand(int m, int n, String s, Deck d) { // specialized constructor
		maxSize = m;
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        
        cards = new Card[m];
        createHand(n);
    }

    public Hand(int m, int n, String s, Deck d, Card c) { // Specialized Hand with specific Card
		maxSize = m;
        name = s;
        deck = d;
        if (n > deck.getLength()) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        
        cards.add(c);
        createHand(n - 1);
    }

    // Creates the hand
	private void createHand(int size) {
        if (deck.getAmountAvail() - size < 0) { // Checks whether the deck has enough cards before creating hand
            throw new IndexOutOfBoundsException("Not enough cards in deck");
        }

		for (int i = 0; i < size; i++) {
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
        cards.remove(c);
    }

    // Shuffles the cards in hand
    public void shuffle() {
        Card temp;
        int r;
        for (int i = 0; i < cards.size(); i++) { // swaps every card with some other random card
            r = (int) (Math.random()*cards.size());
            temp = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, temp);
        }
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
        return cards.size();
    }

    // returns the name of the owner of the hand
    public String getName() {
        return name;
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
}
