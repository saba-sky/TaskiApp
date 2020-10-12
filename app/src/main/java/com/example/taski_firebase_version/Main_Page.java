package com.example.taski_firebase_version;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Page extends AppCompatActivity {
    Button red, green, yellow, blue,add;
    TextView titel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);
        red = findViewById(R.id.btnred);
        green = findViewById(R.id.btngreen);
        blue = findViewById(R.id.btnblue);
        yellow = findViewById(R.id.btnyellow);
        titel = findViewById(R.id.textView);
        add = findViewById(R.id.btnADDNew);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Page.this, Activity_New_Task.class);
                startActivity(intent);
            }
        });

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        titel.setTypeface(MLight);
        red.setTypeface(MMedium);
        green.setTypeface(MMedium);
        blue.setTypeface(MMedium);
        yellow.setTypeface(MMedium);

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Page.this, MainActivity.class);
                String green = "Do";
                intent.putExtra("teil_Key",green);
                startActivity(intent);
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Page.this, MainActivity.class);
                String blue = "Decide";
                intent.putExtra("teil_Key",blue);
                startActivity(intent);
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Page.this, MainActivity.class);
                String yellow = "Delegate";
                intent.putExtra("teil_Key",yellow);
                startActivity(intent);
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Page.this, MainActivity.class);
                String red = "Eliminate";
                intent.putExtra("teil_Key",red);
                startActivity(intent);
            }
        });
    }
}
