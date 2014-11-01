package co.ex.sirsingandla.mytwitter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sirsingandla on 10/26/2014.
 */
public class MyAdapter extends ArrayAdapter<Tweet>
{
    private LayoutInflater inflater;
    Activity activity;
    ArrayList<Tweet> mydata;

    public MyAdapter(Activity activity, ArrayList<Tweet> items)
    {
        super(activity, R.layout.tweet_lists, items);
        this.mydata = items;
        this.activity = activity;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inf = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inf.inflate(R.layout.tweet_lists, parent, false);

        TextView headerText = (TextView) rowView.findViewById(R.id.headertextTV);
        TextView detailText = (TextView) rowView.findViewById(R.id.bodytextTV);

        headerText.setText(mydata.get(position).getTitle());
        detailText.setText(mydata.get(position).getBody());

        return rowView;
    }

    public long getItemId(int i)
    {
        return i;
    }

//    @Override
//    public Object getItem(int i)
//    {
//        return null;
//    }

}
