package nicapp.nic.bihar.nicapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import nicapp.nic.bihar.nicapp.database.DatabaseHelper;

public class Profile extends AppCompatActivity {
    TextView name,user_id,email,phone,fname,mname,colname,colstate,colcity,dob,branch;
    ImageView i1;
    DatabaseHelper m1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
     name= findViewById(R.id.name1);
     user_id=findViewById(R.id.user1);
    email=findViewById(R.id.email1);
    phone= findViewById(R.id.phone1);
    fname = findViewById(R.id.fname1);
    mname=findViewById(R.id.mname1);
    colname=findViewById(R.id.college1);
    colcity=findViewById(R.id.city1);
    colstate=findViewById(R.id.state1);
    dob = findViewById(R.id.dob1);
    branch = findViewById(R.id.branch1);
    i1 = findViewById(R.id.profilepic);
        m1 = new DatabaseHelper(Profile.this);

        try {
            m1.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        m1.openDataBase();
        ShowProfile();
        UpdateProfile();
        ProfilePic();
    }
    public void ProfilePic() {
        SQLiteDatabase dat = m1.getReadableDatabase();
        String userID = PreferenceManager.getDefaultSharedPreferences(Profile.this).getString("User_ID", "");
        Cursor c = dat.rawQuery("SELECT * FROM PROFILEPICTURE where User_ID=?", new String[]{userID});
        if (c.moveToNext()) {
            byte[] pic = c.getBlob(c.getColumnIndex("Image"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
            i1.setImageBitmap(bitmap);

        }
    }
    public void ShowProfile() {
        SQLiteDatabase dat = m1.getReadableDatabase();
        String userID = PreferenceManager.getDefaultSharedPreferences(Profile.this).getString("User_ID", "");
        Cursor c = dat.rawQuery("SELECT * FROM STUDENTINFO where User_ID=?", new String[]{userID});
        if (c.moveToNext()) {
            String name12 = c.getString(c.getColumnIndex("Name")) == null ? "" : c.getString(c.getColumnIndex("Name"));
            name.setText(name12);
            String phone12 = c.getString(c.getColumnIndex("Phone_no")) == null ? "" : c.getString(c.getColumnIndex("Phone_no"));
            phone.setText(phone12);
            String email12 = c.getString(c.getColumnIndex("Email_ID")) == null ? "" : c.getString(c.getColumnIndex("Email_ID"));
            email.setText(email12);
            String user12 = c.getString(c.getColumnIndex("User_ID")) == null ? "" : c.getString(c.getColumnIndex("User_ID"));
            user_id.setText(user12);
        }
    }

    public void UpdateProfile() {
        SQLiteDatabase dat = m1.getReadableDatabase();
        String userID = PreferenceManager.getDefaultSharedPreferences(Profile.this).getString("User_ID", "");
        Cursor c1 = dat.rawQuery("SELECT * FROM UPDATEDINFO where User_ID=?", new String[]{userID});
        if(c1.moveToNext()){
            String fname12 = c1.getString(c1.getColumnIndex("Fname")) == null ? "" : c1.getString(c1.getColumnIndex("Fname"));
            fname.setText(fname12);
            String mname12 = c1.getString(c1.getColumnIndex("Mname")) == null ? "" : c1.getString(c1.getColumnIndex("Mname"));
            mname.setText(mname12);
            String colname12 = c1.getString(c1.getColumnIndex("Colname")) == null ? "" : c1.getString(c1.getColumnIndex("Colname"));
            colname.setText(colname12);
            String colstate12 = c1.getString(c1.getColumnIndex("Colstate")) == null ? "" : c1.getString(c1.getColumnIndex("Colstate"));
            colstate.setText(colstate12);
            String colcity12 = c1.getString(c1.getColumnIndex("Colcity")) == null ? "" : c1.getString(c1.getColumnIndex("Colcity"));
            colcity.setText(colcity12);
            String dob12 = c1.getString(c1.getColumnIndex("DOB")) == null ? "" : c1.getString(c1.getColumnIndex("DOB"));
            dob.setText(dob12);
            String branch12 = c1.getString(c1.getColumnIndex("Branch")) == null ? "" : c1.getString(c1.getColumnIndex("Branch"));
            branch.setText(branch12);
        }
    }

    }


