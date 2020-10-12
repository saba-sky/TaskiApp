package com.example.taski_firebase_version;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    TextView titlePage, subtitlePage, endPage;
    DatabaseReference referencekey;
    RecyclerView ourdoes;
    LinearLayout changingcolor;
    ArrayList<My_Does> list;
    DO_Adapter do_Adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        titlePage = findViewById(R.id.titlePage);
        subtitlePage = findViewById(R.id.subtitlePage);
        endPage = findViewById(R.id.endPage);
        changingcolor = findViewById(R.id.changingcolor);
        final String teil_key = getIntent().getStringExtra("teil_Key");


        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("STRING_I_NEED");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }


        buildRecyclerView(teil_key);


        swipeToDelete(ourdoes);
        if (teil_key != null) {
            onColorChange(teil_key);
        }


        //import Font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        //customize Fonts
        titlePage.setTypeface(MMedium);
        subtitlePage.setTypeface(MLight);
        endPage.setTypeface(MLight);

        titlePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main_Page.class);
                startActivity(intent);
            }
        });


    }

    public void onColorChange(String teil_Key) {
        switch (teil_Key) {
            case "Do":
                changingcolor.setBackgroundColor(getResources().getColor(R.color.colorgeen));
                subtitlePage.setText("done at the same day");
                break;
            case "Decide":
                changingcolor.setBackgroundColor(getResources().getColor(R.color.colorblue));
                subtitlePage.setText("should be scheduled");
                break;
            case "Eliminate":
                changingcolor.setBackgroundColor(getResources().getColor(R.color.colorred));
                subtitlePage.setText("don’t do at all");
                break;
            case "Delegate":
                changingcolor.setBackgroundColor(getResources().getColor(R.color.coloryellow));
                subtitlePage.setText("less important");
                break;
        }

    }

    public void buildRecyclerView(String TailKey) {


        ourdoes = findViewById(R.id.our_DOes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        referencekey = FirebaseDatabase.getInstance().getReference().child("Box");
        referencekey.orderByChild("desc_DO").equalTo(TailKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrive data and replace layout
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    My_Does p = dataSnapshot1.getValue(My_Does.class);
                    list.add(p);
                }
                do_Adapter = new DO_Adapter(MainActivity.this, list);
                ourdoes.setAdapter(do_Adapter);
                do_Adapter.notifyDataSetChanged();
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("List items are: " + list);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void swipeToDelete(RecyclerView ourdoes) {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                My_Does My_DoesToBeDelete = list.get(viewHolder.getAdapterPosition());
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(My_DoesToBeDelete.getKey_DO());
                String finalString = null;
                while (matcher.find()) {
                    finalString = matcher.group();
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Box").child("My_Does" + finalString);
                System.out.println("adapter poisiton is: " + viewHolder.getAdapterPosition());
                System.out.println("List is now: " + list);
                list.remove(viewHolder.getAdapterPosition());
                do_Adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorred))
                        .addSwipeLeftActionIcon(R.drawable.ic_trash_2)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(ourdoes);

    }


}
