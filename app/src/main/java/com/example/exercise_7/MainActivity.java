package com.example.exercise_7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView iconImage;
    private TextView livesTV;
    private TextView pointsTV;
    private Button startBtn;
    private Button answerFourBtn;
    private Button answerThreeBtn;
    private Button answerTwoBtn;
    private Button answerOneBtn;

    private int pointCount = 0;
    private int  livesCount = 5;
    private int seconds = 0;
    ArrayList<Question> questionList = new ArrayList<>();
    private Question selectedQuestion;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconImage = findViewById(R.id.imageIV);
        livesTV = findViewById(R.id.livesTV);
        pointsTV = findViewById(R.id.pointTV);
        startBtn = findViewById(R.id.startBtn);
        answerFourBtn = findViewById(R.id.answerFourBtn);
        answerThreeBtn = findViewById(R.id.answerThreeBtn);
        answerTwoBtn = findViewById(R.id.answerTwoBtn);
        answerOneBtn = findViewById(R.id.answerOneBtn);
        initiateArray();

        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBtn.setVisibility(View.GONE);
                displayImage();
            }
        });

        answerOneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        answerTwoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        answerThreeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        answerFourBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    private void initiateArray(){
        questionList.add(new Question(R.drawable.pumpkin, "Carrot",  "Apple",  "Banana", "Pumpkin",  "Pumpkin"));

        questionList.add(new Question(R.drawable.ghost,"Man", "Ghost",  "Dog",  "Bird",  "Ghost"));

        questionList.add(new Question(R.drawable.candy, "Cake", "Bread", "Candy", "Chocolate",  "Candy"));

        questionList.add(new Question(R.drawable.pot,"Pot", "Spoon", "Knife", "Plate", "Pot"));

        questionList.add(new Question(R.drawable.hat,"Neckless", "Baloon", "Hat", "Socks", "Hat"));

        questionList.add(new Question(R.drawable.broom, "Jug",  "Broom", "Bowl", "Plate", "Broom"));

        questionList.add(new Question(R.drawable.witch, "Witch",  "Man", "Cat", "Dog", "Witch"));

        questionList.add(new Question(R.drawable.spider, "Snake",  "Hat", "Dog", "Spider", "Spider"));

        questionList.add(new Question(R.drawable.cat,"Bat",  "Hat", "Cat", "Spider", "Cat"));

        questionList.add(new Question(R.drawable.bat,"Bat",  "Snake", "Dog", "Spider", "Bat"));
    }

    private void displayImage(){
        if (livesCount > 0 ){
            seconds = 0;
            handler.removeCallbacks(runnable);
            startTimer();
            selectedQuestion = questionList.get(new Random().nextInt(questionList.size()));
            iconImage.setImageResource(selectedQuestion.getImageID());
            answerOneBtn.setText(selectedQuestion.getAnswerOne());
            answerTwoBtn.setText(selectedQuestion.getAnswerTwo());
            answerThreeBtn.setText(selectedQuestion.getAnswerThree());
            answerFourBtn.setText(selectedQuestion.getAnswerFour());

        }else{
            gameOver();
        }
    }

    private boolean validateAnswer(String answer){
        if (answer.equals(selectedQuestion.getCorrectAnswer())) {
            return true;
        }else{
            return false;
        }
    }

    private void updateScoreAndLives( int score , int lives){
        livesTV.setText("Lives : " + String.valueOf(livesCount));
        pointsTV.setText("Points : " + String.valueOf(pointCount));
    }

    private void gameOver(){
        refreshData();
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.pumpkin)
                .setPositiveButton("Yes", (dialog, which) -> restart())
                .setTitle("Do you want to play again?")
                .setNegativeButton("No", (dialog, which) -> hideButton())
                .create()
                .show();
    }

    private void hideButton(){
        startBtn.setVisibility(View.GONE);
        answerOneBtn.setVisibility(View.GONE);
        answerTwoBtn.setVisibility(View.GONE);
        answerThreeBtn.setVisibility(View.GONE);
        answerFourBtn.setVisibility(View.GONE);
    }

    private void startTimer() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                timerAction();
            }
        }, delay);
    }

    private void timerAction(){
        if(seconds == 5) {
            if (livesCount == 0 ){
                gameOver();
            }else{
                livesCount -= 1;
                updateScoreAndLives(pointCount, livesCount);
                displayImage();
            }
        }else{
            seconds += 1;
        }
    }

    private void refreshData(){
        iconImage.setImageResource(R.drawable.question_mark);
        answerOneBtn.setText("???");
        answerTwoBtn.setText("???");
        answerThreeBtn.setText("???");
        answerFourBtn.setText("???");
        updateScoreAndLives(pointCount,livesCount);
        seconds = 0;
        handler.removeCallbacks(runnable);
    }

    private void restart(){
        startBtn.setVisibility(View.VISIBLE);
       livesCount = 5;
        pointCount = 0;
        updateScoreAndLives(pointCount, livesCount);
    }
}