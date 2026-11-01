public class Card { // blueprints for individual cards
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
	
	public int getPoint() {
		return point;
	}

	public boolean equals(Card c) { // comparing points not necessary, unless cards from dif. games are somehow being compared
		return (suit.equals(c.getSuit())) && (face.equals(c.getFace()));
	}

	public void print() {
		System.out.println(face + " of " + suit);
	}
}
