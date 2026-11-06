/*
	Name: Jonathan
	Class Name: Card
	General Purpose: Serve as an individual card, storing the suit, face, and point value of the card
*/

public class Card {
	private String suit; // group of cards
	private String face; // design on card
	private int point; // value of card
	
	// Card is created given the suit, face, and point value of the card
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

	// modifies the card's point value
	public void updatePoint(int n) {
		point = n;
	}

	// modfiies the card's suit
	public void updateSuit(String s) {
		suit = s;
	}

	// Prints the Card's face and suit
	public void print() {
		System.out.println(face + " of " + suit);
	}

	// Rewritten equals statement
	public boolean equals(Card c) { // comparing points not necessary, points should always correspond to face
		return (suit.equals(c.getSuit())) && (face.equals(c.getFace()));
	}
}
