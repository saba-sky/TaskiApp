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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Activity_Edite_Task extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView titelPage_a2, addTitel, addDate;
    EditText doingTitel, doingDate;
    Spinner doingDescSP;
    final Calendar myCalendar = Calendar.getInstance();
    Button btnSave, btnCancle;
    DatabaseReference reference;

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        doingDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edite__task);
        doingTitel = findViewById(R.id.eTitleDo);
        doingDate = findViewById(R.id.edoingDate);
        addTitel = findViewById(R.id.eAddTitle);
        addDate = findViewById(R.id.eDoDate);
        btnCancle = findViewById(R.id.eBtnDelete);
        btnSave = findViewById(R.id.eBtnSaveTask);
        doingDescSP = findViewById(R.id.eDoDesc);
        //getValue
        String spinnerString = null;
        doingTitel.setText(getIntent().getStringExtra("titel_DO"));
        spinnerString=getIntent().getStringExtra("desc_DO");
//        spinnerString = getIntent().getStringExtra("date_DO");

        Toast.makeText(getApplicationContext(),spinnerString,Toast.LENGTH_LONG).show();
        doingDate.setText(getIntent().getStringExtra("date_DO"));
        final String key_DO = getIntent().getStringExtra("key_DO");
        // TODO: 10-Feb-20
        doingDescSP.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerString != null) {
            int spinnerPosition = adapter.getPosition(spinnerString);
            doingDescSP.setSelection(spinnerPosition);
        }
        //import Fonts
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");
        doingTitel.setTypeface(MMedium);
        addTitel.setTypeface(MLight);


        EditText edittext = findViewById(R.id.edoingDate);
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
                new DatePickerDialog(Activity_Edite_Task.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnSave.setTypeface(MMedium);
        btnCancle.setTypeface(MLight);

        reference = FirebaseDatabase.getInstance().getReference().child("Box").child("My_Does" + key_DO);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    //putData
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titel_DO").setValue(doingTitel.getText().toString());
                        dataSnapshot.getRef().child("date_DO").setValue(doingDate.getText().toString());
                        dataSnapshot.getRef().child("desc_DO").setValue(doingDescSP.getSelectedItem().toString());
                        dataSnapshot.getRef().child("key_DO").setValue(key_DO);

                        Intent a = new Intent(Activity_Edite_Task.this, Main_Page.class);
                        startActivity(a);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
