package com.app.studymanager.customcourse;

import android.content.Context;
import android.content.Intent;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.app.studymanager.R;
import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.EditTextWatcher;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomCourseActivity extends AppCompatActivity implements CustomCourseView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.title_et) EditText title;
    @BindView(R.id.description_et) EditText description;
    @BindView(R.id.title) TextInputLayout titleInputLayout;
    @BindView(R.id.description) TextInputLayout descriptionLayout;
    @BindView(R.id.title_book_et) EditText titleBook;
    @BindView(R.id.pages_et) EditText pages;
    @BindView(R.id.author_et) EditText author;
    @BindView(R.id.title_book) TextInputLayout titleBookLayout;
    @BindView(R.id.pages) TextInputLayout pagesInputLayout;

    private Credentials credentials;
    private CustomCoursePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        title.addTextChangedListener(new EditTextWatcher(titleInputLayout));
        description.addTextChangedListener(new EditTextWatcher(descriptionLayout));
        titleBook.addTextChangedListener(new EditTextWatcher(titleBookLayout));
        pages.addTextChangedListener(new EditTextWatcher(pagesInputLayout));

        credentials = SharedPreferenceUtil.getUserToken(this);

        presenter = new CustomCoursePresenterImpl(this, new CustomCourseInteractorImpl());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    public void setAdd() {
        startActivity(new Intent(this, BottomBarActivity.class));
    }

    public void createCourse(View view) {
        if(validate()) {
            Course course = new Course();
            course.setTitle(title.getText().toString());
            course.setDescription(description.getText().toString());
            course.setType("CUSTOM");
            course.setCompletionRate(new java.math.BigDecimal(0));
            course.setTodayGoal(0);
            course.setSubscribed(true);
            course.setPreparationTimeInWeeks(0);
            course.setCurrentStatus("");
            List<Book> books = new ArrayList<>();
            Book book = new Book();
            book.setTitle(titleBook.getText().toString());
            book.setNoOfPages(Integer.parseInt(pages.getText().toString()));
            book.setAuthor(author.getText().toString());
            book.setType("CUSTOM");
            books.add(book);
            course.setBookList(books);
            presenter.onAdd(credentials, course);
        }
    }

    private boolean validate() {
        if(title.getText().toString().isEmpty()){
            titleInputLayout.setError("Title cannot be empty");
            return false;
        } else if(description.getText().toString().isEmpty()) {
            descriptionLayout.setError("Description cannot be empty");
            return false;
        }else if(titleBook.getText().toString().isEmpty()) {
            titleBookLayout.setError("Title cannot be empty");
            return false;
        } else if(pages.getText().toString().isEmpty()) {
            pagesInputLayout.setError("Pages cannot be empty");
            return false;
        } else return true;
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.subscription_error), Snackbar.LENGTH_LONG)
                .show();
    }
}
