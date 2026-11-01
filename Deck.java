public class Deck { // Deck of cards at beginning of game (from box)
	private String[] suits;
    private String[] faces;
	private int[] points; // corresponds to faces

	private Card[] pack; // deck of cards created from cards described by suits, faces, and 
	private boolean[] avail; // stores whether the card is available in the pack
	private int amountAvail;
	
	public Deck() { // default
		suits = new String[]{"Clubs", "Spades", "Hearts", "Diamonds"};
		faces = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
		points = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		pack = new Card[52];
		avail = new boolean[52];
		amountAvail = 52;
	}
	
	public Deck(String[] s1, String[] s2, int[] i) { // specific deck of cards
		int l = s1.length * s2.length;
		if (l != 0 && s2.length > 0 && s2.length == i.length) { 
			suits = s1;
			faces = s2;
			points = i;
			pack = new Card[l];
			avail = new boolean[l];
			amountAvail = l;
			createDeck();
		} else {
			throw new IllegalArgumentException("Illegal Arguments: No empty arrays && point value array must be the same length as face array");
		}
	}

	// helper method for specialized constructor
	private void createDeck() {
		int index = 0;
		for (String s : suits) {
			for (int i = 0; i < faces.length; i++) {
				pack[index] = new Card(s, faces[i], points[i]);
				avail[index] = true;
				index++;
			}
		}
	}

	// getter for Hand class, returns whether card is available in deck
	public boolean getAvail(int i) {
		return avail[i];
	}

	// getter for Hand class
	public Card getCard(int i) { // int i is index of card
		return pack[i];
	}

	// updates the availability of a card
	public void updateCard(int i) { // int is index of card
		if (avail[i]) {
			amountAvail--; // if true, then it will turn to false, so lower amount Avail
		} else {
			amountAvail++;
		}
		avail[i] = !avail[i]; // swaps parity
	}

	// retuns the length of the deck
	public int getLength() {
		return pack.length;
	}

	public int getAmountAvail() {
		return amountAvail;
	}
}
