package com.example.animals_plate.recyclerset;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animals_plate.Animal;
import com.example.animals_plate.Post_new;
import com.example.animals_plate.R;

import java.util.List;

public class recyclerBlog_adaper extends RecyclerView.Adapter<recyclerBlog_adaper.myViewHolders>{
    private List<Post_new> postList;

    public recyclerBlog_adaper(List<Post_new> postList) {
        this.postList = postList;
    }

    public void updateData(List<Post_new> posts) {
        this.postList = posts;
        notifyDataSetChanged();
    }
   /* public recyclerBlog_adaper(List<Post_new> postList){
        this.postList = postList;
    }*/
    public class myViewHolders extends RecyclerView.ViewHolder{
        private TextView blogRecyclerName,blogRecyclerCount,blogRecyclerTitle,blogRecyclerContent,blogRecyclerTime;
        private ImageView blogRecyclerImg,blogRecyclerEmo;

        public myViewHolders(@NonNull View itemView) {
            super(itemView);
            blogRecyclerImg = itemView.findViewById(R.id.blogRecyclerImg);
            blogRecyclerName = itemView.findViewById(R.id.blogRecyclerName);
            blogRecyclerEmo = itemView.findViewById(R.id.blogRecyclerEmo);
            blogRecyclerCount = itemView.findViewById(R.id.blogRecyclerCount);
            blogRecyclerTitle = itemView.findViewById(R.id.blogRecyclerTitle);
            blogRecyclerContent = itemView.findViewById(R.id.blogRecyclertxt);
            blogRecyclerTime = itemView.findViewById(R.id.blogRecyclerTime);
        }

    }


    @NonNull
    @Override
    public recyclerBlog_adaper.myViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerblog_item,parent,false);
        return new myViewHolders(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerBlog_adaper.myViewHolders holder, int position) {

        final int[] cnt = {0};
        int[] count_show={0,1};
        String auther_name = postList.get(position).getAuthor_name();
        String title = postList.get(position).getPost_title();
        String content = postList.get(position).getPost_content();
        String time = postList.get(position).getPosttime();
        holder.blogRecyclerName.setText(auther_name);
        holder.blogRecyclerTitle.setText(title);
        holder.blogRecyclerContent.setText(content);
        holder.blogRecyclerTime.setText(time);
        holder.blogRecyclerEmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cnt[0] %2==0){
                    holder.blogRecyclerEmo.setImageResource(R.drawable.ic_lovefull);
                    holder.blogRecyclerCount.setText(String.valueOf(count_show[1]));
                }else if(cnt[0] %2==1) {
                    holder.blogRecyclerEmo.setImageResource(R.drawable.ic_love);
                    holder.blogRecyclerCount.setText(String.valueOf(count_show[0]));
                }
                cnt[0]++;

            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
