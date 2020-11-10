package com.example.whowantstobeamillionaire;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameQuiz extends AppCompatActivity {
    private static final String KEY_QUESTION_LIST = "questionList";
    private static final String KEY_MONEY = "money";
    private static final String KEY_QUESTION_COUNT = "questionCounter";
    private static final String KEY_ANSWERED = "answered";

    private TextView textViewQuestion;
    private TextView textViewMoney;
    private TextView textViewQuestionCount;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirm;
    private Button buttonNext;
    private ColorStateList textColorDefaultRb;

    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Questions currentQuestion;

    private String money;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_quiz);

        textViewQuestion = findViewById(R.id.text_question);
        textViewMoney = findViewById(R.id.money);
        textViewQuestionCount = findViewById(R.id.question_count);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirm = findViewById(R.id.button_confirm);
        buttonNext = findViewById(R.id.button_next);
        textColorDefaultRb = rb1.getTextColors();

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
            questionList = dbHelper.getAllQuestions();
            questionCountTotal = questionList.size();
            showNextQuestion();

        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            money = savedInstanceState.getString(KEY_MONEY);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                    checkAnswer();
                } else {
                    Toast.makeText(GameQuiz.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionCounter < questionCountTotal) {
                    showNextQuestion();
                } else {
                    finishQuiz();
                }
            }
        });
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        currentQuestion = questionList.get(questionCounter);
        textViewQuestion.setText(currentQuestion.getQuestions());
        rb1.setText(currentQuestion.getOption1());
        rb2.setText(currentQuestion.getOption2());
        rb3.setText(currentQuestion.getOption3());
        rb4.setText(currentQuestion.getOption4());
        textViewMoney.setText(currentQuestion.getAnswerMoney());

        questionCounter++;
        textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
        answered = false;
        buttonConfirm.setText("Confirm");
    }

        private void checkAnswer(){
            answered = true;

            RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
            int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

            if (answerNr == currentQuestion.getAnswerNr()) {
                textViewMoney.setText("Money Earned: " + money);
                }showSolution();
        }

        private void showSolution() {
            rb1.setTextColor(Color.RED);
            rb2.setTextColor(Color.RED);
            rb3.setTextColor(Color.RED);
            rb4.setTextColor(Color.RED);

            switch (currentQuestion.getAnswerNr()) {
                case 1:
                    rb1.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 1 is correct");
                    break;
                case 2:
                    rb2.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 2 is correct");
                    break;
                case 3:
                    rb3.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 3 is correct");
                    break;
                case 4:
                    rb4.setTextColor(Color.GREEN);
                    textViewQuestion.setText("Answer 4 is correct");
                    break;
            }

            if (questionCounter < questionCountTotal) {
                buttonNext.setText("Next");
            } else {
                buttonNext.setText("Finish");
            }
        }



        @Override
        protected void onDestroy() {
            super.onDestroy();
            }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString(KEY_MONEY, money);
            outState.putInt(KEY_QUESTION_COUNT, questionCounter);
            outState.putBoolean(KEY_ANSWERED, answered);
            outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
            }

    private void finishQuiz() {
        Intent resultIntent = new Intent(GameQuiz.this, results.class);
        startActivity(resultIntent);
    }
}
