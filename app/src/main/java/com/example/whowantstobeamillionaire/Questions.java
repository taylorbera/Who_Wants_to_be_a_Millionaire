package com.example.whowantstobeamillionaire;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

public class Questions implements Parcelable{
    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNr;
    private String answerMoney;

    public Questions() {
    }

    public Questions(String question, String option1, String option2, String option3, String option4,
                    int answerNr, String answerMoney) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNr = answerNr;
        this.answerMoney = answerMoney;
    }

    protected Questions(Parcel in) {
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        answerNr = in.readInt();
        answerMoney = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeInt(answerNr);
        dest.writeString(answerMoney);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public String getQuestions() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public String getAnswerMoney(){return answerMoney;}

    public void setId(int id){
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option3 = option4;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public void setAnswerMoney(String answerMoney){ this.answerMoney = answerMoney; }
}