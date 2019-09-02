package nicapp.nic.bihar.nicapp.database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import nicapp.nic.bihar.nicapp.SignupPage;

import static android.widget.Toast.LENGTH_LONG;


public class OnlineDatabase extends AsyncTask {
    Context c1;
    public OnlineDatabase(Context c1){
        this.c1 = c1;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object[] objects) {
        String reg_url = "https://projectpegasus.000webhostapp.com/register.php";
        //String login_url = "https://projectpegasus.000webhostapp.com/login.php";
        String name, email, password, user, phone, method;
        method = (String) objects[0];
        if(method.equals("register"))
        {
            user = (String) objects[1];
            password = (String) objects[2];
            name = (String) objects[3];
            email = (String) objects[4];
            phone = (String) objects[5];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection h1 = (HttpURLConnection) url.openConnection();
                h1.setRequestMethod("POST");
                h1.setDoOutput(true);
                OutputStream OS = h1.getOutputStream();
                BufferedWriter b1 = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("User_ID","UTF-8") + "=" + URLEncoder.encode(user, "UTF-8") + " & " +
                        URLEncoder.encode("Password","UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + " & " +
                        URLEncoder.encode("Name","UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + " & " +
                        URLEncoder.encode("Email","UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + " & " +
                        URLEncoder.encode("Phone","UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                b1.write(data);
                b1.flush();
                b1.close();
                OS.close();
                InputStream i1 = h1.getInputStream();
                i1.close();
                return "Data Inserted online";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(String result)
    {
        Toast.makeText(c1,result,LENGTH_LONG).show();
    }
}
