package hasan.manish.pirateking;

/**
 * Created by manish on 1/14/18.
 */

public class Card {

    //Test Comments
    private static final int ESCAPES=1;
    private static final int YELLOW=2;
    private static final int GREEN=3;
    private static final int PURPLE=4;
    private static final int BLACK=5;
    private static final int PIRATES=6;
    private static final int SKULLKING=7;

    private int cardNum=0, suitNum=0 ;

    public Card(int suit, int card){
        cardNum=card;
        suitNum=suit;
    }

    public int getSuitNum() {
        return suitNum;
    }

    public int getCardNum() {
        return cardNum;
    }

}
