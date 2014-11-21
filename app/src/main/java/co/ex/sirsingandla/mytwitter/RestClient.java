package co.ex.sirsingandla.mytwitter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Sirsingandla on 11/19/2014.
 */
public class RestClient
{
    private ArrayList<NameValuePair> params;
    private ArrayList<NameValuePair> headers;
    private String url;
    private int responseCode;
    private String response;
    private String message;

    public RestClient(String url){
        this.url = url;
        this.params = new ArrayList<NameValuePair>();
        this.headers = new ArrayList<NameValuePair>();
    }

    public String getResponse()
    {
        return response;
    }

    public String getErrorMessage(){
        return message;
    }

    public int getResponseCode(){
        return responseCode;
    }

    public void AddParams(String name, String value)
    {
        params.add(new BasicNameValuePair(name, value));
    }

    public void AddHeader(String name, String value)
    {
        headers.add(new BasicNameValuePair(name, value));
    }

    public void Execute(RequestMethod method) throws Exception
    {
        switch(method)
        {
            case GET:
            {
                String combinedParams = "";

                if(!params.isEmpty())
                {
                    combinedParams +="?";
                    for(NameValuePair p : params)
                    {
                        String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
                        if(combinedParams.length()>1)
                        {
                            combinedParams += "&" +paramString;
                        }
                        else
                        {
                            combinedParams += paramString;
                        }
                    }
                }

                HttpGet request = new HttpGet(url + combinedParams);

                for(NameValuePair h:headers)
                {
                    request.addHeader(h.getName(), h.getValue());
                }

                ExecuteRequest(request, url);
                break;
            }
            case POST:
            {
                HttpPost request = new HttpPost(url);

                for(NameValuePair h:headers)
                {
                    request.addHeader(h.getName(), h.getValue());
                }
                ExecuteRequest(request, url);
                break;
            }
            case DELETE:
            {
                break;
            }
            case PUT:
            {
                break;
            }
        }
    }

    private void ExecuteRequest(HttpUriRequest request, String url)
    {
        HttpClient client = new DefaultHttpClient();

        HttpResponse httpResponse;

        try
        {
            httpResponse = client.execute(request);
            responseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();

            HttpEntity entity = httpResponse.getEntity();

            if(entity != null)
            {
                InputStream inputStream = entity.getContent();
                response = ConvertStreamToString(inputStream);
                inputStream.close();
            }
        }
        catch (ClientProtocolException e)
        {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        }
        catch(IOException e)
        {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        }

    }

    private String ConvertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;

        try
        {
            while((line = reader.readLine())!= null)
            {
                sb.append(line + "\n");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}



