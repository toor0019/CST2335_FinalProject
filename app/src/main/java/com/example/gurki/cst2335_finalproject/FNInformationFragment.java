package com.example.gurki.cst2335_finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class FNInformationFragment extends Fragment {


    public FNInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_fndescription,container,false);
        TextView textView=(TextView) view.findViewById(R.id.fnintroduction);
        Button mGoBackButton =(Button) view.findViewById(R.id.goBack);
        mGoBackButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(),FoodNutritionActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
