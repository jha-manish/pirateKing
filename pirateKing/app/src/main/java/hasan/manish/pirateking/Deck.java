package hasan.manish.pirateking;

/**
 * Created by manish on 1/15/18.
 */

public class Deck {
    private Card[] cardDeck;
    private int top_card = 0;

    public Deck() {
        cardDeck = new Card[57];
        int count = 0;

        //creating new cards and setting the card value using the j offset
        for (int i = 2; i <= 5; i++)
            for (int j = 5; j <= 17; j++) {
                cardDeck[count++] = new Card(i,j);
                }

        //setting an offset for keeping track of the value associated with each special card(Pirates, escapes, Skull King)
        for(int i=6;i<=6;i++)
            for(int j=18; j<=22; j++){
            cardDeck[count++]= new Card(i,j);
            }

        //creating one pirate king card
        for(int i=7;i<=7;i++)
            for(int j=23; j<=23; j++){
                cardDeck[count++]= new Card(i,j);
            }

        //creating escape cards with the lowest j values
        for(int i=1;i<=1;i++)
            for(int j=0; j<=4; j++){
                cardDeck[count++]= new Card(i,j);
            }

        shuffle();
    }

    //This function shuffles the cards in the Deck using the Math.random function
    public void shuffle() {
        for (int i = cardDeck.length - 1; i > 0; i--) {
            int rand = (int) (Math.random() * (i + 1));
            Card temp = cardDeck[i];
            cardDeck[i] = cardDeck[rand];
            cardDeck[rand] = temp;
        }
    }

    //This function returns the top card of the deck
    public Card dealCard(){

        return cardDeck[top_card++];

    }
}

