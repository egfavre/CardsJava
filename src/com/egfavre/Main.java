package com.egfavre;

import javax.swing.plaf.FontUIResource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.egfavre.Card.Rank.*;

public class Main {

    static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    public static void main(String[] args) {
        HashSet<Card> deck = createDeck();
        HashSet<HashSet<Card>> hands = createHands(deck);
        HashSet<HashSet<Card>> flushes = hands.stream()
                .filter(Main::isFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> straights = hands.stream()
                .filter(Main::isSequential)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> straightFlushes = flushes.stream()
                .filter(Main::isSequential)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> fourOfKind = hands.stream()
                .filter(Main::isFourOfKind)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> threeOfKind = hands.stream()
                .filter(Main::isThreeOfKind)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> twoOfKind = hands.stream()
                .filter(Main::isTwoOfKind)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));

        System.out.println("Flushes: " + flushes.size());
        System.out.println("Straights: " + straights.size());
        System.out.println("Two of A Kind: " + twoOfKind.size());
        System.out.println("Three of A Kind: " + threeOfKind.size());
        System.out.println("Straight Flushes: " + straightFlushes.size());
        System.out.println("Four of A Kind: " + fourOfKind.size());
    }

    //all possible 4 card hands
    static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for (Card c1 : deck) {
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for (Card c2 : deck2) {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for (Card c3 : deck3) {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for (Card c4 : deck4) {
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }

    static boolean isFlush(HashSet<Card> hand) {
        HashSet<Card.Suit> suits = hand.stream()
                .map(card -> card.suit)
                .collect(Collectors.toCollection(HashSet<Card.Suit>::new));
        return suits.size() == 1;
    }

    static boolean isSequential(HashSet<Card> hand) {
        //get each card's rank value
        boolean sequential = false;
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card card : hand) {
            Card.Rank rank = card.getRank();
            int rankInt = rank.ordinal();
            ranks.add(rankInt);
        }
        Collections.sort(ranks);
        if (ranks.get(0) + 1 == ranks.get(1) && ranks.get(1) + 1 == ranks.get(2) && ranks.get(2) + 1 == ranks.get(3)) {
            sequential = true;
        }
        return sequential == true;
    }

    static boolean isFourOfKind(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = hand.stream()
                .map(card -> card.rank)
                .collect(Collectors.toCollection(HashSet<Card.Rank>::new));
        return ranks.size() == 1;
    }

    static boolean isThreeOfKind(HashSet<Card> hand) {
        boolean threeOfKind;
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card card : hand) {
            Card.Rank rank = card.getRank();
            int rankInt = rank.ordinal();
            ranks.add(rankInt);
        }
        Collections.sort(ranks);
        if (ranks.get(0) == ranks.get(1) && ranks.get(1) == ranks.get(2) && ranks.get(2) != ranks.get(3)) {
            threeOfKind = true;
        }
        else if (ranks.get(0) != ranks.get(1) && ranks.get(1) == ranks.get(2) && ranks.get(2) == ranks.get(3)){
            threeOfKind = true;
        }
        else {
            threeOfKind = false;
        }
        return threeOfKind == true;
    }

    static boolean isTwoOfKind(HashSet<Card> hand) {
        boolean twoOfKind = false;
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card card : hand) {
            Card.Rank rank = card.getRank();
            int rankInt = rank.ordinal();
            ranks.add(rankInt);
        }
        Collections.sort(ranks);
        if (ranks.get(0) == ranks.get(1) && ranks.get(2) == ranks.get(3) && ranks.get(0) != ranks.get(3)) {
            twoOfKind = true;
        }
        return twoOfKind == true;
    }
}