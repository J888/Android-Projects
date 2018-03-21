package edu.temple.responsiveuicolorselection;

//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.GridView;

/**
 * Created by John Hyland on 3/4/18
 * CIS 3515 Lab 6
 * Two fragment color selection
 */

public class MainActivity extends FragmentActivity
    implements PaletteFragment.OnColorSelectedListener{

    GridView myGridView;
    Boolean twoPanes, displayingColorPortrait = false;
    FragmentTransaction transaction;
    String selected_color;
    private static final String COLOR_KEY = "theColor";
    PaletteFragment pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPanes = (   (findViewById(R.id.canvasFragment) != null)
                        && (findViewById(R.id.paletteFragment)!=null));

        // If we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {

            String savedColor = savedInstanceState.getString(COLOR_KEY);

            // Save the color again in case user does not change colors
            // but changes orientation
            selected_color = savedColor;

            if(twoPanes && (savedColor!=null) ){

                findViewById(R.id.canvasFragment).setBackgroundColor(Color.parseColor(savedColor));
            }

            return;
        }


        if(!twoPanes){

            FragmentManager fm = getSupportFragmentManager();
            pf = new PaletteFragment();
            transaction = fm.beginTransaction();
            transaction.add(R.id.fragContainer, pf);
            transaction.addToBackStack(null);
            transaction.commit();

        }

        else if(findViewById(R.id.paletteFragment)==null){
            //if not already created

            FragmentManager fm = getSupportFragmentManager();
            pf = new PaletteFragment();
            transaction = fm.beginTransaction().add(R.id.paletteFragment, pf);
            transaction.addToBackStack(null);
            transaction.commit();

        }



    }

    @Override
    public void onColorSelected(String selectedColor) {
        //user has selected a color from PaletteFragment

        selected_color = selectedColor; // save the color so it can be used by onSaveInstanceState


        if(twoPanes) {

            //get the canvas fragment from the MainActivity layout
            CanvasFragment cf = (CanvasFragment) getSupportFragmentManager().findFragmentById(R.id.canvasFragment);
            cf.changeBackgroundColor(selectedColor);

        } else {

            // Create fragment and give it an argument specifying the color it should show
            CanvasFragment newFragment = new CanvasFragment();
            Bundle args = new Bundle();
            args.putString(selectedColor, null);
            newFragment.setArguments(args);

            transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the paletteFragment view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragContainer, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
            newFragment.changeBackgroundColor(selectedColor);

        }

    }


    // Android calls this before it destroys the activity to change orientation
    // so this is where we save the color in the instance state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(COLOR_KEY, selected_color);

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}



