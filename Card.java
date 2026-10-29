public class Card {
	private String suit; // group of cards
	private String face; // design on card
	private int point; // value of card
	
	public Card(String s, String f, int p) {
		suit = s;
		face = f;
		point = p;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String getFace() {
		return face;
	}
	
	public String getPoint() {
		return point;
	}

	public boolean equals(Card c) { // comparing points not necessary, unless cards from dif. games are somehow being compared
		return (suit.equals(c.getSuit())) && (face.equals(c.getFace()));
	}
}
