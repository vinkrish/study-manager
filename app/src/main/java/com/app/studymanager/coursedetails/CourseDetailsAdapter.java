package com.app.studymanager.coursedetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.courses.CoursesAdapter;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Vinay on 28-10-2016.
 */

public class CourseDetailsAdapter extends RecyclerView.Adapter<CourseDetailsAdapter.ViewHolder> {
    private Context context;
    private List<Book> items;

    public CourseDetailsAdapter(Context context, List<Book> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_details_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = items.get(position);
        holder.bookName.setText(book.getTitle());
        Picasso.with(context)
                .load(book.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        ImageView bookImage;

        ViewHolder(View view) {
            super(view);
            this.bookName = (TextView) view.findViewById(R.id.book_name);
            this.bookImage = (ImageView) view.findViewById(R.id.book_image);
        }
    }
}
