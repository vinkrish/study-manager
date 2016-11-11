package com.app.studymanager.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Credentials;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileDetailFragment extends Fragment {
    @BindView(R.id.name_et) EditText profileName;
    @BindView(R.id.email_tv) TextView emailId;

    private String name;
    private String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            email = getArguments().getString("email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        ButterKnife.bind(this, view);
        profileName.setText(name);
        emailId.setText(email);
        return view;
    }

}
