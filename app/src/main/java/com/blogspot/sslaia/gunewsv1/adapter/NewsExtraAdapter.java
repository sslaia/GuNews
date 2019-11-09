package com.blogspot.sslaia.gunewsv1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.sslaia.gunewsv1.R;
import com.blogspot.sslaia.gunewsv1.databinding.NewsItemBinding;
import com.blogspot.sslaia.gunewsv1.helpers.AppUtils;
import com.blogspot.sslaia.gunewsv1.model.News;
import com.squareup.picasso.Picasso;

public class NewsExtraAdapter extends PagedListAdapter<News, RecyclerView.ViewHolder> {

    private Context context;

    public NewsExtraAdapter(Context context) {
        super(News.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            NewsItemBinding itemBinding = NewsItemBinding.inflate(layoutInflater, parent, false);
            NewsItemViewHolder viewHolder = new NewsItemViewHolder(itemBinding);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((NewsItemViewHolder) holder).bindTo(getItem(position));
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {

        private NewsItemBinding binding;

        public NewsItemViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(News news) {

            binding.newsImage.setVisibility(View.GONE);

            String byline = news.getFields().getAuthor() == null
                    || news.getFields().getAuthor().isEmpty() ? context.getString(R.string.not_available) : news.getFields().getAuthor();
            binding.newsDate.setText(String.format(context.getString(R.string.date_format),
                    AppUtils.getDate(news.getPubDate()),
                    AppUtils.getTime(news.getPubDate())));
            binding.newsAuthor.setText(byline);
            binding.newsTitle.setText(news.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newsUrl = news.getUrl();

                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    int guColor = Color.parseColor("#0b224d");
                    builder.setToolbarColor(guColor);
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(itemView.getContext(), Uri.parse(newsUrl));
                }
            });
        }
    }
}
