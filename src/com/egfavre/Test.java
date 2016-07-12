package com.egfavre;

import java.util.ArrayList;

/**
 * Created by user on 7/12/16.
 */
public class Test {
    public static void main(String[] args) {

        Card card1 = new Card(Card.Suit.DIAMONDS, Card.Rank.ACE);
        Card card4 = new Card(Card.Suit.CLUBS, Card.Rank.ACE);
        Card card3 = new Card(Card.Suit.CLUBS, Card.Rank.EIGHT);
        Card card2 = new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(card1);
        hand.add(card2);
        hand.add(card3);
        hand.add(card4);

        System.out.println(hand.get(0).getRank());
    }
}