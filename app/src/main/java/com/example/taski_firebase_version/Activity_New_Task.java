package com.example.taski_firebase_version;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.taski_firebase_version.R.id.adoingDate;

public class Activity_New_Task extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView titelPage_a2, addTitel, addDate, addDesc;
    EditText doingTitel, doingDate;
    Button btnSave, btnCancle;
    DatabaseReference reference;
    Integer count = (int) (Math.random() * (1000 - 10)) + 10;
    String teilKey;
    String key_count = Integer.toString(count);
    String key_DO;
    DatePickerDialog.OnDateSetListener date;
    Spinner doingDescSP;
    final Calendar myCalendar = Calendar.getInstance();

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        doingDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new__task);
        doingDescSP = findViewById(R.id.aDoDesc);
        doingDescSP.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teilKey = doingDescSP.getSelectedItem().toString();
        titelPage_a2 = findViewById(R.id.aPageTitle);
        //TextViews
        addTitel = findViewById(R.id.aAddTitle);
        addDate = findViewById(R.id.aDoDate);
        doingTitel=findViewById(R.id.aTitleDo);
        //EditTextdoingTitel = findViewById(R.id.titel_DO);
        //spinner
        doingDate = findViewById(adoingDate);

        //Button
        btnCancle = findViewById(R.id.aBtnDelete);

        btnSave = findViewById(R.id.aBtnSaveTask);


        //import Fonts
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        titelPage_a2.setTypeface(MMedium);

        addTitel.setTypeface(MLight);
//        doingTitel.setTypeface(MMedium);


        addDate.setTypeface(MLight);
        doingDate.setTypeface(MMedium);


        EditText edittext = findViewById(adoingDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Activity_New_Task.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSave.setTypeface(MMedium);
        btnCancle.setTypeface(MLight);
        reference = FirebaseDatabase.getInstance().getReference().child("Box").child("My_Does" + count);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                key_DO = doingDescSP.getSelectedItem().toString() + key_count;
                if (doingTitel.length() == 0) {
                    doingTitel.setError(
                            "Type Something!");
                } else {
//                    reference = FirebaseDatabase.getInstance().getReference().child("Box").child("My_Does" + count);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        //putData
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("titel_DO").setValue(doingTitel.getText().toString());
                            dataSnapshot.getRef().child("date_DO").setValue(doingDate.getText().toString());
                            dataSnapshot.getRef().child("desc_DO").setValue(doingDescSP.getSelectedItem().toString());
//                            dataSnapshot.getRef().child("key_DO").setValue(key_DO);
//                            dataSnapshot.getRef().child("key_DO").setValue(key_DO);
                            Intent a = new Intent(Activity_New_Task.this, Main_Page.class);
                            a.putExtra("key_DO", key_DO);
                            a.putExtra("key_DO", getIntent().getStringExtra("key_DO"));
                            startActivity(a);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }


            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}