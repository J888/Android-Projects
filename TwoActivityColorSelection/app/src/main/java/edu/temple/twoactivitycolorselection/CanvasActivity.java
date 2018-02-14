package edu.temple.twoactivitycolorselection;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Layout;
import android.widget.TextView;

/**
 * Created by John Hyland on 2/14/18.
 * CIS 3515 Lab 4
 */

public class CanvasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        String color = getIntent().getStringExtra("chosenColor");

        ConstraintLayout c = findViewById(R.id.myLayout);

        c.setBackgroundColor(Color.parseColor(color));

    }
}
