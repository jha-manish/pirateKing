package hasan.manish.pirateking;

/**
 * Created by manish on 1/15/18.
 */

public class Deck {
    private Card[] cardDeck;
    private int top_card = 0;

    public Deck() {
        cardDeck = new Card[64];
        int count = 0;
        //creating new cards and setting the value 
        for (int i = 1; i <= 4; i++)
            for (int j = 2; j <= 14; j++) {
                cardDeck[count++] = new Card(i, j);
                }
        //setting an offset for keeping track of the value associated with each special card(Pirates, escapes, Skull King)
        for(int i=5;i<=5;i++)
            for(int j=20; j<=24; j++){
            cardDeck[count++]= new Card(i,j);
            }

        for(int i=6;i<=6;i++)
            for(int j=15; j<=19; j++){
                cardDeck[count++]= new Card(i,j);
            }

        for(int i=7;i<=7;i++)
            for(int j=25; j<=25; j++){
                cardDeck[count++]= new Card(i,j);
            }

        shuffle();
    }
    /*
     * public void shuffle()
     *
     * Shuffles the cards in the Deck using the Math.random function
     *
     * No pre- or postconditions and no parameters.
     *
     */
    public void shuffle() {
        for (int i = cardDeck.length - 1; i > 0; i--) {
            int rand = (int) (Math.random() * (i + 1));
            Card temp = cardDeck[i];
            cardDeck[i] = cardDeck[rand];
            cardDeck[rand] = temp;
        }
    }

    /*
     * public Card dealCard()
     *
     * Deals the cards in the deck
     *
     * No pre- or postconditions and no parameters.
     *
     * Returns:
     * The top card in the Deck
     */

    public Card dealCard(){
            return cardDeck[top_card++];
        }
}

