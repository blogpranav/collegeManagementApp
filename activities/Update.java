package nicapp.nic.bihar.nicapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Utilities.Utiilties;
import nicapp.nic.bihar.nicapp.Model.Branch;
import nicapp.nic.bihar.nicapp.Model.ImageModel;
import nicapp.nic.bihar.nicapp.Model.UpdateModel;
import nicapp.nic.bihar.nicapp.database.DatabaseHelper;
import nicapp.nic.bihar.nicapp.HomeScreen;

public class Update extends AppCompatActivity {
    final int SELECT_PICTURE = 2;
    Bitmap bitmap;
    String selectedImagePath;
    EditText fname, mname, colname, colcity, colstate, dob;
    Spinner spn_spinner;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    ArrayAdapter<String> branchadapter;
    ArrayList<Branch> BranchList = new ArrayList<>();
    String _fname, _mname, _colname, _colcity, _colstate, _dob;
    DatabaseHelper databaseObj;
    String _varBranch_ID = "", _varBranch_Name = "";
    String desig;
    Button picsub;
    Button getGallery;
    Button takepic;
    Button up;
    ImageView dobB, img_picture;
    private final static int CAMERA_PIC = 1;
    int ThumbnailSize = 145;
    byte[] imgData;
    String str_img = "N", str_lat = "", str_long = "", str_gpstime = "";
    byte[] imageData2;
    String str_imagcap;
    String isEdit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        databaseObj = new DatabaseHelper(Update.this);
        try {
            databaseObj.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        databaseObj.openDataBase();
        fname = findViewById(R.id.fNameS);
        mname = findViewById(R.id.mNameS);
        colname = findViewById(R.id.collegeS);
        colcity = findViewById(R.id.cityS);
        colstate = findViewById(R.id.stateS);
        spn_spinner = findViewById(R.id.semS);
        up = findViewById(R.id.updateSubmit);
        img_picture = findViewById(R.id.upPic);
        dob = findViewById(R.id.dobS);
        picsub = findViewById(R.id.updatePic);
        dobB = findViewById(R.id.buttonDOB);
        getGallery= findViewById(R.id.photoGallery);
        takepic= findViewById(R.id.takepic);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValues1();

                final UpdateModel m1 = new UpdateModel();
                m1.setfName(_fname);
                m1.setmName(_mname);
                m1.setColName(_colname);
                m1.setColCity(_colcity);
                m1.setColState(_colstate);
                m1.setDob(_dob);
                m1.setBranch(_varBranch_Name);
                String userid = PreferenceManager.getDefaultSharedPreferences(Update.this).getString("User_ID", "");
                long result = databaseObj.UpdateInfo(Update.this, m1, userid);
                if (result > 0) {
                    Toast.makeText(Update.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                    Thread t1 = new Thread() {
                        public void run() {

                            try {
                                sleep(500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                Intent m1 = new Intent(Update.this, HomeScreen.class);
                                startActivity(m1);
                                finish();
                            }
                        }
                    };
                    t1.start();


                } else
                    Toast.makeText(Update.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });
        dobB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        loadBranchSpinnerData();

//        spn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id) {
//                Branch desig = new Branch();
//                if (arg2 != 0) {
//                    desig = BranchList.get(arg2 - 1);
//                    _varBranch_ID = desig.get_branchCode();
//                    _varBranch_Name=desig.get_branchName();
//
//
//                }
//                else{
//                    ;
//                    _varBranch_ID="";                   //     BindListData();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //_varLandType="";
//            }
//        });


        spn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    Branch wrd = BranchList.get(arg2 - 1);
                    _varBranch_ID = wrd.getBranchCode();
                    _varBranch_Name = wrd.getBranchName();


                } else if (arg2 == 0) {
                    // spWard.setSelection(0);
                    // spVillage.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utiilties.isGPSEnabled(Update.this)) {
                    //str_img = "YES";

                    Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
                    iCamera.putExtra("KEY_PIC", "1");
                    startActivityForResult(iCamera, CAMERA_PIC);
                } else {

                   turnGPSOn();
                }

            }
        });
        getGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);

            }
        });
        picsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageModel m1 = new ImageModel();
                m1.setImage(bitmap);
                String userId = PreferenceManager.getDefaultSharedPreferences(Update.this).getString("User_ID", "");
                long result = databaseObj.ImageInsert(Update.this, m1, userId);
                if (result > 0) {
                    Toast.makeText(Update.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Update.this, "Photo Not Uploaded", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void setValues1() {
        _fname = fname.getText().toString().trim();
        _mname = mname.getText().toString().trim();
        _colname = colname.getText().toString().trim();
        _colcity = colcity.getText().toString().trim();
        _colstate = colstate.getText().toString().trim();
//        _sem =  sem.getText().toString().trim();
    }

    public void ShowDialog() {


        Calendar c = Calendar.getInstance();
        // Date min = new Date(2018, 4, 25);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(Update.this,
                mDateSetListener, mYear, mMonth, mDay);

        datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // datedialog.getDatePicker().setMinDate(min.getTime());
        datedialog.show();

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            String ds = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            ds = ds.replace("/", "-");
            String[] separated = ds.split(" ");
            Date min = new Date(2018, 4, 25);
            try {
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String newString = currentTimeString.replace("A.M.", "");

                String smDay = "" + mDay, smMonth = "" + (mMonth + 1);
                if (mDay < 10) {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10) {
                    smMonth = "0" + (mMonth + 1);
                }


                // et_mulayankandate.setText(new StringBui lder().append("0" + mYear).append("-")
                //  .append(mMonth + 1).append("-").append(mDay).append(" ").append(newString));
                dob.setText(smDay + "-" + smMonth + "-" + mYear);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                _dob = smDay + "/" + smMonth + "/" + mYear;

                //}
//                } else {
//                    //et_mulayankandate.setText(new StringBuilder().append(mYear).append("-")
//                    // .append(mMonth + 1).append("-").append(mDay).append(" ").append(newString));
//                    et_mulayankandate.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
//                    dateMul = mYear + "-" + (mMonth + 1) + "-" + mDay + " " + newString;
//                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void loadBranchSpinnerData() {

        BranchList = databaseObj.getBranchList();
        String[] divNameArray = new String[BranchList.size() + 1];
        divNameArray[0] = "-SELECT-";
        int i = 1;
        int setID = 0;
        for (Branch gen : BranchList) {
            divNameArray[i] = gen.getBranchName();
            if (desig == gen.getBranchCode()) {
                setID = i;
            }
            i++;

        }
        branchadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
        branchadapter.setDropDownViewResource(R.layout.dropdownlist);
        spn_spinner.setAdapter(branchadapter);

//        if (getIntent().hasExtra("KeyId")) {
//            for (int j = 0; j < BranchList.size(); j++) {
//
//                if (BranchList.get(j).get_branchCode().equalsIgnoreCase(_spinCategory)) {
//                    setID = j;
////                    spn_gender.setSelection(setID+1);
////                    spn_gender.setEnabled(false);
//                }
//                spn_category.setSelection(setID + 1);
////                spn_faculty.setEnabled(false);
//            }

        //spn_category.setSelection(((ArrayAdapter<String>) spn_category.getAdapter()).getPosition(_spinCategory));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    imgData = data.getByteArrayExtra("CapturedImage");
                    //imageData.add(imgData);

                    switch (data.getIntExtra("KEY_PIC", 1)) {
                        case 1:
                            bitmap = BitmapFactory.decodeByteArray(imgData, 0,
                                    imgData.length);
                            img_picture.setScaleType(ImageView.ScaleType.FIT_XY);
                            img_picture.setImageBitmap(Utiilties.GenerateThumbnail(bitmap,
                                    ThumbnailSize, ThumbnailSize));
                            str_img = "Y";
                            imageData2 = imgData;
                            str_lat = data.getStringExtra("Lat");
                            str_long = data.getStringExtra("Lng");
                            str_gpstime = data.getStringExtra("GPSTime");
                            str_img = "Y";
                            str_imagcap = org.kobjects.base64.Base64.encode(imgData);
                            break;



                    }

                }

            default:
                if (requestCode == SELECT_PICTURE) {
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = getPath(selectedImageUri);
                    System.out.println("Image Path : " + selectedImagePath);
                    img_picture.setImageURI(selectedImageUri);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }}

    private void turnGPSOn() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("GPS is off\nDo you want to turn on GPS..")
//		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);
//                System.out.println("Image Path : " + selectedImagePath);
//                img_picture.setImageURI(selectedImageUri);
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
