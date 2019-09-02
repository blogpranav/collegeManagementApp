package nicapp.nic.bihar.nicapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import nicapp.nic.bihar.nicapp.Edit;
import nicapp.nic.bihar.nicapp.Model.Branch;
import nicapp.nic.bihar.nicapp.Model.ImageModel;
import nicapp.nic.bihar.nicapp.Model.RegistrationModel;
import nicapp.nic.bihar.nicapp.Model.UpdateModel;
import nicapp.nic.bihar.nicapp.SignupPage;
import nicapp.nic.bihar.nicapp.Update;


public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mDatabase;
    // The Android's default system path of your application database.
    public static String DB_PATH = "";// "/data/data/nicapp.nic.bihar.nicapp/databases/";
    //private static String DB_NAME = "chatrawasInsp.db";
    // private static String DB_NAME = "AlertMSGDB";

    public static String DB_NAME = "myProject.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 3);
        Log.e("DataBaseHelper", "1");
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {

            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
            //CreateNewTables(db);

        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS
                            | SQLiteDatabase.OPEN_READWRITE);


        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist() {


        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {

        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


//
//        DataBaseHelper dh = new DataBaseHelper(myContext);
//        try {
//            dh.createDataBase();
//
//
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//            return null;
//        }

    public long addProduct(RegistrationModel product) {
        ContentValues content = new ContentValues();
        content.put("User_ID", product.getUserID());
        content.put("Password", product.getPassword());
        openDataBase();
        long returnValue = mDatabase.insert("LOGIN", null, content);
        close();
        return returnValue;
    }

    public long InsertNewEntry(SignupPage newEntryActivity, RegistrationModel result) {

        long c = -1;
        try {
            DatabaseHelper placeData = new DatabaseHelper(newEntryActivity);
            SQLiteDatabase db = placeData.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("Name", result.getName());
            values.put("Email_ID", result.getEmail());
            values.put("Password", result.getPassword());
            values.put("Phone_no", result.getPhone_no());
            values.put("User_ID", result.getUserID());


            c = db.insert("STUDENTINFO", null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }
    public long ImageInsert(Update m1, ImageModel m2, String User_ID) {
        DatabaseHelper Im1 = new DatabaseHelper(m1);
        SQLiteDatabase db1 = Im1.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("User_ID", User_ID);
        content.put("Image", m2.getImage());
        openDataBase();
        long returnValue = db1.insert("PROFILEPICTURE", null, content);
        close();
        return returnValue;
    }


    public long UpdateInfo(Update updateInfo, UpdateModel result, String User_ID){
        long p = -1;
        String uId = User_ID;
        try{
            DatabaseHelper updateData = new DatabaseHelper(updateInfo);
            SQLiteDatabase db1 = updateData.getWritableDatabase();
            ContentValues v = new ContentValues();

            v.put("Fname", result.getfName());
            v.put("Mname", result.getmName());
            v.put("Colname", result.getColName());
            v.put("Colcity", result.getColCity());
            v.put("Colstate", result.getColState());
            v.put("Branch", result.getBranch());
            v.put("User_ID", uId);
            v.put("DOB", result.getDob());

            p = db1.insert("UPDATEDINFO", null, v);
            db1.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return p;
        }

        return p;
    }

    public RegistrationModel profilelogin(String username, String password) {
        RegistrationModel pm = null;
        try {
            SQLiteDatabase mDatabase = this.getWritableDatabase();
            Cursor c = mDatabase.rawQuery("SELECT * FROM STUDENTINFO WHERE User_ID =? AND Password =? ", new String[]{username, password});
            if (c.moveToFirst()) {
                pm = new RegistrationModel();
                pm.setUserID(c.getString(0));
                pm.setPassword(c.getString(1));
            }


        } catch (Exception e) {
            pm = null;
        }
        return pm;
    }

    public int CheckInfo(String username) {
        String f_name = null ,m_name = null, col_name=null, col_city = null, col_state=null;
        int ch;
        try {
            SQLiteDatabase mDatabase = this.getWritableDatabase();
            Cursor c = mDatabase.rawQuery("SELECT * FROM UPDATEDINFO WHERE User_ID =?", new String[]{username});
            if (c.moveToFirst()) {
                f_name = c.getString(c.getColumnIndex("Fname")) == null ? null : c.getString(c.getColumnIndex("Fname"));
                m_name = c.getString(c.getColumnIndex("Mname")) == null ? null : c.getString(c.getColumnIndex("Mname"));
                col_name = c.getString(c.getColumnIndex("Colname")) == null ? null : c.getString(c.getColumnIndex("Colname"));
                col_city = c.getString(c.getColumnIndex("Colcity")) == null ? null : c.getString(c.getColumnIndex("Colcity"));
                col_state = c.getString(c.getColumnIndex("Colstate")) == null ? null : c.getString(c.getColumnIndex("Colstate"));
            }
            if (f_name != null && m_name != null && col_name != null && col_city != null && col_state != null)
                ch = 0;

            else
                ch = 1;
        }
            catch (Exception e) {
            ch = 0;
        }
        return ch;
    }

    public long EditInfoStudTab(Edit e1, RegistrationModel rm, String User_ID){
        long c = -1;
        String uId = User_ID;
        try{
            DatabaseHelper updateData = new DatabaseHelper(e1);
            SQLiteDatabase db1 = updateData.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("Name", rm.getName());
            v.put("Email_ID", rm.getEmail());
            v.put("Password", rm.getPassword());
            v.put("Phone_no", rm.getPhone_no());
            String[] whereArgs = {uId};
            c = db1.update("STUDENTINFO", v, "User_ID=?", whereArgs);
            db1.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return c;
        }
        return c;

    }
    public long EditInfoUpTab(Edit e1, UpdateModel ru, String User_ID){
        long p = -1;
        String uId = User_ID;
        try{
            DatabaseHelper updateData = new DatabaseHelper(e1);
            SQLiteDatabase db1 = updateData.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("Fname", ru.getfName());
            v.put("Mname", ru.getmName());
            v.put("Colname", ru.getColName());
            v.put("Colcity", ru.getColCity());
            v.put("Colstate", ru.getColState());
            v.put("Branch", ru.getBranch());
            v.put("DOB", ru.getDob());
            String[] whereArgs = {uId};

            p = db1.update("UPDATEDINFO", v, "User_ID=?", whereArgs);
            db1.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return p;
        }
        return p;

    }


    public ArrayList<Branch> getBranchList() {

        ArrayList<Branch> branch = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db.rawQuery("SELECT Id,BranchName FROM Branch ", null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Branch gen = new Branch();
                gen.setBranchCode(cur.getString(cur
                        .getColumnIndex("Id")));
                gen.setBranchName(cur.getString(cur
                        .getColumnIndex("BranchName")));

                branch.add(gen);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception

        }
        return branch;
    }


}