package hasan.manish.pirateking;

import java.util.ArrayList;

/**
 * Created by hasan on 16/01/18.
 */

public class Player {

    ArrayList<Card> hand;
    private String player_name;
    private int points;
    boolean isPlaying,isOut;

    public Player(String name, int amount){
        hand = new ArrayList<>();
        player_name=name;
        points = amount;
        isPlaying=true;
        isOut=false;
    }

    public String getPlayer_name(){ return player_name; }

    public int getPoints(){
        return points;
    }

    public void deductPoints(int amount){
        points-=amount;
    }

    public void addPoints(int amount){
        points+=amount;
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void clearHand(){
        hand.clear();
    }

    //Sort cards in player's hand by cardValue
    public void sortCards(){

        ArrayList<Card>tmp = new ArrayList<>();
        while(hand.size() != 0){
            Card best = hand.get(0);

            for (int i = 1; i<hand.size(); i++){
                if(hand.get(i).getCardNum()>best.getCardNum()){
                    best = hand.get(i);
                }
            }

            tmp.add(best);
            hand.remove(best);
        }
        hand = tmp;
    }

}
