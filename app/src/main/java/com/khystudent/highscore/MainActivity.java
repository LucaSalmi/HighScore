package com.khystudent.highscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button pointButton;
    TextView highScore;
    TextView points;

    SharedPreferences sp;

    int pointsNumber;
    int highScoreNumber;
    int max = 10;
    int min = 1;
    int range = max - min + 1;

    String highScorePrint;
    String pointsPrint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("com.khystudent.highscore.MyPrefs", MODE_PRIVATE);
        highScoreNumber = sp.getInt("Highscore", 0);

        setIDs();
        setListeners();
        publishHighScore();


    }

    private void setListeners() {

        pointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRandom();

            }
        });

    }

    public void setIDs(){

        pointButton = findViewById(R.id.point_button);
        highScore = findViewById(R.id.text_highscore);
        points = findViewById(R.id.text_points);
    }

    public void saveHighScore(){

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Highscore", highScoreNumber);
        editor.apply();
    }

    public void publishScore(){

        pointsPrint = Integer.toString(pointsNumber);
        points.setText(pointsPrint);

    }

    public void publishHighScore(){

        highScorePrint = Integer.toString(highScoreNumber);
        highScore.setText(highScorePrint);
    }

    public void checkScore(){

        if (pointsNumber > highScoreNumber){

            highScoreNumber = pointsNumber;
            publishHighScore();
            saveHighScore();
        }
        publishScore();
    }

    private void createRandom() {

        int a  = (int)(Math.random()*range)+min;
        pointsNumber += a;
        String aPrint = Integer.toString(a);
        Toast.makeText(MainActivity.this, aPrint, Toast.LENGTH_SHORT).show();
        checkScore();

    }


}