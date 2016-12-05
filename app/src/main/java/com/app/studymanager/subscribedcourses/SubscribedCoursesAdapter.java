package com.app.studymanager.subscribedcourses;

import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.studymanager.R;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.util.AdapterCallback;
import com.app.studymanager.util.Circle;
import com.app.studymanager.util.CircleAnimation;
import com.app.studymanager.util.CourseStatus;
import com.app.studymanager.util.DateUtil;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.studymanager.R.id.pages;

/**
 * Created by Vinay on 27-10-2016.
 */

class SubscribedCoursesAdapter extends RecyclerView.Adapter<SubscribedCoursesAdapter.ViewHolder> {
    private Context context;
    private List<Course> items;
    private float mTitleSize, mSubTitleSize, titleSubtitleSpace;
    private AdapterCallback adapterCallback;
    private Toast myToast;

    SubscribedCoursesAdapter(Context context, List<Course> items, AdapterCallback adapterCallback) {
        this.context = context;
        this.items = items;
        mTitleSize = context.getResources().getDimensionPixelSize(R.dimen.mTitleSize);
        mSubTitleSize = context.getResources().getDimensionPixelSize(R.dimen.mSubTitleSize);
        titleSubtitleSpace = context.getResources().getDimensionPixelSize(R.dimen.title_subtitle_space);
        this.adapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribed_courses_item, parent, false);
        return new SubscribedCoursesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Course course = items.get(position);
        final Book book = course.getBookList().get(0);
        final int initialPagesRead = book.getNoOfPagesRead();

        holder.courseName.setText(course.getTitle());
        holder.status.setText(CourseStatus.getValue(course.getCurrentStatus()));
        holder.goal.setText(String.format(Locale.ENGLISH, "Daily Goal: %d Pages", course.getTodayGoal()));

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.pages.getText().toString()) > initialPagesRead){
                    int page = Integer.parseInt(holder.pages.getText().toString()) - 1;
                    //book.setNoOfPagesRead(page);
                    holder.pages.setText(String.format("%s", page));
                }
            }
        });

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = Integer.parseInt(holder.pages.getText().toString()) + 1;
                //book.setNoOfPagesRead(page);
                holder.pages.setText(String.format("%s", page));
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(initialPagesRead >= Integer.parseInt(holder.pages.getText().toString())){
                    showToast("Number of pages must be greater than pages that are already read");
                } else if(book.getNoOfPages() < Integer.parseInt(holder.pages.getText().toString())) {
                    showToast("Number of pages read cannot be greater than total number of pages");
                } else {
                    int updatedPages = Integer.parseInt(holder.pages.getText().toString()) - initialPagesRead;
                    book.setNoOfPagesRead(updatedPages);
                    adapterCallback.onSaveCallback(course.getId(), book);
                }
            }
        });

        if(book.isRevisionCompleted()) {
            holder.pagesLayout.setVisibility(View.GONE);
            holder.save.setVisibility(View.GONE);
            holder.completed.setVisibility(View.VISIBLE);
        } else if(book.getNoOfPagesRead() == book.getNoOfPages()) {
            holder.pagesLayout.setVisibility(View.GONE);
            holder.revisionLayout.setVisibility(View.VISIBLE);
        } else{
            holder.pages.setText(String.format("%s", book.getNoOfPagesRead()));
            holder.totalPages.setText(String.format("/ %s", book.getNoOfPages()));
        }

        if (course.getCurrentStatus().equals("BEHIND_SCHEDULE")) {
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.alert_red));
            holder.circle.setmTitleColor(ContextCompat.getColor(context, R.color.alert_red));
            holder.circle.setmSubtitleColor(ContextCompat.getColor(context, R.color.alert_red));
            holder.circle.setmStrokeColor(ContextCompat.getColor(context, R.color.alert_red));
            holder.behindLayout.setVisibility(View.VISIBLE);
        } else if(course.getCurrentStatus().equals("NOT_STARTED")) {
            holder.pages.setKeyListener(null);
            holder.pages.setEnabled(false);
            holder.decrement.setClickable(false);
            holder.increment.setClickable(false);
            holder.save.setEnabled(false);
            holder.notStartedLayout.setVisibility(View.VISIBLE);
            holder.start.setText(DateUtil.getDisplayTargetDate(course.getStartDate()));
        } else {
            holder.circle.setmStrokeColor(ContextCompat.getColor(context, R.color.light_green));
        }

        holder.circle.setmTitleSize(mTitleSize);
        holder.circle.setmSubtitleSize(mSubTitleSize);
        holder.circle.setmTitleSubtitleSpace(titleSubtitleSpace);

        int progress = course.getCompletionRate().setScale(0, RoundingMode.DOWN).intValueExact();
        int circularProgress = (int) ((progress / 100.00) * 360);
        holder.circle.setmTitleText(String.format(Locale.ENGLISH, "%s%%", Integer.toString(progress)));
        CircleAnimation animation = new CircleAnimation(holder.circle, circularProgress);
        animation.setDuration(1000);
        holder.circle.startAnimation(animation);

        Picasso.with(context)
                .load(book.getImageUrl())
                .placeholder(R.drawable.bookcover)
                .error(R.drawable.bookcover)
                .into(holder.bookImage);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onMoreCallback(course.getId(), course.getEndDate());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_name) TextView courseName;
        @BindView(R.id.status) TextView status;
        @BindView(R.id.circle) Circle circle;
        @BindView(R.id.goal) TextView goal;
        @BindView(R.id.more) Button more;
        @BindView(R.id.save) Button save;
        @BindView(R.id.pages_et) EditText pages;
        @BindView(R.id.total_pages_tv) TextView totalPages;
        @BindView(R.id.behind_layout) LinearLayout behindLayout;
        @BindView(R.id.catchup_tv) TextView catchup;
        @BindView(R.id.not_started_layout) LinearLayout notStartedLayout;
        @BindView(R.id.start_tv) TextView start;
        @BindView(R.id.book_image) ImageView bookImage;
        @BindView(R.id.increment) ImageView increment;
        @BindView(R.id.decrement) ImageView decrement;
        @BindView(R.id.pages_layout) LinearLayout pagesLayout;
        @BindView(R.id.revision_layout) LinearLayout revisionLayout;
        @BindView(R.id.completed_tv) TextView completed;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    private void showToast(String msg){
        if(myToast !=null){
            myToast.cancel();
        }
        myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        myToast.show();
    }

}
