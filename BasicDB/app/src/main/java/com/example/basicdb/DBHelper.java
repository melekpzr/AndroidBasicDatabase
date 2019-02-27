package com.example.basicdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance = null;
    // Logcat tag
    private static final String LOG = "DBHelper";

    // Database Versiyonu
    private static final int DATABASE_VERSION = 1;

    // Database Adi
    private static final String DATABASE_NAME = "users";

    // Table Adlari
    private static final String TABLE_USER = "user";

    //User tablosunun sütunlari
    private static final String USER_ID = "id";
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "password";


    // Table Create Statements
    // User table
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME
            + " TEXT," + USER_PASSWORD + " TEXT)";

    /**
     * Bu siniftan sadece tek bir tane nesne olusmasini saglar.
     * Bu sayede memory leak meydana gelmez.
     *
     * @param context
     * @return DBHelper nesnesi
     */
    public static DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // create new tables
        onCreate(db);
    }

    /**
     * Veritabanini kapatir
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * Yeni kullanici eklemeyi saglar
     *
     * @param user eklenecek kullanici
     * @return eklenen kullanicinin id'si doner, hata durumunda -1 doner
     */
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getID());
        values.put(USER_NAME, user.getUserName());
        values.put(USER_PASSWORD, user.getPassword());

        // insert row
        return db.insert(TABLE_USER, null, values);

    }

    /**
     * id'ye gore kullanici getirir.
     *
     * @param userId belirtilen id degerine gore kullaniciyi getirir
     * @return user eger userId ile belirtilen id'ye sahip kullanici varsa donderir,
     * aksi taktirde null doner
     */
    public User getUser(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + userId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            User user = new User();
            user.setID(c.getInt(c.getColumnIndex(USER_ID)));
            user.setUserName((c.getString(c.getColumnIndex(USER_NAME))));
            user.setPassword(c.getString(c.getColumnIndex(USER_PASSWORD)));
            return user;

        } else {
            return null;
        }

    }

    /**
     * Tum kullanicilari getirir
     *
     * @return Kayitli kullanicilar
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setID(c.getInt(c.getColumnIndex(USER_ID)));
                user.setUserName((c.getString(c.getColumnIndex(USER_NAME))));
                user.setPassword(c.getString(c.getColumnIndex(USER_PASSWORD)));

                // adding to user list
                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }

    /**
     * Kullaniciyi gunceller
     *
     * @param user guncellenecek kullanici nesnesi
     * @return etkilenen satir sayisi
     */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getID());
        values.put(USER_NAME, user.getUserName());
        values.put(USER_PASSWORD, user.getPassword());

        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
    }

    /**
     * Kullaniciyi siler
     *
     * @param userId silinecek kullanici id'si
     */
    public void deleteUser(Integer userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, USER_ID + " = ?",
                new String[]{String.valueOf(userId)});
    }

    public ArrayAdapter<String> tumKayitlar(Context context)
    {
        String[] sutunlar = new String[] { USER_ID, USER_NAME, USER_PASSWORD};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_USER, sutunlar, null, null,null, null, null);

        int idSiraNo = c.getColumnIndex(USER_ID);
        int nameSiraNo = c.getColumnIndex(USER_NAME);
        int passSiraNo = c.getColumnIndex(USER_PASSWORD);
        String dizi[]=new String[c.getCount()];
        int sayac=0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            dizi[sayac]=c.getString(idSiraNo) + "    "  + c.getString(nameSiraNo) + "  " + c.getString(passSiraNo);
            sayac+=1;
        }

        ArrayAdapter AA= new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,dizi);
        return AA;
    }
}
