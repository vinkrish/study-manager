package com.app.studymanager.courseupdate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private int pages;
    private boolean revision;

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
                SharedPreferenceUtil.saveTargetDateSettings(this, false, "");
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
        date.setText(getTargetDate(course.getEndDate()));
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
    public void setDeleted() {
        presenter.onResume(credentials, courseId);
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.error_msg), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showAPIError(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_bar));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public Dialog displayUpdateDialog(final Book book) {
        final Dialog dialog = new Dialog(this, R.style.DialogFadeAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_course_update);

        (dialog.findViewById(R.id.delete_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                presenter.deleteBook(credentials, courseId, book.getId());
            }
        });

        //pages = book.getNoOfPagesRead();
        pages = 0;

        final EditText pagesRead = (EditText) dialog.findViewById(R.id.pages_et);
        pagesRead.addTextChangedListener(new PagesTextWatcher());
        ((TextView) dialog.findViewById(R.id.book_tv)).setText(book.getTitle());
        pagesRead.setText(String.format(Locale.ENGLISH, "%d", pages));
        ((TextView) dialog.findViewById(R.id.total_pages_tv))
                .setText(String.format("%s/%s", book.getNoOfPagesRead(), book.getNoOfPages()));

        ImageView decrement = (ImageView) dialog.findViewById(R.id.decrement);
        ImageView increment = (ImageView) dialog.findViewById(R.id.increment);

        final CheckBox revisionBox = (CheckBox) dialog.findViewById(R.id.checkbox_revision);

        LinearLayout revisionLayout = (LinearLayout) dialog.findViewById(R.id.revision_layout);

        if(book.isRevisionCompleted()) {
            (dialog.findViewById(R.id.completed_tv)).setVisibility(View.VISIBLE);
            /*pagesRead.setKeyListener(null);
            pagesRead.setEnabled(false);
            decrement.setClickable(false);
            increment.setClickable(false);*/
        } else if(book.getNoOfPages() == book.getNoOfPagesRead()){
            /*pagesRead.setKeyListener(null);
            pagesRead.setEnabled(false);
            decrement.setClickable(false);
            increment.setClickable(false);*/
            revisionLayout.setVisibility(View.VISIBLE);
        }

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pages>0){
                    pages -= 1;
                    pagesRead.setText(String.format(Locale.ENGLISH, "%d", pages));
                }
            }
        });

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pages += 1;
                pagesRead.setText(String.format(Locale.ENGLISH, "%d", pages));
            }
        });

        revisionBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    revision = true;
                }
            }
        });

        (dialog.findViewById(R.id.save_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setNoOfPagesRead(pages);
                book.setRevisionCompleted(revision);
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

    class PagesTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.toString().equals("")){
                pages = 0;
            } else {
                pages = Integer.parseInt(editable.toString());
            }
        }
    }

    private void getInitValues(){
        Course initCourse = SharedPreferenceUtil.getCourse(this);
        courseId = initCourse.getId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(credentials, courseId);
    }
}
