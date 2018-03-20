package edu.temple.modifiedcolorselection;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

//import edu.temple.twoactivitycolorselection.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaletteFragment extends Fragment {

    OnColorSelectedListener mCallback;

    public interface OnColorSelectedListener {
        public void onColorSelected(String color);
    }

    public PaletteFragment() {
        // Required empty public constructor
    }

    View v;
    GridView myGridView;
    private String[] colors = {"Red", "Blue", "Green", "Yellow", "White", "Gray", "Purple", "Aqua", "Magenta", "Lime"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(savedInstanceState!=null){

        }

        v = inflater.inflate(R.layout.fragment_palette, container, false);

        String[] gridLabels = getResources().getStringArray(R.array.labels);

        customAdapter myColorAdapter = new customAdapter(this.getContext(), R.layout.grid_view_item, gridLabels, colors);

        myGridView = v.findViewById(R.id.myGridView);
        myGridView.setAdapter(myColorAdapter);


        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onColorSelected(colors[position]);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Make sure the parent activity implemented callback interface, throw exception
        // if it did not
        try {
            mCallback = (OnColorSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "must implement OnColorSelectedListener");
        }
    }
}
