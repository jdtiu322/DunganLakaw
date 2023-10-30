package com.example.dungan_lakaw.posts;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dungan_lakaw.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostCardHolder> {

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }

    private Context context;
    private ArrayList<Postcards> posts;
    private OnButtonClickListener buttonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    public PostAdapter(Context context, ArrayList<Postcards> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.posts_view, parent, false);
        return new PostCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCardHolder holder, int position) {
        Postcards post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostCardHolder extends RecyclerView.ViewHolder {
        private ImageView imgv;
        private ImageButton btnupvote;
        private TextView username, txtposts, upvotecount;

        public PostCardHolder(View postView) {
            super(postView);
            imgv = postView.findViewById(R.id.imgprofilepic);
            username = postView.findViewById(R.id.txtusername);
            txtposts = postView.findViewById(R.id.txtposts);
            upvotecount = postView.findViewById(R.id.txtnoofupvotes);
            btnupvote = postView.findViewById(R.id.btnupvote);

            btnupvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && buttonClickListener != null) {
                        buttonClickListener.onButtonClick(position);
                    }
                }
            });
        }

        public void bind(Postcards post) {
            imgv.setImageResource(R.drawable.samplepfp);
            username.setText(post.getUsername());
            txtposts.setText(post.getText());
            upvotecount.setText(String.valueOf(post.getUpvotes()));
        }
    }

    public static class ItemSpacingDecoration extends RecyclerView.ItemDecoration {
        private final int itemSpacing;

        public ItemSpacingDecoration(int itemSpacing) {
            this.itemSpacing = itemSpacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildAdapterPosition(view);
            int itemCount = state.getItemCount();

            // Add spacing to the bottom of all items except the last one
            if (position < itemCount - 1) {
                outRect.bottom = itemSpacing;
            }
        }
    }
}
