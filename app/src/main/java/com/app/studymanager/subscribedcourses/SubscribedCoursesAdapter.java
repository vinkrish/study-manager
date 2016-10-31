package com.app.studymanager.subscribedcourses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.coursedetails.CourseDetailsAdapter;
import com.app.studymanager.models.Course;
import com.app.studymanager.util.Circle;
import com.app.studymanager.util.CircleAnimation;

import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public class SubscribedCoursesAdapter extends RecyclerView.Adapter<SubscribedCoursesAdapter.ViewHolder> {
    private List<Course> items;
    private float mTitleSize;
    private float mSubTitleSize;
    private float titleSubtitleSpace;

    public SubscribedCoursesAdapter(Context context, List<Course> items){
        this.items = items;
        mTitleSize = context.getResources().getDimensionPixelSize(R.dimen.mTitleSize);
        mSubTitleSize = context.getResources().getDimensionPixelSize(R.dimen.mSubTitleSize);
        titleSubtitleSpace = context.getResources().getDimensionPixelSize(R.dimen.title_subtitle_space);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribed_courses_item, parent, false);
        return new SubscribedCoursesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = items.get(position);
        holder.courseName.setText(course.getDescription());
        holder.date.setText(course.getEndDate());
        holder.status.setText(course.getCurrentStatus());
        holder.circle.setmTitleSize(mTitleSize);
        holder.circle.setmSubtitleSize(mSubTitleSize);
        holder.circle.setmTitleSubtitleSpace(titleSubtitleSpace);
        CircleAnimation animation = new CircleAnimation(holder.circle, 180);
        animation.setDuration(1000);
        holder.circle.startAnimation(animation);
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

        public ViewHolder(View view) {
            super(view);
            this.courseName = (TextView) view.findViewById(R.id.course_name);
            this.date = (TextView) view.findViewById(R.id.date);
            this.status = (TextView) view.findViewById(R.id.status);
            this.circle = (Circle) view.findViewById(R.id.circle);
        }

    }
}
