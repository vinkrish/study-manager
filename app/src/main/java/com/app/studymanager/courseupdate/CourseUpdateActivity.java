package com.app.studymanager.courseupdate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.coursesettings.CourseSettingsActivity;
import com.app.studymanager.custombook.CustomBookActivity;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.util.DividerItemDecoration;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CourseUpdateActivity extends AppCompatActivity implements CourseUpdateView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.date_tv) TextView date;
    @BindView(R.id.target_tv) TextView target;
    @BindView(R.id.progress_tv) TextView progress;

    private CourseUpdatePresenter presenter;
    private Credentials credentials;
    private long courseId;
    private Course course;
    private String endDate;
    private int pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        getInitValues();

        credentials = SharedPreferenceUtil.getUserToken(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        presenter = new CourseUpdatePresenterImpl(this, new CourseUpdateInteractorImpl());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourseUpdateActivity.this, CustomBookActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.course_settings:
                Intent intent = new Intent(this, CourseSettingsActivity.class);
                Bundle args = new Bundle();
                if(course != null){
                    args.putSerializable("course", course);
                }
                intent.putExtras(args);
                startActivity(intent);
                return true;
            case R.id.unsubscribe_settings:
                presenter.unsubscribeCourse(credentials, courseId);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private String getTargetDate(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
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
    public void setCourse(Course course) {
        this.course = course;
        title.setText(course.getTitle());
        date.setText(getTargetDate(endDate));
        target.setText(String.format("Read %s pages today to stay on track", course.getTodayGoal()));
        progress.setText(String.format("Today's progress"));
        recyclerView.setAdapter(new CourseUpdateAdapter(this, course.getBookList(), new CourseUpdateAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Book book) {
                displayUpdateDialog(book);
            }
        }));
    }

    @Override
    public void setUpdate() {
        presenter.onResume(credentials, courseId);
    }

    @Override
    public void whileUnscribed() {
        startActivity(new Intent(this, BottomBarActivity.class));
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.subscription_error), Snackbar.LENGTH_LONG)
                .show();
    }

    public Dialog displayUpdateDialog(final Book book) {
        final Dialog dialog = new Dialog(this, R.style.DialogFadeAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_course_update);

        final TextView pagesRead = (TextView) dialog.findViewById(R.id.pages_tv);
        pages = book.getNoOfPagesRead();

        ((TextView) dialog.findViewById(R.id.book_tv)).setText(book.getTitle());
        pagesRead.setText(String.format(Locale.ENGLISH, "%d", pages));
        ((TextView) dialog.findViewById(R.id.total_pages_tv)).setText(String.format("%s", book.getNoOfPages()));

        (dialog.findViewById(R.id.decrement)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pages>0){
                    pages -= 1;
                    pagesRead.setText(String.format(Locale.ENGLISH, "%d", pages));
                }
            }
        });

        (dialog.findViewById(R.id.increment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pages += 1;
                pagesRead.setText(String.format(Locale.ENGLISH, "%d", pages));
            }
        });

        (dialog.findViewById(R.id.save_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setNoOfPagesRead(pages);
                presenter.onUpdate(credentials, courseId, book);
                dialog.dismiss();
            }
        });
        dialog.show();

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }

    private void getInitValues(){
        Course initCourse = SharedPreferenceUtil.getCourse(this);
        courseId = initCourse.getId();
        endDate = initCourse.getEndDate();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(credentials, courseId);
    }
}
