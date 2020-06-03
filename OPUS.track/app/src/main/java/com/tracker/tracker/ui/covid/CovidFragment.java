package com.tracker.tracker.ui.covid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tracker.tracker.R;


public class CovidFragment extends Fragment {

    private CovidViewModel covidViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        covidViewModel =
                ViewModelProviders.of(this).get(CovidViewModel.class);
        View root = inflater.inflate(R.layout.fragment_covid, container, false);

        Button whoBtn = root.findViewById(R.id.whoBtn);

        whoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWho();
            }
        });
        return root;
    }

    private void goToWho () {
        Uri uriUrl = Uri.parse("https://www.who.int/emergencies/diseases/novel-coronavirus-2019");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
