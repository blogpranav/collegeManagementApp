package nicapp.nic.bihar.nicapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import nicapp.nic.bihar.nicapp.Model.RegistrationModel;
import nicapp.nic.bihar.nicapp.database.DatabaseHelper;
import nicapp.nic.bihar.nicapp.database.OnlineDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name_L;
    EditText password_L;
    DatabaseHelper mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);*/

        Context context;
        mDatabase = new DatabaseHelper(MainActivity.this);

        //   prefs = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;
        try {
            mDatabase.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        mDatabase.openDataBase();

        /*getSupportActionBar().hide();*/
        TextView signup;
        Button b2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup = findViewById(R.id.tv1);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hello = new Intent(MainActivity.this, SignupPage.class);
                startActivity(hello);
            }
        });
        b2 = findViewById(R.id.button1);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_L = findViewById(R.id.useridlogin);
                password_L = findViewById(R.id.passwordlogin);
                boolean validate=false;
                validate=validate1();
                if(validate==true) {
                    String username = name_L.getText().toString();
                    String password = password_L.getText().toString();
                    String method ="login";
                    OnlineDatabase ob1 = new OnlineDatabase(MainActivity.this);
                    ob1.execute(method,username,password);
                    RegistrationModel pm = mDatabase.profilelogin(username, password);
                    if(pm==null) {
                        Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    else{
//                        SharedPreferences sp = getApplicationContext().getSharedPreferences("User_ID",MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putString("User_ID",username);
//                        editor.putString("Password",password);
//                        editor.commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("User_ID",username).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Password",password).commit();

                        Intent i = new Intent(MainActivity.this, HomeScreen.class);
                        startActivity(i);
                        finish();


                    }
                }
            }
        });
    }
    private boolean validate1() {
        boolean validname = true;
        String uname = name_L.getText().toString();
        String pass = password_L.getText().toString();
        if(uname.isEmpty()){
            name_L.setError("Please fill your User ID ");
            validname=false;
        }else{
            name_L.setError(null);

        }
        if(pass.isEmpty()){
            password_L.setError("Please Enter Your Password");
            validname=false;
        }else{
            password_L.setError(null);

        }
        return validname;
    }
}
