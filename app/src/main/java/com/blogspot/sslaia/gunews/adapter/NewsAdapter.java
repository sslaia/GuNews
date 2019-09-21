package com.blogspot.sslaia.gunews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.sslaia.gunews.R;
import com.blogspot.sslaia.gunews.model.web.NewsResult;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<NewsResult> mNewsItems;
    private Context mContext;
    private OnItemClickListener mListener;

    public NewsAdapter(Context context, ArrayList<NewsResult> newsItems) {
        mNewsItems = newsItems;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_list, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        NewsResult currentItem = mNewsItems.get(position);
        holder.mTitle.setText(currentItem.getWebTitle());
//        holder.mDate.setText(currentItem.getWebPublicationDate().substring(0, 10));
        // Convert the original date format to reading friendly dd-MM-yyyy
        try {
            // First convert the date string to date format
            String originalDate = currentItem.getWebPublicationDate();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date newDate = sdf1.parse(originalDate);
            // Then convert the date back to string format
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM yyyy");
            String date = sdf2.format(newDate);
            // Set the dateView
            holder.mDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String section = currentItem.getSectionName();
        if (section == null) {
            holder.mSection.setVisibility(View.GONE);
        } else {
            holder.mSection.setText(section);
        }

        try {
            String imageUrl = currentItem.getFields().getThumbnail();
            if (imageUrl == null || imageUrl.isEmpty()) {
                holder.mImage.setVisibility(View.GONE);
            } else {
                Glide.with(mContext)
                        .load(imageUrl)
                        .centerCrop()
                        .into(holder.mImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public void clear() {
        mNewsItems.clear();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDate;
        private TextView mSection;
        private ImageView mImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            mDate = itemView.findViewById(R.id.list_news_date);
            mSection = itemView.findViewById(R.id.list_news_section);
            mTitle = itemView.findViewById(R.id.list_news_title);
            mImage = itemView.findViewById(R.id.list_news_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
