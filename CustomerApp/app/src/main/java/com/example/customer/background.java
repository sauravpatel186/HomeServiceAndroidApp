package com.example.customer;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class background extends AsyncTask<String,Void,String>
{



    @Override
    protected String doInBackground(String... voids) {
        String result="";
        String email=voids[0];
        String password=voids[1];
        String conn="https://on-stream-attribute.000webhostapp.com/home/login.php";
        try
        {
            URL url=new URL(conn);
            HttpURLConnection http=(HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream op=http.getOutputStream();
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(op,"UTF-8"));
            String data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            op.close();

            InputStream ip=http.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(ip,"ISO-8859-1"));
            String line="";
            while((line = reader.readLine() )!= null)
            {
                result += line;
            }

            Log.d("Result======",result.toString());

            reader.close();
            ip.close();
            http.disconnect();
            return  result;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            result=e.getMessage();
        }
        return null;
    }
}