package com.app.studymanager.courseupdate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Vinay on 03-11-2016.
 */

public class CourseUpdateAdapter extends RecyclerView.Adapter<CourseUpdateAdapter.ViewHolder> {
    private Context context;
    private List<Book> items;

    public CourseUpdateAdapter(Context context, List<Book> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_update_item, parent, false);
        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = items.get(position);
        holder.bookName.setText(book.getTitle());
        holder.pages.setText(String.format("%s / %s", book.getNoOfPagesRead(), book.getNoOfPages()));
        Picasso.with(context)
                .load("http://vinkrish.info/bookcover.jpg")
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
        TextView pages;
        ImageView bookImage;

        ViewHolder(View view) {
            super(view);
            this.bookName = (TextView) view.findViewById(R.id.book_name);
            this.pages = (TextView) view.findViewById(R.id.pages);
            this.bookImage = (ImageView) view.findViewById(R.id.book_image);
        }
    }

}
