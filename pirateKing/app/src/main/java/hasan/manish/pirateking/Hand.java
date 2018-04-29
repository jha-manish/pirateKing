package hasan.manish.pirateking;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand extends Activity {

    Deck deck;
    HumanPlayer human;
    CPU_Player cpu1;
    CPU_Player cpu2;
    CPU_Player cpu3;

    TextView CPU1, CPU2, CPU3, Human;
    ImageView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10;

    Button play;
    int points=0;

    ArrayList<Player> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_table);

        //setting the views for the names of the player
        CPU1 = findViewById(R.id.name_cpu1);
        CPU2 = findViewById(R.id.name_cpu2);
        CPU3 = findViewById(R.id.name_cpu3);
        Human =findViewById(R.id.name_human);

        //setting all the views for the cards of the human player
        card1 =findViewById(R.id.human_card1);
        card2 =findViewById(R.id.human_card2);
        card3 =findViewById(R.id.human_card3);
        card4 =findViewById(R.id.human_card4);
        card5 =findViewById(R.id.human_card5);
        card6 =findViewById(R.id.human_card6);
        card7 =findViewById(R.id.human_card7);
        card8 =findViewById(R.id.human_card8);
        card9 =findViewById(R.id.human_card9);
        card10 =findViewById(R.id.human_card10);
        play = findViewById(R.id.play);

        playerList = new ArrayList<>();

        //Initializes all players

        cpu1 = new CPU_Player("CPU1",0);
        cpu2 = new CPU_Player("CPU2",0);
        cpu3 = new CPU_Player("CPU3",0);
        human = new HumanPlayer("You", 0);

        play.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Human.setBackground(getDrawable(R.color.black_overlay));
                CPU1.setBackground(getDrawable(R.color.black_overlay));
                CPU2.setBackground(getDrawable(R.color.black_overlay));
                CPU3.setBackground(getDrawable(R.color.black_overlay));

                cpu1.clearHand();
                cpu2.clearHand();
                cpu3.clearHand();
                human.clearHand();

                playerList.clear();

                deck = new Deck();

                // dealing the cards...
                for (int i = 1; i <= 40; i++) {
                    switch (i%4){
                        case 0: human.addCard(deck.dealCard()); break;
                        case 1: cpu1.addCard(deck.dealCard()); break;
                        case 2: cpu2.addCard(deck.dealCard()); break;
                        case 3: cpu3.addCard(deck.dealCard()); break;
                    }
                }

                cpu1.sortCards();
                cpu2.sortCards();
                cpu3.sortCards();
                human.sortCards();

                play.setVisibility(View.INVISIBLE);

                    playCPU(0);
            }
        });

        /***********************************************************/
        // <-------------- Now the Game Begins... ------------------>
        /***********************************************************/

    }

    private void playCPU(final int start) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = start;
                while (!playerList.get(i).equals(human)){

                    CPU_Player cpuPlayer = (CPU_Player) playerList.get(i);
                    String playerName = cpuPlayer.getPlayer_name();

                    Log.i("tag",playerName+"'s turn");

                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",playerName);
                    msg.setData(bundle);

                    greenLightHandler.sendMessage(msg);

                    long futuretime = System.currentTimeMillis() + 2000;
                    while (System.currentTimeMillis() < futuretime){}

                    String decision = cpuPlayer.getBestDecision(playerList.size());
                    Log.i("tag",playerName+" decided to "+decision);

                    interpretDecision(playerName,decision);

                    futuretime = System.currentTimeMillis() + 500;
                    while (System.currentTimeMillis() < futuretime){}

                    if (decision.equals("show")) {
                        tempHandler.sendEmptyMessage(0);
                        return;
                    }

                    if (playerList.size()==1){
                        TwoPlayerFold(playerList.get(i));
                        return;
                    }

                    futuretime = System.currentTimeMillis() + 1000;
                    while (System.currentTimeMillis() < futuretime) {}

                    Log.i("tag","changing turn...");

                    Message msg2 = new Message();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("name",playerName);
                    msg2.setData(bundle2);

                    greyLightHandler.sendMessage(msg2);

                    futuretime = System.currentTimeMillis() + 200;
                    while (System.currentTimeMillis() < futuretime){}
                }

                humanTurnHandler.sendEmptyMessage(0);
            }
        };
        Thread sidethread = new Thread(runnable);
        sidethread.start();
    }

    Handler tempHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            decideWinner();
        }
    };
    Handler humanTurnHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            Log.i("tag","human turn");
            fn_bar.setVisibility(View.VISIBLE);
            Human.setBackground(getDrawable(R.color.green));
            if (playerList.size()==2 && !isShowEnabled) {
                fn_bar.addView(show);
                isShowEnabled = true;
            }
        }
    };

    Handler greenLightHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            switch (name){
                case "CPU1": CPU1.setBackground(getDrawable(R.color.green)); break;
                case "CPU2": CPU2.setBackground(getDrawable(R.color.green)); break;
                case "CPU3": CPU3.setBackground(getDrawable(R.color.green)); break;
            }
        }
    };

    Handler greyLightHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            switch (name){
                case "CPU1": CPU1.setBackground(getDrawable(R.color.black_overlay)); break;
                case "CPU2": CPU2.setBackground(getDrawable(R.color.black_overlay)); break;
                case "CPU3": CPU3.setBackground(getDrawable(R.color.black_overlay)); break;
            }
        }
    };

    Handler endGameHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");

            switch (name){
                case "CPU1": cards_cpu1.setVisibility(View.INVISIBLE);
                    cpu1.isPlaying=false;
                    playerList.remove(cpu1);    break;

                case "CPU2": cards_cpu2.setVisibility(View.INVISIBLE);
                    cpu2.isPlaying=false;
                    playerList.remove(cpu2);    break;

                case "CPU3": cards_cpu3.setVisibility(View.INVISIBLE);
                    cpu3.isPlaying=false;
                    playerList.remove(cpu3);    break;

            }
        }
    };

    Handler escapeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            Log.i("tag",name+" plays...");
            switch (name){
                case "CPU1": cpu1.getPoints();
                    text_money_cpu1.setText(String.valueOf(cpu1.getPoints())); break;
                case "CPU2": cpu2.getPoints();
                    text_money_cpu2.setText(String.valueOf(cpu2.getPoints())); break;
                case "CPU3": cpu3.getPoints();
                    text_money_cpu3.setText(String.valueOf(cpu3.getPoints())); break;
            }

            showCPUCards();
        }
    };

    Handler pirateHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            Log.i("tag",name+" plays...");
            switch (name){
                case "CPU1": cpu1.getPoints();
                    text_money_cpu1.setText(String.valueOf(cpu1.getPoints())); break;
                case "CPU2": cpu2.getPoints();
                    text_money_cpu2.setText(String.valueOf(cpu2.getPoints())); break;
                case "CPU3": cpu3.getPoints();
                    text_money_cpu3.setText(String.valueOf(cpu3.getPoints())); break;
            }

            showCPUCards();
        }
    };

    Handler skullKingHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            Log.i("tag",name+" plays...");
            switch (name){
                case "CPU1": cpu1.getPoints();
                    text_money_cpu1.setText(String.valueOf(cpu1.getPoints())); break;
                case "CPU2": cpu2.getPoints();
                    text_money_cpu2.setText(String.valueOf(cpu2.getPoints())); break;
                case "CPU3": cpu3.getPoints();
                    text_money_cpu3.setText(String.valueOf(cpu3.getPoints())); break;
            }

            showCPUCards();
        }
    };

    public void interpretDecision(String name, String decision){
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);

        switch (decision){
            case "playEscapeCard":
                msg.setData(bundle);
                escapeHandler.sendMessage(msg);
                break;

            case "playYellowCard":
                msg.setData(bundle);
                break;

            case "playGreenCard":
                msg.setData(bundle);
                break;

            case "playPurpleCard":
                msg.setData(bundle);
                break;

            case "playBlackCard":
                msg.setData(bundle);
                break;

            case "playPirateCard":
                msg.setData(bundle);
                pirateHandler.sendMessage(msg);
                break;

            case "playSkullKingCard":
                msg.setData(bundle);
                skullKingHandler.sendMessage(msg);
                break;

            case "playLowerSuit":
                msg.setData(bundle);
                break;
        }
    }

    Handler OnOffHandler = new Handler(){
        @TargetApi(21)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            int signal = msg.getData().getInt("signal");

            if (signal==1) {
                switch (name){
                    case "You": Human.setBackground(getDrawable(R.color.colorAccent)); break;
                    case "CPU1": CPU1.setBackground(getDrawable(R.color.colorAccent)); break;
                    case "CPU2": CPU2.setBackground(getDrawable(R.color.colorAccent)); break;
                    case "CPU3": CPU3.setBackground(getDrawable(R.color.colorAccent)); break;
                }
            }else {
                switch (name){
                    case "You": Human.setBackground(getDrawable(R.color.black_overlay)); break;
                    case "CPU1": CPU1.setBackground(getDrawable(R.color.black_overlay)); break;
                    case "CPU2": CPU2.setBackground(getDrawable(R.color.black_overlay)); break;
                    case "CPU3": CPU3.setBackground(getDrawable(R.color.black_overlay)); break;
                }
            }
        }
    };

    public void TwoPlayerFold(final Player winner){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long futuretime = System.currentTimeMillis() + 500;
                while (System.currentTimeMillis() < futuretime){}

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("name",winner.getPlayer_name());
                message.setData(bundle);
                winnerHandler.sendMessage(message);

                Blink(winner.getPlayer_name());

                futuretime = System.currentTimeMillis() + 1500;
                while (System.currentTimeMillis() < futuretime) {}

                endings.sendEmptyMessage(0);
            }
        };
        Thread endThread = new Thread(runnable);
        endThread.start();
    }

    /*public void showCPUCards(){
        if (cpu1.isPlaying) {
            cpu1_card1.setText(cpu1.hand.get(0).toString());
            cpu1_card2.setText(cpu1.hand.get(1).toString());
            cpu1_card3.setText(cpu1.hand.get(2).toString());
        }
        if (cpu2.isPlaying) {
            cpu2_card1.setText(cpu2.hand.get(0).toString());
            cpu2_card2.setText(cpu2.hand.get(1).toString());
            cpu2_card3.setText(cpu2.hand.get(2).toString());
        }
        if (cpu3.isPlaying) {
            cpu3_card1.setText(cpu3.hand.get(0).toString());
            cpu3_card2.setText(cpu3.hand.get(1).toString());
            cpu3_card3.setText(cpu3.hand.get(2).toString());
        }
        if (cpu4.isPlaying) {
            cpu4_card1.setText(cpu4.hand.get(0).toString());
            cpu4_card2.setText(cpu4.hand.get(1).toString());
            cpu4_card3.setText(cpu4.hand.get(2).toString());
        }
    }*/

    public void decideWinner() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Player winner = null;

                int maxRank=-1;
                for (int i=0;i<playerList.size();i++)
                    if (playerList.get(i).getRank() > maxRank)
                        maxRank = playerList.get(i).getRank();

                ArrayList<Player> finalist = new ArrayList<>();
                for (int i=0;i<playerList.size();i++)
                    if (playerList.get(i).getRank()==maxRank)
                        finalist.add(playerList.get(i));

                if (finalist.size()>1){
                    switch (maxRank){
                        case 2:
                            Collections.sort(finalist,new sortByPairCard());
                            winner = finalist.get(0);
                            break;
                        default:
                            Collections.sort(finalist,new sortByHighCard());
                            winner = finalist.get(0);
                    }
                }else {
                    winner = finalist.get(0);
                }

                long futuretime = System.currentTimeMillis() + 2000;
                while (System.currentTimeMillis() < futuretime){}

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("name",winner.getPlayer_name());
                message.setData(bundle);
                winnerHandler.sendMessage(message);

                Blink(winner.getPlayer_name());

                futuretime = System.currentTimeMillis() + 2000;
                while (System.currentTimeMillis() < futuretime) {}

                endings.sendEmptyMessage(0);
            }
        };

        Thread endThread = new Thread(runnable);
        endThread.start();
    }

    Handler winnerHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            switch (name){
                case "You": text_human_money.setText(String.valueOf(human.getPoints())); break;
                case "CPU1": text_money_cpu1.setText(String.valueOf(cpu1.getPoints())); break;
                case "CPU2": text_money_cpu2.setText(String.valueOf(cpu2.getPoints())); break;
                case "CPU3": text_money_cpu3.setText(String.valueOf(cpu3.getPoints())); break;
            }

            String message;
            if (name.equals("You"))
                message = "You won!";
            else
                message = name+" wins.";
        }
    };

    private class sortByHighCard implements Comparator<Player>{

        @Override
        public int compare(Player A, Player B) {
            if (B.hand.get(0).getCardNum() != A.hand.get(0).getCardNum())
                return B.hand.get(0).getCardNum()-A.hand.get(0).getCardNum();

            if (B.hand.get(1).getCardNum() != A.hand.get(1).getCardNum())
                return B.hand.get(1).getCardNum()-A.hand.get(1).getCardNum();

            if (B.hand.get(2).getCardNum() != A.hand.get(2).getCardNum())
                return B.hand.get(2).getCardNum()-A.hand.get(2).getCardNum();

            return 0;
        }
    }

    private class sortByPairCard implements Comparator<Player>{

        @Override
        public int compare(Player A, Player B) {
            if (B.hand.get(1).getCardNum() != A.hand.get(1).getCardNum())
                return B.hand.get(1).getCardNum()-A.hand.get(1).getCardNum();

            if (B.hand.get(0).getCardNum() != A.hand.get(0).getCardNum())
                return B.hand.get(0).getCardNum()-A.hand.get(0).getCardNum();

            if (B.hand.get(2).getCardNum() != A.hand.get(2).getCardNum())
                return B.hand.get(2).getCardNum()-A.hand.get(2).getCardNum();

            return 0;
        }
    }

    public void Blink(final String name){
        Runnable blinker = new Runnable() {
            @Override
            public void run() {
                for (int i=1;i<=11;i++){
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    bundle.putInt("signal",i%2);
                    message.setData(bundle);
                    OnOffHandler.sendMessage(message);

                    long waitTime = System.currentTimeMillis() + 250;
                    while (System.currentTimeMillis()<waitTime) {}
                }
            }
        };
        Thread sideThread = new Thread(blinker);
        sideThread.start();
    }

    Handler endings = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            points = 0;

            fn_bar.removeView(show);
            isShowEnabled = false;

            int noOfPlayer = 5;
            if (human.getPoints() <= 0) {
                Intent intent = new Intent(Hand.this, MainActivity.class);
                startActivity(intent);
            }
            if (cpu1.getPoints() <= 0) {
                cpu1.isOut = true;
                noOfPlayer--;
            }
            if (cpu2.getPoints() <= 0) {
                cpu2.isOut = true;
                noOfPlayer--;
            }
            if (cpu3.getPoints() <= 0) {
                cpu3.isOut = true;
                noOfPlayer--;
            }
            play.setVisibility(View.VISIBLE);
        }
    };

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
