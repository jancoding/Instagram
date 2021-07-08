package com.example.instagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.instagram.activities.DetailActivity;
import com.example.instagram.activities.MainActivity;
import com.example.instagram.fragments.CommentFragment;
import com.example.instagram.fragments.PostsFragment;
import com.example.instagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the movie at the position
        Post post = posts.get(position);
        // Bind the movie data into the view holder
        holder.bind(post, holder);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvUsername;
        TextView tvDescription;
        ImageView ivImage;
        TextView tvUsernameTwo;
        TextView tvNumLikes;
        ImageView ivHeart;
        ImageView ivComment;
        ImageView ivProfile;

        public ViewHolder(@NonNull View itemView) {

            // calling super ViewHolder constructor
            super(itemView);

            // assigning all elements to connect to view
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvUsernameTwo = itemView.findViewById(R.id.tvUsernameTwo);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            tvNumLikes = itemView.findViewById(R.id.tvNumLikes);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivProfile = itemView.findViewById(R.id.ivProfile);

            ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = posts.get(getAdapterPosition());
                    post.setLikes(post.getLikes() + 1);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            tvNumLikes.setText((Integer.parseInt(tvNumLikes.getText().toString()) + 1) + "");
                        }
                    });
                }
            });

            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", posts.get(getAdapterPosition()));
                    profileFragment.setArguments(bundle);
                    Log.d("PostsAdapter","set arguments of profile fragment");
                    fragmentTransaction.replace(R.id.rlMain, profileFragment);
                    fragmentTransaction.commit();

                }
            });

            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommentFragment();
                }
            });

            itemView.setOnClickListener(this);
        }

        private void showCommentFragment() {
            FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
            CommentFragment commentFragment = CommentFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable("post", posts.get(getAdapterPosition()));
            commentFragment.setArguments(bundle);
            commentFragment.show(fm, "fragment_edit_tweet");
        }

        public void bind(Post post, ViewHolder holder) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsernameTwo.setText(post.getUser().getUsername());
            tvNumLikes.setText(post.getLikes() + "");
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            ParseFile profile = post.getUser().getParseFile("profile");
            if (profile != null) {
                Glide.with(context).load(profile.getUrl()).into(ivProfile);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Post post = posts.get(position);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("Post", (Serializable) post);
            context.startActivity(intent);
        }
    }

}
