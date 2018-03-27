package edu.temple.responsiveuicolorselection;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    String selected_color_passed_to_frag;

    public CanvasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_canvas, container, false);
        fl = v.findViewById(R.id.fragment_canvas);

        //get color from the main activity
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
           selected_color_passed_to_frag = bundle.getString("passColorToFrag", "White");

            v.setBackgroundColor(Color.parseColor(selected_color_passed_to_frag));
        }
        // inflate the layout
       return v;
    }


    public void changeBackgroundColor(String selectedColor){
        if(isAdded()) {
            fl.setBackgroundColor(Color.parseColor(selectedColor));
        }

    }
}