package com.app.studymanager.profile;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.Profile;
import com.app.studymanager.models.SubscribedCourses;
import com.app.studymanager.subscribedcourses.SubscribedCoursesAdapter;
import com.app.studymanager.util.EditTextWatcher;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileDetailFragment extends Fragment implements ProfileView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.name_et) EditText profileName;
    @BindView(R.id.name) TextInputLayout nameLayout;
    @BindView(R.id.email_tv) TextView emailId;
    @BindView(R.id.save_btn) Button updateProfile;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ProfilePresenter presenter;
    private Credentials credentials;
    private Toast myToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        credentials = SharedPreferenceUtil.getUserToken(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        ButterKnife.bind(this, view);

        profileName.addTextChangedListener(new EditTextWatcher(nameLayout));
        presenter = new ProfilePresenterImpl(this, new ProfileInteractorImpl());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(profileName.getText().toString().isEmpty()) {
                    nameLayout.setError("Please enter profile name");
                } else {
                    presenter.onUpdate(credentials, profileName.getText().toString());
                }
            }
        });
        return view;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError() {
        showToast("Failed to update profile");
    }

    @Override
    public void setSuccess() {
        showToast("Profile details updated");
    }

    @Override
    public void setProfile(Profile profile) {
        profileName.setText(profile.getName());
        emailId.setText(profile.getEmail());
    }

    @Override
    public void setSubscribedCourses(SubscribedCourses subscribedCourses) {
        recyclerView.setAdapter(new ProfileCoursesAdapter(getActivity(), subscribedCourses.getCourses()));
    }

    private void showToast(String msg){
        if(myToast != null){
            myToast.cancel();
        }
        myToast = Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT);
        myToast.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(credentials);
        presenter.onCoursesResume(credentials);
    }

}
