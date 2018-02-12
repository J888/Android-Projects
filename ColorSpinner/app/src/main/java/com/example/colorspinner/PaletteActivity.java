package com.example.colorspinner;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PaletteActivity extends AppCompatActivity {

    Spinner myColorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        myColorSpinner = findViewById(R.id.myColorSpinner);
        final LinearLayout myLayout = findViewById(R.id.myLayout);

        String[] colors = {"Red", "Blue", "Green", "Yellow", "White", "Purple", "Gray", "Cyan", "#E8AA14"};

        customAdapter myColorAdapter = new customAdapter(this, R.layout.spinner_item, colors);

        myColorSpinner.setAdapter(myColorAdapter);

        myColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String chosenColor = adapterView.getItemAtPosition(position).toString();

                myLayout.setBackgroundColor(Color.parseColor(chosenColor));
                Toast t = Toast.makeText(getApplicationContext(), "You have selected: " +chosenColor, Toast.LENGTH_SHORT);
                t.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
