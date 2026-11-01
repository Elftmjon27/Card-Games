public class Hand {
    private int size;
    private String name;
    private ArrayList<Card> cards = new ArrayList<>; // bad name, can't think of a better one
    // cards is an arraylist b/c regular arrays' property of immutable annoying when trying to add cards to hands
    private Deck deck; // another bad name

    public Hand() {
        size = 5;
        name = "Player";
        deck = new Deck();
        createHand(0);
    }

    public Hand(int n, String s, Deck d) {
        size = n;
        name = s;
        deck = d;
        if (deck.getlength() > n) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        createHand(0);
    }

    public Hand(int n, String s, Deck d, Card c) {
        size = n;
        name = s;
        deck = d;
        if (deck.getlength() > n) {
            throw new IllegalArgumentException("Hand cannot be bigger than the deck");
        }
        drawCard(c);
        createHand(1);
    }


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
            System.out.println("The Deck is empty, you cannot get more cards");
            return;
        }

        int r = (int) (Math.random()*deck.getLength());
        // As the deck gets smaller (relative to initial size), this will take longer and longer (be aware of run-time)

        while (!(deck.getAvail(r))) { // ensures card is available, only false if card not available
			r = (int) (Math.random()*pack.length());
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
        cards.remove(Card c);
        size--;
    }

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

    public void print() {
        for(Card c: cards) {
            c.print();
        }
    }

    public void printCard(int i) {
        Card c = cards.get(i);
        c.print();
    }

    public Card getCard(int i) {
        return cards[i];
    }

    public int getLength() {
        return size;
    }

    public Deck getDeck() {
        return deck;
    }
}