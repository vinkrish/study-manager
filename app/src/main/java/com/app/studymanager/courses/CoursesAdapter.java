package com.app.studymanager.courses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vinay on 28-10-2016.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Course item);
    }

    private DecimalFormat df;
    private final List<Course> items;
    private final OnItemClickListener listener;

    CoursesAdapter(List<Course> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
        df = new DecimalFormat("#.#");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView time;
        ImageView subscribed;

        ViewHolder(View view) {
            super(view);
            this.courseName = (TextView) view.findViewById(R.id.course_name);
            this.time = (TextView) view.findViewById(R.id.time_tv);
            this.subscribed = (ImageView) view.findViewById(R.id.subscribed);
        }

        public void bind(final Course item, final OnItemClickListener listener) {
            courseName.setText(item.getTitle());
            if(item.getPreparationTimeInMonths() < 1) {
                time.setText(String.format(Locale.US, "%d weeks", item.getPreparationTimeInWeeks()));
            } else {
                time.setText(String.format(Locale.US, "%s months", df.format(item.getPreparationTimeInMonths())));
            }

            if(item.getSubscribed()) {
                subscribed.setVisibility(View.VISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }
}
