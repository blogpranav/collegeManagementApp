package nicapp.nic.bihar.nicapp;

import android.content.Intent;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    TextView start1;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
*/        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        start1 = findViewById(R.id.main);
        Animation animob = AnimationUtils.loadAnimation(this, R.anim.alpha);
        start1.startAnimation(animob);
        String uname = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this).getString("User_ID", "");
        String pass = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this).getString("Password", "");

        if(uname!="" && pass!=""){
            i = new Intent(SplashScreen.this,HomeScreen.class);
        }
        else{
            i = new Intent(getApplicationContext(),MainActivity.class);
        }

        Thread t1 = new Thread() {
            public void run() {

                try {
                    sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        t1.start();


    }
}
