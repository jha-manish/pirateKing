package hasan.manish.pirateking;

public class CPU_Player extends Player{

    public CPU_Player(String name, int amount) {

        super(name, amount);
        isPlaying=true;
        isOut=false;
    }
    int round=0;

    //This function uses the rank of a given hand to set what decisions the CPU player will make
    public String getBestDecision(int numOfPlayers){
        round++;
        switch (getRank()){
            case 1:
                    return "playEscapeCard";

            case 2:
                if (round <= 5 && hand.get(0).getCardNum()>7)
                    return "playYellowCard";
                else
                    return "playLowerSuit";

            case 3:
                if (round <= 5 && hand.get(0).getCardNum()>7)
                    return "playGreenCard";
                else
                    return "playLowerSuit";

            case 4:
                if (round <= 5 && hand.get(0).getCardNum()>7)
                    return "playPurpleCard";
                else
                    return "playLowerSuit";

            case 5:
                if (round <= 5 && hand.get(0).getCardNum()>7)
                    return "playBlackCard";
                else
                    return "playLowerSuit";

            case 6:
                if (round >= 5)
                    return "playPirateCard";
                else
                    return "playLowerSuit";

            case 7:
                if (round >= 5)
                    return "playSkullKingCard";
                else
                    return "playLowerSuit";

            default: return null;
        }
    }

    @Override
    public void clearHand() {
        hand.clear();
        round=0;
    }
}