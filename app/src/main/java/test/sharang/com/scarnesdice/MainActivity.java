package test.sharang.com.scarnesdice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView score,turn,p1tscore,p1fscore,p2tscore,p2fscore;
    Button hold,roll,reset;
    ImageView dice;

    int P1totalScore,P1turnScore,P2totalScore,P2turnScore;
    int randomNum;

    Boolean PlayerTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialize()
    {
        score=(TextView)findViewById(R.id.tvscore);
        turn=(TextView)findViewById(R.id.tvturn);
        p1tscore=(TextView)findViewById(R.id.tvp1tscore);
        p1fscore=(TextView)findViewById(R.id.tvp1fscore);
        p2tscore=(TextView)findViewById(R.id.tvp2tscore);
        p2fscore=(TextView)findViewById(R.id.tvp2fscore);
        hold=(Button)findViewById(R.id.bhold);
        reset=(Button)findViewById(R.id.breset);
        roll=(Button)findViewById(R.id.broll);
        dice=(ImageView)findViewById(R.id.diceimg);
        hold.setOnClickListener(this);
        reset.setOnClickListener(this);
        roll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.broll:
                roll();
                break;

            case R.id.bhold:
                hold();
                break;

            case R.id.breset:
                reset();
                break;
        }

    }

    private boolean gameEnded()
    {
        return (P1totalScore>=100||P2totalScore>=100);
    }
    private void finishGame()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        String winner;
        if(PlayerTurn) winner = "Player 1";
        else winner = "Player 2";
        reset();
        builder.setTitle("Game Ends!!").setMessage(winner+" wins!").setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
            }
        }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void roll()
    {
        Random rn = new Random();
        int range = 6 - 1 + 1;
        randomNum =  rn.nextInt(range) + 1;

        RotateAnimation anim = new RotateAnimation(0f,350f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(1);
        anim.setDuration(100);

        dice.startAnimation(anim);

        if(randomNum!=1)
        {
            if(randomNum==2) dice.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
            if(randomNum==3) dice.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
            if(randomNum==4) dice.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
            if(randomNum==5) dice.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
            if(randomNum==6) dice.setImageDrawable(getResources().getDrawable(R.drawable.dice6));

            if(PlayerTurn)
                P1turnScore+=10;
            else P2turnScore+=10;
            p1tscore.setText("Player 1 turn score :"+ P1turnScore);
            p2tscore.setText("Player 2 turn score :"+ P2turnScore);

            //if(gameEnded()) finishGame();

        }
        else {
            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice1));

            if (PlayerTurn) {
                P1totalScore = 0;
                P1turnScore = 0;
                p1tscore.setText("Player 1 turn score :" + P1turnScore);
                p1fscore.setText("Player 1 final score :" + P1totalScore);
            } else {
                P2totalScore = 0;
                P2turnScore = 0;
                p2tscore.setText("Player 2 turn score :" + P2turnScore);
                p2fscore.setText("Player 2 final score :" + P2totalScore);
            }

            PlayerTurn = !PlayerTurn;

            if (PlayerTurn) turn.setText("Player 1 turn");
            else turn.setText("Player 2 turn");
            score.setText("Player 1 score : " + P1totalScore + " Player 2 score : " + P2totalScore);
        }
    }

    private void hold()
    {
        if(PlayerTurn)
        {
            P1totalScore+=P1turnScore;
            p1fscore.setText("Player 1 final score :"+ P1totalScore);
            if(gameEnded()) finishGame();
        }
        else
        {
            P2totalScore+=P2turnScore;
            p2fscore.setText("Player 2 final score :" + P2totalScore);
            if(gameEnded()) finishGame();
        }
        score.setText("Player 1 score : "+P1totalScore+" Player 2 score : "+P2totalScore);
        PlayerTurn=!PlayerTurn;
        if(PlayerTurn)turn.setText("Player 1 turn");
        else turn.setText("Player 2 turn");

        P1turnScore=P2turnScore=0;
        p1tscore.setText("Player 1 turn score :"+ P1turnScore);
        p2tscore.setText("Player 2 turn score :"+ P2turnScore);
    }

    private void reset()
    {
        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
        P1totalScore=P1turnScore=P2turnScore=P2totalScore=0;
        score.setText("Player 1 score : "+P1totalScore+" Player 2 score : "+P2totalScore);
        p1tscore.setText("Player 1 turn score :"+ P1turnScore);
        p1fscore.setText("Player 1 final score :"+ P1totalScore);
        p2tscore.setText("Player 2 turn score :"+ P2turnScore);
        p2fscore.setText("Player 2 final score :"+ P2totalScore);
        PlayerTurn=true;
    }
}
