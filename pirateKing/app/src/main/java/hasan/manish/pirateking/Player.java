package hasan.manish.pirateking;

import java.util.ArrayList;

/**
 * Created by hasan on 16/01/18.
 */

public class Player {

    ArrayList<Card> hand;
    private String player_name;
    private int money=0;
    boolean isPlaying,isOut;

    public Player(String name, int amount){
        player_name=name;
        money=amount;
        hand = new ArrayList<>();
        isPlaying=true;
        isOut=false;
    }

    public int getMoney() {
        return money;
    }
    public void addMoney(int amount){
        money += amount;
    }
    public void deductMoney(int amount){
        money -= amount;
    }

    public String getPlayer_name(){ return player_name; }

    public void addCard(Card card){
        hand.add(card);
    }

    public void clearHand(){
        hand.clear();
    }

    public void sortCards(){

    }
}
