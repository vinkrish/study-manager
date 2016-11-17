package com.app.studymanager.customcourse;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studymanager.R;
import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.DatePickerFragment;
import com.app.studymanager.util.EditTextWatcher;
import com.app.studymanager.util.SharedPreferenceUtil;
import com.app.studymanager.util.StartDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomCourseActivity extends AppCompatActivity
        implements CustomCourseView, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.title_et) EditText title;
    @BindView(R.id.start_tv) TextView startDate;
    @BindView(R.id.title) TextInputLayout titleInputLayout;
    @BindView(R.id.title_book_et) EditText titleBook;
    @BindView(R.id.pages_et) EditText pages;
    @BindView(R.id.title_book) TextInputLayout titleBookLayout;
    @BindView(R.id.pages) TextInputLayout pagesInputLayout;

    private Credentials credentials;
    private CustomCoursePresenter presenter;
    private String startDateString;
    private Toast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        title.addTextChangedListener(new EditTextWatcher(titleInputLayout));
        startDate.setText(getStartDate());
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
            course.setType("CUSTOM");
            course.setCompletionRate(new java.math.BigDecimal(0));
            course.setTodayGoal(0);
            course.setSubscribed(true);
            course.setPreparationTimeInWeeks(0);
            course.setCurrentStatus("");
            course.setStartDate(startDateString);
            List<Book> books = new ArrayList<>();
            Book book = new Book();
            book.setTitle(titleBook.getText().toString());
            book.setNoOfPages(Integer.parseInt(pages.getText().toString()));
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
        } else if(titleBook.getText().toString().isEmpty()) {
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

    private String getStartDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        c.set(year, month, day);
        Date d = c.getTime();
        startDateString = defaultFormat.format(d);

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(year+"-"+month+"-"+day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }

    private String getCustomStartDate(String s){
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }

    public void changeDate(View view) {
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(startDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        StartDatePicker newFragment = new StartDatePicker();
        newFragment.setOnDateSetListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        bundle.putInt("day", day);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showToast(String msg){
        if(myToast != null){
            myToast.cancel();
        }
        myToast = Toast.makeText(this, msg,Toast.LENGTH_LONG);
        myToast.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        Date date = cal.getTime();

        if (GregorianCalendar.getInstance().get(Calendar.YEAR) > cal.get(Calendar.YEAR)) {
            Calendar c = Calendar.getInstance();
            date = c.getTime();
            showToast("Can't select date previous to current date");
        } else if (GregorianCalendar.getInstance().get(Calendar.MONTH) > cal.get(Calendar.MONTH) &&
                GregorianCalendar.getInstance().get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            Calendar c = Calendar.getInstance();
            date = c.getTime();
            showToast("Can't select date previous to current date");
        } else if (GregorianCalendar.getInstance().get(Calendar.DAY_OF_MONTH) > cal.get(Calendar.DAY_OF_MONTH) &&
                GregorianCalendar.getInstance().get(Calendar.MONTH) >= cal.get(Calendar.MONTH) &&
                GregorianCalendar.getInstance().get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            Calendar c = Calendar.getInstance();
            date = c.getTime();
            showToast("Can't select date previous to current date");
        }

        startDateString = dateFormat.format(date);
        startDate.setText(getCustomStartDate(startDateString));
    }

}
