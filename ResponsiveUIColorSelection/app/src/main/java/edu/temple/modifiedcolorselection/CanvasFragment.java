package edu.temple.modifiedcolorselection;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

//import edu.temple.twoactivitycolorselection.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanvasFragment extends Fragment {

    View v;
    FrameLayout fl;

    public CanvasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       v = inflater.inflate(R.layout.fragment_canvas, container, false);
       fl = v.findViewById(R.id.fragment_canvas);

        // Inflate the layout for this fragment
       return v;
    }

    public void changeBackgroundColor(String selectedColor){
        fl.setBackgroundColor(Color.parseColor(selectedColor));
    }

}
