package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.putraprima.skorbola.Model.Model;

public class MatchActivity extends AppCompatActivity {
    private TextView homeName;
    private TextView awayName;
    private TextView homeScore;
    private TextView awayScore;
    private TextView homeScorer;
    private TextView awayScorer;
    private ImageView homeLogo;
    private  ImageView awayLogo;
    private int home_score =0;
    private int away_score =0;
    private String winner;
    private Model model;
    private String scorer;
    public static final String DATA_KEY = "data";
    public static final String ADD_KEY = "add";
    private static final String TIME_KEY = "time";
    public static final int HOME_SCORER_REQUEST_CODE = 1;
    public static final int AWAY_SCORER_REQUEST_CODE = 2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        homeName = findViewById(R.id.txt_home);
        awayName = findViewById(R.id.txt_away);
        homeScore = findViewById(R.id.score_home);
        awayScore = findViewById(R.id.score_away);
        awayLogo = findViewById(R.id.away_logo);
        homeLogo = findViewById(R.id.home_logo);
        homeScorer = findViewById(R.id.txt_home_scorer);
        awayScorer = findViewById(R.id.txt_away_scorer);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            model = extras.getParcelable(MainActivity.USER_KEY);
            homeName.setText(model.getHomeName());
            awayName.setText(model.getAwayName());
            homeScore.setText(String.valueOf(model.getHomeScore()));
            awayScore.setText(String.valueOf(model.getAwayScore()));
            Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("homeLogo"), 0, getIntent().getByteArrayExtra("homeLogo").length);
            homeLogo.setImageBitmap(bmp);
            Bitmap bmpAway = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("awayLogo"), 0, getIntent().getByteArrayExtra("awayLogo").length);
            awayLogo.setImageBitmap(bmpAway);

        }

    }

    public void handleAddHomeScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, HOME_SCORER_REQUEST_CODE);
    }

    public void handleAddAwayScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, AWAY_SCORER_REQUEST_CODE);
    }

    public void handleCekHasil(View view) {

        Intent intent = new Intent(this, ResultActivity.class );

        intent.putExtra(ResultActivity.EXTRA_RESULT,winner);

        if(model.getHomeScore()>model.getAwayScore()){
            winner = model.getHomeName();
            model.setWinner(model.getHomeName());
            intent.putExtra(ResultActivity.EXTRA_RESULT,winner);
        }
        else if(model.getHomeScore()<model.getAwayScore()){
            winner = model.getAwayName();
            model.setWinner(model.getAwayName());
            intent.putExtra(ResultActivity.EXTRA_RESULT,winner);
        }
        else if(model.getHomeScore()==model.getAwayScore()){
            winner = "RESULT IS DRAW";
            intent.putExtra(ResultActivity.EXTRA_RESULT,winner);
        }

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) return;

        if(requestCode == HOME_SCORER_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                //tambah score
                model.addHomeScore(data.getStringExtra(ADD_KEY), data.getStringExtra(TIME_KEY));
                //print score
                homeScore.setText(String.valueOf(model.getHomeScore()));
                //print pemain
                homeScorer.setText(model.getHomeScorer().toString());
            }
        } else if (requestCode == AWAY_SCORER_REQUEST_CODE){
            model.addAwayScore(data.getStringExtra(ADD_KEY), data.getStringExtra(TIME_KEY));
            //print score
            awayScore.setText(String.valueOf(model.getAwayScore()));
            //print pemain
            awayScorer.setText(model.getAwayScorer().toString());


        }

    }

}
