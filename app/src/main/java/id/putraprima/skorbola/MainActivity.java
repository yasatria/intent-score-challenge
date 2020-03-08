package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import id.putraprima.skorbola.Model.Model;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private EditText homeName;
    @NotEmpty
    private EditText awayName;
    //@NotEmpty
    private ImageView homeLogo;
    //@NotEmpty
    private  ImageView awayLogo;
    Uri imageUri = null;

    Bitmap bitmapAway ; // store the image in your bitmap
    Bitmap bmpHome ;
    public static final String USER_KEY = "USER_KEY";
    private static final int REQUEST_CODE_HOME_LOGO = 1;
    private static final int REQUEST_CODE_AWAY_LOGO = 2;
    private static final String TAG = MatchActivity.class.getCanonicalName();
    Validator validator;
    Model model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //bind view
        homeName = findViewById(R.id.home_team);
        awayName = findViewById((R.id.away_team));
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        //validasi

        validator = new Validator(this);
        validator.setValidationListener(this);


        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity
    }

    public void handleEditHomeLogo(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_HOME_LOGO);
    }

    public void handleEditAwayLogo(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_AWAY_LOGO);
    }

    public void handleNext(View view) {
        validator.validate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED){
            return;
        }

        if(requestCode == REQUEST_CODE_HOME_LOGO){
            if(data!= null){
                try{
                    imageUri = data.getData();
                    bmpHome = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    homeLogo.setImageBitmap(bmpHome);
                }catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        else if(requestCode == REQUEST_CODE_AWAY_LOGO){
            if(data!= null){
                try{
                    imageUri = data.getData();
                    bitmapAway = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    awayLogo.setImageBitmap(bitmapAway);
                }catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }

        }
    }

    @Override
    public void onValidationSucceeded() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        bitmapAway.compress(Bitmap.CompressFormat.PNG, 50, baos);
        bmpHome.compress(Bitmap.CompressFormat.PNG, 50, baos2);

        model = new Model(
                homeName.getText().toString(),
                awayName.getText().toString());

        Intent intent = new Intent(this, MatchActivity.class );
        intent.putExtra(USER_KEY,model);

        intent.putExtra("homeLogo",baos.toByteArray());
        intent.putExtra("awayLogo",baos2.toByteArray());
        startActivity(intent);


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
