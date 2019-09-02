package nicapp.nic.bihar.nicapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import nicapp.nic.bihar.nicapp.Model.RegistrationModel;
import nicapp.nic.bihar.nicapp.Model.UpdateModel;
import nicapp.nic.bihar.nicapp.database.DatabaseHelper;

public class HomeScreen extends AppCompatActivity {
    CardView c1, c2, c3, c4, c5, c6;
    int ch=1;
    DatabaseHelper mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mDatabase = new DatabaseHelper(HomeScreen.this);
        /*DatabaseHelper mDatabase = null;
        try {
            mDatabase.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        mDatabase.openDataBase();

*/
        c1 = findViewById(R.id.card1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(HomeScreen.this, Profile.class);
                startActivity(i1);
            }

        });
        c2 = findViewById(R.id.card2);
        String username =PreferenceManager.getDefaultSharedPreferences(HomeScreen.this).getString("User_ID", "");
        ch = mDatabase.CheckInfo(username);
        c2.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                if(ch==0) {
                    Toast.makeText(HomeScreen.this, "Go To Edit Tab", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeScreen.this);
                    alertDialogBuilder.setMessage("You Can Only Update Your Profile once!" +
                            "Go to Edit Tab to make any changes");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
                else{
                    Intent i = new Intent(HomeScreen.this, Update.class);
                    startActivity(i);
                    finish();


                }


            }
        });
        c3 = findViewById(R.id.card3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch==0){
                Intent i3 = new Intent(HomeScreen.this, Edit.class);
                startActivity(i3);}
                else
                    Toast.makeText(HomeScreen.this, "Frist Enter Data in Update", Toast.LENGTH_SHORT).show();
            }
        });
        c6 = findViewById(R.id.card6);
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(HomeScreen.this, MainActivity.class);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("User_ID", "").commit();

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Password", "").commit();
                finish();
                startActivity(i1);
            }

        });
    }

}
