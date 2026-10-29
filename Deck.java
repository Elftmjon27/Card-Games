public class Deck { // Deck of cards at beginning of game (from box)
	private String[] suits;
    private String[] faces;

	private int totalCardsChosen = 0;
	private Card[] chosenCards; // Cards that have been put into players' hands and no longer available in the deck 
	
	public Deck() { // default
		suits = {"Clubs", "Spades", "Hearts", "Diamonds"};
		faces = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
		chosenCards = new Card[52];
	}
	
	public Deck(String[] s1, String[] s2) {
		if (s1.length() > 0 && s2.length() > 0) { 
			suits = s1;
			faces = s2;
			chosenCards = new Card[s1.length() * s2.length()];
		} else {
			throw new IllegalArgumentException("String array arguments cannot be negative")
		}
	}

	public void dealCards(int handSize) {
		for (int i = 0; i < handSize; i++) {
			
		}
	}

	public boolean checkCardAvail(Card c) {
		for (int i = 0; i < totalCardsChosen; i++) {
			if (c.equals(chosenCards[i])) {
				return true;
			}
		}
		return false;
	}
}
