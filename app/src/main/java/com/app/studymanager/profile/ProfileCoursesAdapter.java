package com.app.studymanager.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import java.util.List;

/**
 * Created by Vinay on 15-11-2016.
 */

public class ProfileCoursesAdapter extends RecyclerView.Adapter<ProfileCoursesAdapter.ViewHolder>{
    private List<Course> items;

    public ProfileCoursesAdapter(Context context, List<Course> items){
        this.items = items;
    }

    @Override
    public ProfileCoursesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribed_course_item, parent, false);
        return new ProfileCoursesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileCoursesAdapter.ViewHolder holder, int position) {
        final Course course = items.get(position);
        holder.courseName.setText(course.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;

        public ViewHolder(View view) {
            super(view);
            this.courseName = (TextView) view.findViewById(R.id.course_name);
        }

    }
}
