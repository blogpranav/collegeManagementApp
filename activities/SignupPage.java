package nicapp.nic.bihar.nicapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nicapp.nic.bihar.nicapp.Model.RegistrationModel;
import nicapp.nic.bihar.nicapp.database.DatabaseHelper;
import nicapp.nic.bihar.nicapp.database.OnlineDatabase;



public class SignupPage extends AppCompatActivity {
    EditText name, email, password, user, phone, cPassword;
    String _name, _email, _password, _user, _phone, _cPassword;
    DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button b1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        mDatabase = new DatabaseHelper(this);
        b1 = findViewById(R.id.buttonSubmit);
        name = findViewById(R.id.nameS);
        user = findViewById(R.id.idS);
        email = findViewById(R.id.emailS);
        password = findViewById(R.id.passwordS);
        phone = findViewById(R.id.phoneS);
        cPassword= findViewById(R.id.password_confS);
        DatabaseHelper localDBHelper;
        Context context;
        localDBHelper = new DatabaseHelper(SignupPage.this);

//         prefs = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;
        try {
            localDBHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        localDBHelper.openDataBase();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = false;
                valid = Validate();
                if (valid == true) {
                    setValue();
                    //uploadOnline();
                    String method ="register";
                    OnlineDatabase ob1 = new OnlineDatabase(SignupPage.this);
                    ob1.execute(method,_user,_password,_name,_email,_phone);
                    RegistrationModel regmodel = new RegistrationModel();
                    regmodel.setName(_name);
                    regmodel.setEmail(_email);
                    regmodel.setPassword(_password);
                    regmodel.setPhone_no(_phone);
                    regmodel.setUserID(_user);
                    long result = mDatabase.InsertNewEntry(SignupPage.this, regmodel);
                    if (result > 0) {
                        Toast.makeText(SignupPage.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                        Thread t1 = new Thread() {
                            public void run() {

                                try {
                                    sleep(500);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    Intent m1 = new Intent(SignupPage.this, MainActivity.class);
                                    startActivity(m1);
                                    finish();
                                }
                            }
                        };
                        t1.start();


                    } else
                        Toast.makeText(SignupPage.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*File database = getApplicationContext().getDatabasePath(DatabaseHelper.DB_NAME);
        if(false == database.exists()){
           mDatabase.getReadableDatabase();
           if(copyDatabase(this)){
                Toast.makeText(this, "Database copied successfully", Toast.LENGTH_SHORT).show();
            }
           else{                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                return;
           }
       }*/

    }

    private void setValue() {
        _name = name.getText().toString().trim();
        _email = email.getText().toString().trim();
        _password = password.getText().toString().trim();
        _user = user.getText().toString().trim();
        _phone = phone.getText().toString().trim();

    }

    public boolean Validate() {
        _name = name.getText().toString().trim();
        _email = email.getText().toString().trim();
        _password = password.getText().toString().trim();
        _user = user.getText().toString().trim();
        _phone = phone.getText().toString().trim();
        _cPassword = cPassword.getText().toString().trim();
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        boolean validName = true;

        if (_name.isEmpty()) {
            name.setError("Please Fill you name");
            name.requestFocus();
            validName = false;
        } else {
            name.setError(null);

        }
        //Add email validation
        if (_password.isEmpty()) {
            password.setError("Please fill an appropiate password");
            password.requestFocus();
            validName = false;
        } else {
            password.setError(null);

        }

        String str_pass=password.getText().toString().trim();
        String str_conf_pass=cPassword.getText().toString().trim();

        if(!str_pass.equals(str_conf_pass)){
            cPassword.setError("Password does not match");
            validName = false;

        }
        else{
            cPassword.setError(null);
        }
        if (_phone.isEmpty() || _phone.length()!=10) {
            phone.setError("Please Fill your Phone no");
            phone.requestFocus();
            validName = false;
        } else {
            phone.setError(null);

        }
        if (_user.isEmpty()) {
            user.setError("Please Fill an appropiate user Id");
            user.requestFocus();
            validName = false;
        } else {
            user.setError(null);

        }
        return validName;
    }


    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DB_NAME);
            String outFileName = DatabaseHelper.DB_PATH + DatabaseHelper.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("signup", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void uploadOnline(){
       String method ="register";
       OnlineDatabase ob1 = new OnlineDatabase(this);
       ob1.execute(method,_user,_password,_name,_email,_phone);

    }
}

