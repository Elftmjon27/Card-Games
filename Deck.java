public class Deck {
	private String[] suits;
    private String[] faces;
    private int[] points; // points correspond to faces
	private Card[] deck;
	
	public Deck() {
		deck = new Card[7];
		suits = {"Clubs", "Spades", "Hearts", "Diamonds"};
		faces = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
		points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
	}
	
	public Deck(int n) {
		if (n >= 0) {
			deck = new Card[n];
		} // Nothing happens if you put negative sized deck
		suits = {"Clubs", "Spades", "Hearts", "Diamonds"};
		faces = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
		points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
	}
	
	public Deck(String[] s1, String[] s2, int[] i) {
		deck = new Card[7];
		if (s1.length() > 0 && s2.length() > 0 && i.length() == s2.length()) { 
			suits = s1;
			faces = s2;
			points = i;
		}
	}
	
	public Deck(int n, String[] s1, String[] s2, int[] i) {
		if (n >= 0) {
			deck = new Card[n];
		} // Nothing happens if you put negative sized deck
		if (s1.length() > 0 && s2.length() > 0 && i.length() == s2.length()) { 
			suits = s1;
			faces = s2;
			points = i;
		} // Nothing happsn if you have zero suit or face options
	}
	
	public createDeck() {
		int rs = (int) (Math.random() * suits.length());
		int rf = (int) (Math.random() * faces.length());
		for (int i = 0; i < deck.length(); i++) {
			deck[i] = new Card()
		}
	}
}
