package co.ex.sirsingandla.mytwitter;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by Sirsingandla on 10/26/2014.
 */
public class Tweet
{

    public Tweet() {

    }

    public Tweet(int id, String title, String body, Date datetime){
        this.id = id;
        this.title = title;
        this.body = body;
        this.datetime = datetime;
    }

    public Tweet(String title, String body, Date datetime){
        this.title = title;
        this.body = body;
        this.datetime = datetime;
    }


    private String title;

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    private String body;

    public String getBody()
    {
        return this.body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    private int id;

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    private Date datetime;

    public Date getDate()
    {
        return datetime;
    }

    public void setDate(Date datetime)
    {
        this.datetime = datetime;
    }

}
