package com.example.rupali.thalassaemiaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    Context context;
    ArrayList<NotificationContent> notificationContentArrayList;
    OnItemClickListener listener;

    public NotificationAdapter(Context context, ArrayList<NotificationContent> notificationContentArrayList,OnItemClickListener listener) {
        this.context = context;
        this.notificationContentArrayList = notificationContentArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.notification_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        NotificationContent notification=notificationContentArrayList.get(position);
        if(!notification.getTitle().isEmpty()) {
            holder.title.setText(notification.getTitle());
        }
        if(!notification.getMessage().isEmpty()) {
            holder.message.setText(notification.getMessage());
        }
        if (!notification.getImageUri().isEmpty()) {
            Picasso.get().load(notification.getImageUri()).resize(350, 230).into(holder.notiImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationContentArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView title;
        TextView message;
        ImageView notiImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            notiImage=itemView.findViewById(R.id.noti_image);
            title=itemView.findViewById(R.id.noti_title);
            message=itemView.findViewById(R.id.noti_message);
        }
    }
    interface OnItemClickListener{
        void onItemClick(int position);
    }
}
