package edu.temple.modifiedcolorselection;

import android.app.Activity;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by John Hyland on 3/4/18
 * CIS 3515 Lab 6
 * Two fragment color selection
 */

public class MainActivity extends FragmentActivity
    implements PaletteFragment.OnColorSelectedListener{

    GridView myGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction().add(R.id.paletteFragment, new PaletteFragment());
        //FragmentTransaction transaction2 = fm.beginTransaction().add(R.id.canvasFragment, new CanvasFragment());
        transaction.commit();


    }

    @Override
    public void onColorSelected(String selectedColor) {
        //user has selected a color from PaletteFragment

        //get the canvas fragment from the MainActivity layout
        CanvasFragment cf = (CanvasFragment) getSupportFragmentManager().findFragmentById(R.id.canvasFragment);
        cf.changeBackgroundColor(selectedColor);

    }
}
