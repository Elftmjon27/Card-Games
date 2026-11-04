/*
	Name: Jonathan
	Class Name: Deck
	General Purpose: Serve as the collection of cards out of the pack, stores the cards that players will draw from
*/

import java.util.ArrayList;
public class Deck {
	private Card[] pack; // deck of cards created from cards described by suits, faces, and point values of the faces
	private ArrayList<Card> availCards = new ArrayList<>(); // subset of pack, stores all cards available for players to draw
	private ArrayList<Card> discardedCards = new ArrayList<>(); // subset of deck, stores all cards that have been discarded, thus availale to be added back into the deck

	public Deck() { // default constructor
		pack = new Card[52];
		createDeck(new String[]{"Clubs", "Spades", "Hearts", "Diamonds"}, 
		new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}, 
		new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14});
	}
	
	public Deck(String[] s1, String[] s2, int[] p) { // specialized constructor
		if (s1.length * s2.length != 0 && s2.length == p.length) { 
			pack = new Card[s1.length * s2.length];
			createDeck(s1, s2, p);
		} else {
			throw new IllegalArgumentException("No empty arrays && point value array must be the same length as face array");
		}
	}

	// helper method for both constructors, creates the collection of cards
	private void createDeck(String[] suits, String[] faces, int[] points) {
		int index = 0;
		Card c;
		for (String s : suits) {
			for (int i = 0; i < faces.length; i++) {
				c = new Card(s, faces[i], points[i]);
				pack[index] = c;
				availCards.add(c);
			}
		}
	}

	// Draws a singular card from the avail cards in the deck, removing it from viable options
	public Card getCardRandom() {
		if (availCards.size() == 0) {
			throw new IndexOutOfBoundsException("The Deck is empty");
		}

		Card c = availCards.get((int) (Math.random() * availCards.size()));
		availCards.remove(c);
		return c;
	}

	// retuns the length of the deck
	public int getLength() {
		return pack.length;
	}

	// returns the number of cards available in the deck
	public int getAmountAvail() {
		return availCards.size();
	}

	// Adds card to discard pile, allows card to be put back into the deck later
	public void addDiscardedCard(Card c) {
		discardedCards.add(c);
	}

	// when there are not enough available cards, refills them
	public void refillDeck() {
		for (Card c : discardedCards) {
			availCards.add(c);
			discardedCards.remove(c);
		}
	}
}
