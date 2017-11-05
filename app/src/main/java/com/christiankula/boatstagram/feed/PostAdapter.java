package com.christiankula.boatstagram.feed;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.post.details.PostDetailsActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> data;

    public PostAdapter(List<Post> data) {
        this.data = data;
    }

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_post, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        final Post post = data.get(position);

        Picasso.with(holder.tvThumbnail.getContext())
                .load(post.getThumbnailSrc())
                .placeholder(R.drawable.ic_boat_blue_24dp)
                .into(holder.tvThumbnail);

        holder.tvCaption.setText(post.getCaption());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostDetailsActivity.class);

                intent.putExtra(PostDetailsActivity.POST_EXTRA, Parcels.wrap(post));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView tvThumbnail;
        TextView tvCaption;

        PostViewHolder(View itemView) {
            super(itemView);

            tvThumbnail = itemView.findViewById(R.id.iv_list_item_post_thumbnail);
            tvCaption = itemView.findViewById(R.id.tv_list_item_post_caption);
        }
    }
}
