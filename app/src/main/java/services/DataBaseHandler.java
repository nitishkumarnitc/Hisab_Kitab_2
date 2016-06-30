package services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by sameershekhar on 29-Jun-16.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hisab_kitab";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_LEN_DEN = "lenden";

    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_ID2 = "id2";
    private static final String KEY_FROM="from";
    private static final String KEY_TO="to";
    private static final String KEY_TRANSID="transid";
    private static final String KEY_DATE = "date";
    private static final String KEY_AMOUNT="amount";
    private static final String KEY_DESC="descp";
    private static final String KEY_STATUS="status";
    private static final String KEY_SEX="sex";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + "INTEGER PRIMARY KEY,"
                + KEY_FNAME + "TEXT ," + KEY_LNAME + "TEXT ," + KEY_EMAIL + "TEXT," + KEY_PASS + "TEXT," + KEY_MOBILE + "TEXT, " + KEY_DATE +
                "TEXT " + ")";
        db.execSQL(sql);
        Log.d("ONCREATE", "after users table");
        sql="CREATE TABLE "+TABLE_CONTACTS+"("+ KEY_ID2 +"INTEGER PRIMARY KEY,"+ KEY_DATE +"TEXT"+")";
        db.execSQL(sql);
        Log.d("ONCREATE", "after ontacts table");
         sql = "CREATE TABLE " + TABLE_LEN_DEN + "(" + KEY_TRANSID +"INTEGER PRIMARY KEY,"+ KEY_FROM + "INTEGER,"
                + KEY_TO + "INTEGER ," + KEY_AMOUNT + "REAL ," + KEY_DATE + "TEXT," + KEY_DESC + "TEXT," + KEY_STATUS + "INTEGER " + ")";
        db.execSQL(sql);
        Log.d("ONCREATE", "after lenden table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_LEN_DEN);
        onCreate(db);
    }

    public void insertUsers(int keyId,String fname,String lname,String email,String mobile,String sex,String date)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID,keyId);
        values.put(KEY_FNAME,fname);
        values.put(KEY_LNAME,lname);
        values.put(KEY_EMAIL,email);
        values.put(KEY_SEX,sex);
        values.put(KEY_MOBILE, mobile);
        values.put(KEY_DATE, date);
        database.insert(TABLE_USERS, null, values);
        database.close();

    }
    public void addContacts(int id,String date)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID2,id);
        values.put(KEY_DATE,date);
        database.insert(TABLE_CONTACTS,null,values);
        database.close();
    }
    public void addLenDen(int transcid,int from,int to,float amount,String date,String descp,int status)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_TRANSID,transcid);
        values.put(KEY_FROM,from);
        values.put(KEY_TO,to);
        values.put(KEY_AMOUNT,amount);
        values.put(KEY_DATE,date);
        values.put(KEY_DESC, descp);
        values.put(KEY_STATUS, status);
        database.insert(TABLE_LEN_DEN, null, values);
        database.close();

    }

    public HashMap<String,String> getUserdata() {
        HashMap<String,String> userMap=new HashMap<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS, null);
        cursor.moveToFirst();
        while(cursor.getCount()>0)
        {
            userMap.put("fname",cursor.getString(1));
            userMap.put("lname",cursor.getString(2));
            userMap.put("email",cursor.getString(3));
            userMap.put("sex",cursor.getString(4));
            userMap.put("mobile",cursor.getString(5));
            userMap.put("date",cursor.getString(6));

        }
        cursor.close();
        db.close();
        return userMap;
    }
}
