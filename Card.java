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
	
	public void setSuit(String s) {
		suit = s;
	}
	
	public void setFace(String s) {
		face = s;
	}
	
	public void setPoint(int n) {
		point = n;
	}
}
