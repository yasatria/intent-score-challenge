package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ScorerActivity extends AppCompatActivity {

    private EditText etScorer;
    private EditText etTime;
    public static final String ADD_SCORER_KEY = "add";
    public static final String ADD_TIME_KEY = "time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);

        //bind view
        etScorer= findViewById(R.id.scorerInput);
        etTime = findViewById(R.id.timeInput);
    }

    public void handleSubmitScorer(View view) {
        Intent intent = new Intent();
        intent.putExtra(ADD_SCORER_KEY, etScorer.getText().toString());
        intent.putExtra(ADD_TIME_KEY, etTime.getText().toString());
        setResult(RESULT_OK, intent);
        finish();

    }
}
