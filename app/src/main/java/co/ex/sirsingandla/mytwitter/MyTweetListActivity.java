package co.ex.sirsingandla.mytwitter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyTweetListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet_list);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //String[] myStrings = new String[]{"AAAAAAA", "BBBBBB", "CCCCCCCC", "DDDDDDD", "EEEEEEEEEEE", "FFFFFFFF"};

        //This is where I will fill out my tweet model from service

        DatabaseHandler db = new DatabaseHandler(this);

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for ( int i = 0; i < 20; i++ ) {
            Tweet tweet = new Tweet();
            tweet.setId(i*100);
            tweet.setTitle("A nice header for Tweet # " + i*100);
            tweet.setBody("Some random body text for the tweet # " + i*100);
            tweet.setDate(new Date());
            db.addTweet(tweet);
            //tweets.add(tweet);
        }

        //MyAdapter myAdapter = new MyAdapter(this, tweets);
        MyAdapter myAdapter  = null;
        try {
            myAdapter = new MyAdapter(this, db.getAllTweets());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setListAdapter(myAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int i, long id)
    {

        Tweet tweet = new Tweet();
        tweet.setTitle("A nice header for Tweet # ");
        tweet.setBody("Some random body text for the tweet # " );

        TextView ht = (TextView)v.findViewById(R.id.headertextTV);
        TextView dt = (TextView)v.findViewById(R.id.bodytextTV);

        TextView th = (TextView) v.findViewById(R.id.headertextTV);
        th.setText("Tweet Clicked");
        Intent intent = new Intent(MyTweetListActivity.this, MyTweetDetail.class);
        intent.putExtra("HeaderTextxx", ht.getText());
        intent.putExtra("DetailTextyy", dt.getText());
        intent.putExtra("k", tweet.getClass());
        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.my_tweet_list, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
