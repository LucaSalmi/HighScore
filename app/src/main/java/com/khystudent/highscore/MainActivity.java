package com.khystudent.highscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button pointButton;
    Button savePoints;
    Button loadPoints;
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
        File file = new File(MainActivity.this.getFilesDir(), "text");


        setIDs();
        setListeners(file);
        publishHighScore();


    }

    private void setListeners(File file) {

        pointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRandom();

            }
        });

        savePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveToText(file);

            }
        });

        loadPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFromText(file);

            }
        });

    }

    public void setIDs() {

        pointButton = findViewById(R.id.point_button);
        highScore = findViewById(R.id.text_highscore);
        points = findViewById(R.id.text_points);
        loadPoints = findViewById(R.id.load_btn);
        savePoints = findViewById(R.id.save_btn);
    }

    public void saveHighScore() {

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Highscore", highScoreNumber);
        editor.apply();
    }

    public void publishScore() {

        pointsPrint = Integer.toString(pointsNumber);
        points.setText(pointsPrint);

    }

    public void publishHighScore() {

        highScorePrint = Integer.toString(highScoreNumber);
        highScore.setText(highScorePrint);
    }

    public void checkScore() {

        if (pointsNumber > highScoreNumber) {

            highScoreNumber = pointsNumber;
            publishHighScore();
            saveHighScore();
        }
        publishScore();
    }

    private void createRandom() {

        int a = (int) (Math.random() * range) + min;
        pointsNumber += a;
        checkScore();

    }

    public void saveToText(File file) {


        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File textFile = new File(file, "saves.txt");
            PrintWriter writer = new PrintWriter(textFile);
            writer.write(points.getText().toString());
            writer.close();
            Toast.makeText(MainActivity.this, "Points saved", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void readFromText(File file){

        File readFile = new File(file, "saves.txt");
        try {
            Scanner sc = new Scanner(readFile);
            points.setText(sc.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "Points loaded", Toast.LENGTH_LONG).show();
        pointsNumber = Integer.parseInt(points.getText().toString());

    }

}