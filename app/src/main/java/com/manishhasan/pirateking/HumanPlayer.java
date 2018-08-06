package com.manishhasan.pirateking;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, int amount) {
        super(name, amount);
        isPlaying=true;
        isOut=false;
    }
}
