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
                }

        for(int i=5;i<=5;i++)
            for(int j=1; j<=4; j++){
            cardDeck[count++]= new Card(i,j);
            }

        for(int i=6;i<=6;i++)
            for(int j=1; j<=4; j++){
                cardDeck[count++]= new Card(i,j);
            }

    }

}
