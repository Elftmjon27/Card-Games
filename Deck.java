/*
	Name: Jonathan
	Class Name: Deck
	General Purpose: Serve as the collection of cards out of the pack, stores the cards that players will draw from
*/

import java.util.ArrayList;
public class Deck {
	private Card[] pack; // deck of cards created from cards described by suits, faces, and point values of the faces
	private Card[] availCards; // subset of pack, stores all cards available for players to draw
	private int availCardsSize;
	private Card[] discardedCards; // subset of deck, stores all cards that have been discarded, thus availale to be added back into the deck
	private int discardedCardsSize = 0;

	public Deck() { // default constructor
		pack = new Card[52];
		availCards = new Card[52];
		availCardsSize = 52;
		discardedCards = new Card[52];
		createDeck(new String[]{"Clubs", "Spades", "Hearts", "Diamonds"}, 
		new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}, 
		new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14});
	}
	
	public Deck(String[] s1, String[] s2, int[] p) { // specialized constructor
		int l1 = s1.length;
		int l2 = s2.length;
		if (l1 * l2 != 0 && l2 == p.length) { 
			pack = new Card[l1 * l2];
			availCards = new Card[l1*l2];
			availCardsSize = l1*l2;
			discardedCards = new Card[l1*l2];
			createDeck(s1, s2, p);
		} else {
			throw new IllegalArgumentException("No empty arrays && point value array must be the same length as face array");
		}
	}

	// helper method for both constructors, creates the collection of cards
	private void createDeck(String[] suits, String[] faces, int[] points) {
		int index = 0;
		int l = faces.length;
		Card c;
		for (String s : suits) {
			for (int i = 0; i < l; i++) {
				c = new Card(s, faces[i], points[i]);
				pack[index] = c;
				availCards[index] = c;
				index++;
			}
		}
	}

	// Draws a singular card from the avail cards in the deck, removing it from viable options
	public Card getCardRandom() {
		if (availCardsSize == 0) {
			throw new IndexOutOfBoundsException("The Deck is empty");
		}

		int r = (int) (Math.random()*pack.length);
		
		while (availCards[r] != null) {
			r = (int) (Math.random()*pack.length);
		}
		
		Card c = availCards[r];
		availCards[r] = null;
		addDiscardedCard(c);
	
		return c;
	}

	// retuns the length of the deck
	public int getLength() {
		return pack.length;
	}

	// returns the number of cards available in the deck
	public int getAmountAvail() {
		return availCardsSize;
	}

	// Adds card to discard pile, allows card to be put back into the deck later
	public void addDiscardedCard(Card c) {
		discardedCards[discardedCardsSize] = c;
		discardedCardsSize++;
	}

	// when there are not enough available cards, refills them
	public void refillDeck() {
		int l = pack.length;
		int pos = 0;
		for (int i = 0; i < l; i++) {
			if (availCards[i] == null) {
				availCards[i] = discardedCards[pos];
				
				availCardsSize++;
				discardedCardsSize--;
				pos++;
				
				if (discardedCardsSize == 0) {
					i = l;
				}
			}
		}
	}
}
