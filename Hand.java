public class Hand {
    private int size;
    private String name;
    private ArrayList<Card> cards; // bad name, can't think of a better one
    // cards is an arraylist b/c regular arrays' property of immutable annoying when trying to add cards to hands
    private Deck deck; // another bad name

    public Hand(int n, String s, Deck d) {
        size = n;
        name = s;
        cards = new ArrayList<>;
        deck = d;
        createHand();
    }


	private void createHand() {
		for (int i = 0; i < size; i++) {
            drawCard();
		}
	}

    // helper method to get available card
    private Card findCard() {
        int r = (int) (Math.random()*deck.getLength());

        while (!(deck.getAvail(r))) { // ensures card is available, only false if card not available
			r = (int) (Math.random()*pack.length());
		}

        deck.updateCard(r);
        return deck.getCard(r);
    }

    public void drawCard() {
        cards.add(findCard());
    }

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

    public print() {
        for(Card c: cards) {
            c.print();
        }
    }
}