package com.example.whowantstobeamillionaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.whowantstobeamillionaire.QuizContract.*;
import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Millionarie.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + "TEXT," +
                QuestionsTable.COLUMN_ANSWER_NR + "INTEGER," +
                QuestionsTable.COLUMN_ANSWER_MONEY + "TEXT"+ ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillQuestionsTable() {
        Questions q1 = new Questions("Easy: A is correct",
                "A", "B", "C","D",1,"$0");
        insertQuestion(q1);

        Questions q2 = new Questions("Easy: B is correct",
                "A", "B", "C","D",4, "$100");
        insertQuestion(q2);
        Questions q3 = new Questions("Easy: C is correct",
                "A", "B", "C","D",3, "$250");
        insertQuestion(q3);
        Questions q4 = new Questions("Medium: A is correct",
                "A", "B", "C","D",3, "$1000");
        insertQuestion(q4);
        Questions q5 = new Questions("Medium: A is correct",
                "A", "B", "C","D",1, "$4000");
        insertQuestion(q5);
        Questions q6 = new Questions("Medium: B is correct",
                "A", "B", "C", "D",2,"$8000");
        insertQuestion(q6);

        Questions q7 = new Questions("Hard: B is correct",
                "A", "B", "C","D",3, "$64000");
        insertQuestion(q7);

        Questions q8= new Questions("Hard: C is correct",
                "A", "B", "C","D",1, "$250000");
        insertQuestion(q8);

        Questions q9 = new Questions("Hard: B is correct",
                "A", "B", "C","D",1, "$725000");
        insertQuestion(q9);

        Questions q10 = new Questions("Hard: A is correct",
                "A", "B", "C", "D",4,"$1000000");
        insertQuestion(q10);
    }

    private void insertQuestion(Questions question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestions());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_ANSWER_MONEY,question.getAnswerMoney());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Questions> getAllQuestions() {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                QuestionsTable._ID
                );

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setAnswerMoney(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_MONEY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
