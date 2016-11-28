package com.app.studymanager.subscribedcourses;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.util.AdapterCallback;
import com.app.studymanager.util.Circle;
import com.app.studymanager.util.CircleAnimation;

import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vinay on 27-10-2016.
 */

public class SubscribedCoursesAdapter extends RecyclerView.Adapter<SubscribedCoursesAdapter.ViewHolder> {
    private Context context;
    private List<Course> items;
    private float mTitleSize, mSubTitleSize, titleSubtitleSpace;
    private AdapterCallback adapterCallback;

     SubscribedCoursesAdapter(Context context, List<Course> items, AdapterCallback adapterCallback){
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Course course = items.get(position);

        holder.courseName.setText(course.getTitle());
        holder.date.setText(getDate(course.getEndDate()));
        holder.status.setText(course.getCurrentStatus());
        if(course.getCurrentStatus().equals("BEHIND_SCHEDULE")) {
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.alert_red));
            holder.circle.setmTitleColor(ContextCompat.getColor(context, R.color.alert_red));
            holder.circle.setmSubtitleColor(ContextCompat.getColor(context, R.color.alert_red));
            holder.circle.setmStrokeColor(ContextCompat.getColor(context, R.color.alert_red));
        } else {
            holder.circle.setmStrokeColor(ContextCompat.getColor(context, R.color.light_green));
        }
        holder.circle.setmTitleSize(mTitleSize);
        holder.circle.setmSubtitleSize(mSubTitleSize);
        holder.circle.setmTitleSubtitleSpace(titleSubtitleSpace);

        int progress = course.getCompletionRate().setScale(0, RoundingMode.DOWN).intValueExact();
        int circularProgress = (int)((progress/100.00)*360);
        holder.circle.setmTitleText(String.format(Locale.ENGLISH, "%s%%", Integer.toString(progress)));
        CircleAnimation animation = new CircleAnimation(holder.circle, circularProgress);
        animation.setDuration(1000);
        holder.circle.startAnimation(animation);

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onMethodCallback(course.getId(), course.getEndDate());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         TextView courseName;
         TextView date;
         TextView status;
         Circle circle;
         Button update;

         ViewHolder(View view) {
            super(view);
            this.courseName = (TextView) view.findViewById(R.id.course_name);
            this.date = (TextView) view.findViewById(R.id.date);
            this.status = (TextView) view.findViewById(R.id.status);
            this.circle = (Circle) view.findViewById(R.id.circle);
            this.update = (Button) view.findViewById(R.id.update);
        }

    }

    private String getDate(String dateString) {
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
}
