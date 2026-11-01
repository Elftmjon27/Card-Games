public class Card { // blueprints for individual cards
	private String suit; // group of cards
	private String face; // design on card
	private int point; // value of card
	
	public Card(String s, String f, int p) {
		suit = s;
		face = f;
		point = p;
	}
	
	// returns card's suit
	public String getSuit() {
		return suit;
	}
	
	// returns card's face
	public String getFace() {
		return face;
	}
	
	// returns card's point value
	public int getPoint() {
		return point;
	}

	// modifies the card's value
	public void updatePoint(int n) {
		point = n;
	}

	// Prints the Card's face and suit
	public void print() {
		System.out.println(face + " of " + suit);
	}

	// Rewritten equals statement
	public boolean equals(Card c) { // comparing points not necessary, unless cards from dif. games are somehow being compared
		return (suit.equals(c.getSuit())) && (face.equals(c.getFace()));
	}
}
