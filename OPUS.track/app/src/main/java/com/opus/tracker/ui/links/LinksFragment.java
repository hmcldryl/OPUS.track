package com.opus.tracker.ui.links;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.opus.tracker.R;
import com.opus.tracker.ui.about.AboutViewModel;

public class LinksFragment extends Fragment {
    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_links, container, false);

        androidx.cardview.widget.CardView whoCardBtn = root.findViewById(R.id.whoCardBtn);
        androidx.cardview.widget.CardView dohCardBtn = root.findViewById(R.id.dohCardBtn);

        whoCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWho();
            }
        });


        dohCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDoh();
            }
        });

        return root;
    }

    private void goToWho () {
        Uri uriUrl = Uri.parse("https://www.who.int/emergencies/diseases/novel-coronavirus-2019");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void goToDoh () {
        Uri uriUrl = Uri.parse("https://www.doh.gov.ph/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
