package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private String data;
    private TextView tvWinner;
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        data = intent.getStringExtra(EXTRA_RESULT);


        //bind view
        tvWinner = findViewById(R.id.textView3);
        tvWinner.setText(data);

    }
}
