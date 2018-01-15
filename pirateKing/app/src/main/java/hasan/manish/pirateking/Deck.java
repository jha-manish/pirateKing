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
        for (int i = 1; i <= 4; i++)
            for (int j = 2; j <= 14; j++) {
                cardDeck[count++] = new Card(i, j);
                cardDeck[count++].setCardValue(j);
                }

        for(int i=5;i<=5;i++)
            for(int j=1; j<=4; j++){
            cardDeck[count++]= new Card(i,j);
            }

        for(int i=6;i<=6;i++)
            for(int j=1; j<=4; j++){
                cardDeck[count++]= new Card(i,j);
            }

        for(int i=7;i<=7;i++)
            for(int j=1; j<=1; j++){
                cardDeck[count++]= new Card(i,j);
            }

        cardDeck[56].setCardValue(15);
        cardDeck[57].setCardValue(15);
        cardDeck[58].setCardValue(15);
        cardDeck[59].setCardValue(15);
        cardDeck[60].setCardValue(0);
        cardDeck[61].setCardValue(0);
        cardDeck[62].setCardValue(0);
        cardDeck[63].setCardValue(0);
        cardDeck[64].setCardValue(16);
        shuffle();
    }

    public void shuffle() {
        for (int i = cardDeck.length - 1; i > 0; i--) {
            int rand = (int) (Math.random() * (i + 1));
            Card temp = cardDeck[i];
            cardDeck[i] = cardDeck[rand];
            cardDeck[rand] = temp;
        }
    }

    public Card dealCard(){
            return cardDeck[top_card++];
        }
}

