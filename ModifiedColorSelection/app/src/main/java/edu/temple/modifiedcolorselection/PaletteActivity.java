package edu.temple.modifiedcolorselection;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by John Hyland on 2/14/18.
 * CIS 3515 Lab 4
 */

public class PaletteActivity extends Activity {

    GridView myGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGridView = findViewById(R.id.myGridView);

        Resources res = getResources();
        String[] gridLabels = res.getStringArray(R.array.labels);

        //Color.parseColor needs the English colors
        String[] colors = {"Red", "Blue", "Green", "Yellow", "White", "Gray", "Purple", "Aqua", "Magenta", "Lime"};

        customAdapter myColorAdapter = new customAdapter(this, R.layout.grid_view_item, gridLabels, colors);

        myGridView.setAdapter(myColorAdapter);

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String chosenColor = adapterView.getItemAtPosition(position).toString();

                Intent launchIntent = new Intent(PaletteActivity.this, CanvasActivity.class);

                launchIntent.putExtra("chosenColor", chosenColor);

                startActivity(launchIntent);
            }
        });


    }
}
