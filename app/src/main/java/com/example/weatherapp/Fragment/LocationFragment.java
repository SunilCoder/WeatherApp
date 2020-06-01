
package com.example.weatherapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {
    private MaterialButton btn_currentLocation;
    private View view;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_location, container, false);
        initView();
        BottomSheetLocation bottomSheetLocation= new BottomSheetLocation();
        bottomSheetLocation.show(getFragmentManager(),"modal bootom sheet");
        return view;
    }

    private void initView() {
        btn_currentLocation = view.findViewById(R.id.mb_currentLocation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetLocation bottomSheetLocation= new BottomSheetLocation();
                bottomSheetLocation.show(getFragmentManager(),"modal bootom sheet");
            }
        });
    }
}
