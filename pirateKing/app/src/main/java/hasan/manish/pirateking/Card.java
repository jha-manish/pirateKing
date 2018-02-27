package hasan.manish.pirateking;

/**
 * Created by manish on 1/14/18.
 */

public class Card {

    //Assigning suits an integer value
    private static final int ESCAPES=1;
    private static final int YELLOW=2;
    private static final int GREEN=3;
    private static final int PURPLE=4;
    private static final int BLACK=5;
    private static final int PIRATES=6;
    private static final int SKULLKING=7;

    private int cardNum=0, suitNum=0 ;

    //Constructor for the card class which takes a int suit value and a integer card value as an argument
    public Card(int suit, int card){
        cardNum=card;
        suitNum=suit;
    }

    //This function returns the Suit of the associated card
    public int getSuitNum() {
        return suitNum;
    }

    //This function returns the card value of the associated card
    public int getCardNum() {

        return cardNum;
    }

}
