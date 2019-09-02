package nicapp.nic.bihar.nicapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import nicapp.nic.bihar.nicapp.Model.RegistrationModel;
import nicapp.nic.bihar.nicapp.Model.UpdateModel;
import nicapp.nic.bihar.nicapp.database.DatabaseHelper;

public class Edit extends AppCompatActivity {
    TextView name,dob,email,phone,fname,mname,colname,colstate,colcity;
    String _name, _dob, _email, _phone, _fname, _mname, _colname, _colstate, _colcity;
    Button b1;

    DatabaseHelper m1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name= findViewById(R.id.name1);
        dob=findViewById(R.id.dob1);
        email=findViewById(R.id.email1);
        phone= findViewById(R.id.phone1);
        fname = findViewById(R.id.fname1);
        mname=findViewById(R.id.mname1);
        colname=findViewById(R.id.college1);
        colcity=findViewById(R.id.city1);
        colstate=findViewById(R.id.state1);
        b1 = findViewById(R.id.submit_edit);
        m1 = new DatabaseHelper(Edit.this);
        try {
            m1.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        m1.openDataBase();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues();
                RegistrationModel r1 = new RegistrationModel();
                UpdateModel u1 = new UpdateModel();
                r1.setName(_name);
                r1.setEmail(_email);
                r1.setPhone_no(_phone);
                u1.setfName(_fname);
                u1.setmName(_mname);
                u1.setColName(_colname);
                u1.setColCity(_colcity);
                u1.setColState(_colstate);
                u1.setDob(_dob);
                String userid = PreferenceManager.getDefaultSharedPreferences(Edit.this).getString("User_ID", "");
                long result1 = m1.EditInfoStudTab(Edit.this, r1, userid);
                long result2 = m1.EditInfoUpTab(Edit.this, u1, userid);
                if (result1 > 0 && result2 > 0) {
                    Toast.makeText(Edit.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(Edit.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void setValues(){
        _name = name.getText().toString().trim();
        _dob = dob.getText().toString().trim();
        _email = email.getText().toString().trim();
        _phone = phone.getText().toString().trim();
        _fname = fname.getText().toString().trim();
        _mname = mname.getText().toString().trim();
        _colname = colname.getText().toString().trim();
        _colstate = colstate.getText().toString().trim();
        _colcity = colcity.getText().toString().trim();


    }
}
