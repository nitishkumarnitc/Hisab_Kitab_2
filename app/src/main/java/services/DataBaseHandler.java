package services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sameershekhar on 29-Jun-16.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = " hisab_kitab ";
    private static final String TABLE_USERS = "  users ";
    private static final String TABLE_CONTACTS = " contacts ";
    private static final String TABLE_LEN_DEN = " lenden ";;

    private static final String KEY_ID = " user_id ";
    private static final String KEY_FNAME = " fname ";
    private static final String KEY_LNAME = " lname ";
    private static final String KEY_EMAIL = " email ";
    private static final String KEY_PASS = " password ";
    private static final String KEY_MOBILE = " mobile ";
    private static final String KEY_ID2 = " id2 ";
    private static final String KEY_FROM=" from ";
    private static final String KEY_TO=" to ";
    private static final String KEY_TRANSID=" transid ";
    private static final String KEY_DATE = " date ";
    private static final String KEY_AMOUNT=" amount ";
    private static final String KEY_DESC=" descp ";
    private static final String KEY_STATUS=" status ";
    private static final String KEY_SEX=" sex ";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("ONCREATE DBHandler", "before users table");
        String sql = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_FNAME + "TEXT ," + KEY_LNAME + "TEXT ," + KEY_SEX + " TEXT, "+ KEY_EMAIL + "TEXT," + KEY_PASS + "TEXT," + KEY_MOBILE + "TEXT, " + KEY_DATE +
                "TEXT " + ")";
        db.execSQL(sql);
        Log.d("ONCREATE", "after users table");

        sql="CREATE TABLE "+TABLE_CONTACTS+"("+ KEY_ID2 +"INTEGER PRIMARY KEY,"+ KEY_DATE +"TEXT"+")";
        db.execSQL(sql);

      /*  Log.d("ONCREATE", "after ontacts table");
         sql = "CREATE TABLE " + TABLE_LEN_DEN + "(" + KEY_TRANSID +" INTEGER PRIMARY KEY, "+ " from " + "INTEGER,"
                + KEY_TO + "INTEGER ," + KEY_AMOUNT + "REAL ," + KEY_DATE + "TEXT," + KEY_DESC + "TEXT,"
                 + KEY_STATUS + "INTEGER " + ")";
        db.execSQL(sql);
        Log.d("ONCREATE", "after lenden table");*/

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


   /* public void addLenDen(int transcid,int from,int to,float amount,String date,String descp,int status)
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
*/
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

 /* checking user is login or not*/

    public boolean isUserLogin(){
        boolean isPresent=false;
        String selectQuery="SELECT * FROM "+TABLE_USERS;
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            isPresent=true;
            return isPresent;
        }
        else return isPresent;
    }


    //Getting user Login status, return true if rows are present

    public int getRowCount(){
        String countQuery="SELECT * FROM"+TABLE_USERS;
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery(countQuery, null);
        int rowCount=cursor.getCount();
        return rowCount;
    }

    //Re Create the table, Delete the tables and create them again

    public void resetTables(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(TABLE_USERS,null,null);
        database.close();
    }



    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
