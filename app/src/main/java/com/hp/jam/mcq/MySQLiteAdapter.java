package com.hp.jam.mcq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jam on 15-06-2016.
 */
public class MySQLiteAdapter {
    Context context;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    public MySQLiteAdapter(Context context){
        this.context=context;
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }
    public long insertRecord(String question,String [] options, int correctOPtion){
        ContentValues cont = new ContentValues();
        cont.put(MySQLiteOpenHelper.QUESTION,question);
        cont.put(MySQLiteOpenHelper.OPTION1,options[0]);
        cont.put(MySQLiteOpenHelper.OPTION2,options[1]);
        cont.put(MySQLiteOpenHelper.OPTION3,options[2]);
        cont.put(MySQLiteOpenHelper.OPTION4,options[3]);
        cont.put(MySQLiteOpenHelper.CORRECTOPTION,correctOPtion);
        SQLiteDatabase dbS=mySQLiteOpenHelper.getWritableDatabase();
        long id = dbS.insert(MySQLiteOpenHelper.TABLE_NAME,null,cont);
        return id;
    }
    public Cursor displayAllRecords(){
        String [] columns={MySQLiteOpenHelper.QUESTION,
                MySQLiteOpenHelper.OPTION1,
                MySQLiteOpenHelper.OPTION2,
                MySQLiteOpenHelper.OPTION3,
                MySQLiteOpenHelper.OPTION4,
                MySQLiteOpenHelper.CORRECTOPTION,};
        SQLiteDatabase db= mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(MySQLiteOpenHelper.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }

    public class MySQLiteOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "questions.db";
        private static final String TABLE_NAME = "sub_questions";
        private static final int DATABASE_VERSION = 1;
        private static final String QID = "_id";
        private static final String QUESTION = "question";
        private static final String OPTION1 = "option1";
        private static final String OPTION2 = "option2";
        private static final String OPTION3 = "option3";
        private static final String OPTION4 = "option4";
        private static final String CORRECTOPTION = "corrrectoption";

        private static final String CREATE_TABLE = " create table " +TABLE_NAME +
                " ( " + QID + " integer primary key autoincrement , "+
                QUESTION + " varchar(255), " +
                OPTION1 + " varchar(255), "+
                OPTION2 + " varchar(255), "+
                OPTION3 + " varchar(255), "+
                OPTION4 + " varchar(255), "+
                CORRECTOPTION+" integer );";
        private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME + " ;";
        Context context;


        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Log.d("jam","Constructor called");

        }

        public void message(String msgStr) {
            Log.d("jam",msgStr);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                message("onCreate exec");
                db.execSQL(CREATE_TABLE);
            } catch (Exception sqlEx) {
                message("" + sqlEx);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                message("Onupgrade exec");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception sqle) {
                message("" + sqle);
            }
        }
    }
}
