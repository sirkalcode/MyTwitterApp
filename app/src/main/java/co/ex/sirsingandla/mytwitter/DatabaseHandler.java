package co.ex.sirsingandla.mytwitter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sirsingandla on 11/13/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MyTwitter";

    private static final String TABLE_TWEETS = "Tweets";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE = "datetime";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TWEETS_TABLE = "CREATE TABLE " + TABLE_TWEETS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_BODY + " TEXT, " + KEY_DATE + " TEXT" + ")";

        db.execSQL(CREATE_TWEETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS );

        onCreate(db);
    }


    public void addTweet(Tweet tweet)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, tweet.getTitle());
        values.put(KEY_BODY, tweet.getBody());
        values.put(KEY_DATE, tweet.getDate().toString());

        db.insert(TABLE_TWEETS, null, values);
        db.close();
    }

    public Tweet getTweet(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TWEETS, new String[]{KEY_ID,KEY_TITLE, KEY_BODY, KEY_DATE},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        Tweet tweet = new Tweet(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), df.parse(cursor.getString(3)));

        return tweet;
    }

    public ArrayList<Tweet> getAllTweets() throws ParseException {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        String selectQuery = "SELECT * FROM " + TABLE_TWEETS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        if(cursor.moveToFirst()){
            do{
                Tweet tweet = new Tweet();
                tweet.setId(Integer.parseInt(cursor.getString(0)));
                tweet.setTitle(cursor.getString(1));
                tweet.setBody(cursor.getString(2));
                tweet.setDate(df.parse(cursor.getString(3)));
                tweets.add(tweet);
            }while(cursor.moveToNext());
        }

        return tweets;
    }

    public int getTweetCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_TWEETS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return  cursor.getCount();
    }

    public int updateTweet(Tweet tweet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, tweet.getTitle());
        values.put(KEY_BODY, tweet.getBody());
        values.put(KEY_DATE, tweet.getDate().toString());

        return  db.update(TABLE_TWEETS, values, KEY_ID + " =?",
                new String[]{String.valueOf(tweet.getId())});
    }

    public void deleteTweet(Tweet tweet)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_TWEETS, KEY_ID + " =?", new String[]{String.valueOf(tweet.getId())});
        db.close();
    }

}
