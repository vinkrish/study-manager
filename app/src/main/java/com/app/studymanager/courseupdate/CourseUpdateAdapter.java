package com.app.studymanager.courseupdate;

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
import java.util.Locale;

/**
 * Created by Vinay on 03-11-2016.
 */

public class CourseUpdateAdapter extends RecyclerView.Adapter<CourseUpdateAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    private Context context;
    private List<Book> items;
    private final OnItemClickListener listener;

    public CourseUpdateAdapter(Context context, List<Book> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_update_item, parent, false);
        return new  ViewHolder(v);
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
        TextView bookName;
        TextView pages;
        ImageView bookImage;

        ViewHolder(View view) {
            super(view);
            this.bookName = (TextView) view.findViewById(R.id.book_name);
            this.pages = (TextView) view.findViewById(R.id.pages);
            this.bookImage = (ImageView) view.findViewById(R.id.book_image);
        }

        public void bind(final Book book, final OnItemClickListener listener) {

            bookName.setText(book.getTitle());
            pages.setText(String.format("%s / %s", book.getNoOfPagesRead(), book.getNoOfPages()));
            Picasso.with(context)
                    .load(book.getImageUrl())
                    .placeholder(R.drawable.bookcover)
                    .error(R.drawable.bookcover)
                    .into(bookImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });
        }
    }

}
