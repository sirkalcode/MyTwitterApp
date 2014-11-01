package co.ex.sirsingandla.mytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MyTweetDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_detail);

        Intent intent = getIntent();
        TextView hText = (TextView)findViewById(R.id.headerTextDetail);
        String e1 = intent.getStringExtra("HeaderTextxx");
        hText.setText(e1);
        TextView dText = (TextView)findViewById(R.id.bodyDetail);
        String e2 = intent.getStringExtra("DetailTextyy");
        dText.setText(e2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_tweet_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
