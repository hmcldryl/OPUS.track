package com.tracker.projectopus.ui.covid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tracker.projectopus.R;

public class CovidFragment extends Fragment {

    private CovidViewModel covidViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        covidViewModel =
                ViewModelProviders.of(this).get(CovidViewModel.class);
        View root = inflater.inflate(R.layout.fragment_covid, container, false);
        return root;
    }
}
