package com.christiankula.boatstagram.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.models.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostsViewHolder> {

    private List<Post> data;

    public PostAdapter(List<Post> data) {
        this.data = data;
    }

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public PostAdapter.PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_post, parent, false);

        return new PostAdapter.PostsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostAdapter.PostsViewHolder holder, int position) {
        Post post = data.get(position);

        Picasso.with(holder.tvThumbnail.getContext())
                .load(post.getThumbnailSrc())
                .placeholder(R.drawable.ic_boat_blue_24dp)
                .into(holder.tvThumbnail);

        holder.tvCaption.setText(post.getCaption());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder {
        ImageView tvThumbnail;
        TextView tvCaption;

        PostsViewHolder(View itemView) {
            super(itemView);

            tvThumbnail = itemView.findViewById(R.id.iv_list_item_post_thumbnail);
            tvCaption = itemView.findViewById(R.id.tv_list_item_post_caption);
        }
    }
}
