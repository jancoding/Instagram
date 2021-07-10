package com.example.instagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.models.Comment;
import com.example.instagram.adapters.CommentAdapter;
import com.example.instagram.models.Post;
import com.example.instagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private Post post;
    TextView tvUsername;
    TextView tvUsernameTwo;
    TextView tvTime;
    TextView tvDescription;
    ImageView ivHeart;
    ImageView ivImage;
    RecyclerView rvComments;
    CommentAdapter commentAdapter;
    List<Comment> comments;
    TextView tvNumLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        post = (Post) getIntent().getSerializableExtra("Post");
        tvUsername = findViewById(R.id.tvUsername);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        ivImage = findViewById(R.id.ivImage);
        ivHeart = findViewById(R.id.ivHeart);
        tvUsernameTwo = findViewById(R.id.tvUsernameTwo);
        rvComments = findViewById(R.id.rvComments);
        tvNumLikes = findViewById(R.id.tvNumLikes);
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, comments);
        rvComments.setAdapter(commentAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        ivHeart.setColorFilter(ContextCompat.getColor(this, R.color.medium_red), android.graphics.PorterDuff.Mode.SRC_IN);



        tvNumLikes.setText(post.getLikes() + "");
        tvUsername.setText(post.getUser().getUsername());
        tvTime.setText(Post.calculateTimeAgo(post.getCreatedAt()));
        tvDescription.setText(post.getDescription());
        tvUsernameTwo.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }
        if (hasLiked()) {
            ivHeart.setImageResource(R.drawable.ufi_heart_active);
        } else {
            ivHeart.setImageResource(R.drawable.ufi_heart);
        }

        getComments();
    }


    private boolean hasLiked() {
        Log.d("PostAdapter", "in has liked method" );
        JSONArray peopleLiked = post.getJSONArray("liked");
        for (int i = 0; i<peopleLiked.length(); i++) {
            try {
                Log.d("PostAdapter",peopleLiked.getString(i) );
                Log.d("PostAdapter", ParseUser.getCurrentUser().getObjectId().toString() );
                if (peopleLiked.getString(i).equals(ParseUser.getCurrentUser().getObjectId().toString())) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void getComments() {
        // Define the class we would like to query
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        // limit query to latest 20 items
        query.setLimit(30);
        query.whereEqualTo("post", post);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<Comment>() {
            public void done(List<Comment> itemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    commentAdapter.clear();
                    comments.addAll(itemList);
                    commentAdapter.notifyDataSetChanged();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }
}