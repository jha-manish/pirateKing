package hasan.manish.pirateking;

import java.util.ArrayList;

/**
 * Created by hasan on 16/01/18.
 */

public class Player {

    ArrayList<Card> hand;
    private String player_name;
    private int points;
    boolean isPlaying, isOut;

    //The constructor for the Player class which takes in string and an integer for the player name
    //and the number of points respectively
    public Player(String name, int amount){
        hand = new ArrayList<>();
        player_name=name;
        points = amount;
        isPlaying=true;
        isOut=false;
    }

    //This function returns the player name
    public String getPlayer_name(){

        return player_name;
    }

    //This function returns the number of points
    public int getPoints(){

        return points;
    }

    //This function adds a card to the player's hand
    public void addCard(Card card){

        hand.add(card);
    }

    //This function is used to clear a player's hand
    public void clearHand(){

        hand.clear();

    }

    //This function sort cards in player's hand by the card value
    public void sortCards(){

        ArrayList<Card>tmp = new ArrayList<>();
        while(hand.size() != 0){
            Card best = hand.get(0);

            //iterating through the number of cards in the hand
            for (int i = 1; i<hand.size(); i++){
                //determing the best card by comparing the card value and the suit rank
                if(hand.get(i).getSuitNum()>best.getSuitNum() || hand.get(i).getCardNum()>best.getCardNum()){
                    best = hand.get(i);
                }
            }

            tmp.add(best);
            hand.remove(best);
        }
        hand = tmp;
    }

    //This function calculates the rank of a given hand by returning the the suit value of the card
    int getRank(){
        if (hand.size()!=0){
            for(int i = 1; i<hand.size(); i++){
                if (hand.get(i).getSuitNum() == 7)
                    return 7;
                else if (hand.get(i).getSuitNum() == 6)
                    return 6;
                else if (hand.get(i).getSuitNum() == 5)
                    return 5;
                else if (hand.get(i).getSuitNum() == 4)
                    return 4;
                else if (hand.get(i).getSuitNum() == 3)
                    return 3;
                else if (hand.get(i).getSuitNum() == 2)
                    return 2;
                else if (hand.get(i).getSuitNum() == 1)
                    return 1;
            }
        }
        return 0;
    }

}
