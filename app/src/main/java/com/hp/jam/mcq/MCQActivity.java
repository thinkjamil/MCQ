package com.hp.jam.mcq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MCQActivity extends AppCompatActivity {
    Context context=this;
    Button optb1,optb2,optb3,optb4;
    TextView questionTv;
    Button nextBt;
    String question;
    String opt1,opt2,opt3,opt4;
    int correctOption, currentScore=0;
    MySQLiteAdapter mySQLiteAdapter;
    Cursor cursor;
    Boolean buttonActive=true;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);
        mySQLiteAdapter = new MySQLiteAdapter(context);
        questionTv=(TextView)findViewById(R.id.qestiontv);
        optb1=(Button)findViewById(R.id.opt1);
        optb2=(Button)findViewById(R.id.opt2);
        optb3=(Button)findViewById(R.id.opt3);
        optb4=(Button)findViewById(R.id.opt4);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean loadedData = sp.getBoolean("loadedData",false);
        if(!loadedData)
            populate();
        nextBt=(Button)findViewById(R.id.corect_opt);
        cursor = mySQLiteAdapter.displayAllRecords();
        displayAnItem();
    }
    public void displayAnItem(){
        if(cursor.moveToNext()){
            question = cursor.getString(0);
            questionTv.setText(question);

            opt1=cursor.getString(1);
            optb1.setText(opt1);
            optb1.getBackground().clearColorFilter();

            opt2=cursor.getString(2);
            optb2.setText(opt2);
            optb2.getBackground().clearColorFilter();

            opt3=cursor.getString(3);
            optb3.setText(opt3);
            optb3.getBackground().clearColorFilter();

            opt4=cursor.getString(4);
            optb4.setText(opt4);
            optb4.getBackground().clearColorFilter();

            correctOption = cursor.getInt(5);
            buttonActive=true;
        }else{
            int maxScore  = sp.getInt("MaxScore",0);
            if(currentScore>maxScore){
                SharedPreferences.Editor edt = sp.edit();
                edt.putInt("MaxScore",currentScore);
                edt.apply();
            }
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra("LastScore",currentScore);
            startActivity(mainIntent);
        }
    }
    public void optionClick(View v){
        if(!buttonActive)
            return;
        buttonActive=false;
        Button [] bt = {optb1,optb2,optb3,optb4};
        bt[correctOption-1].getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        switch (v.getId()){
            case R.id.opt1: {
                if (correctOption != 1)
                    optb1.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                else{
                    currentScore++;
                    optb1.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                }
                break;
            }
            case R.id.opt2:{
                if(correctOption!=2)
                    optb2.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                else {
                    currentScore++;
                    optb2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                }
                break;
            }
            case R.id.opt3:{
                if(correctOption!=3)
                    optb3.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                else {
                    currentScore++;
                    optb3.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                }
                break;
            }
            case R.id.opt4:{
                if(correctOption!=4)
                    optb4.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                else {
                    currentScore++;
                    optb4.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                }
                break;
            }
        }
    }
    public void nextClick(View v){
        displayAnItem();
    }
    public void populate(){
        String [] options1 = {"2","4","6","8"};
        mySQLiteAdapter.insertRecord("2+2*2",options1,3);
        String [] options2 = {"2","0","6","8"};
        mySQLiteAdapter.insertRecord("2+2*0",options2,1);
        SharedPreferences.Editor edt = sp.edit();
        edt.putBoolean("loadedData",true);
        edt.apply();
    }
}
