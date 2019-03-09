package com.cloudrider.semicolon;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>{

    ArrayList notificationList;

    Activity activity;

    public NotificationsAdapter(Activity activity, ArrayList notificationList) {
        this.activity = activity;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_item_layout, viewGroup, false);
        return new NotificationViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder myViewHolder, int i) {
        NotificationDTO notificationDTO = (NotificationDTO) notificationList.get(i);
        myViewHolder.txtTitle.setText(notificationDTO.getTitle());
        myViewHolder.txtSubTitle.setText(notificationDTO.getSubtitle());
        myViewHolder.imgAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Approve Action clicked", Toast.LENGTH_LONG).show();
            }
        });
        myViewHolder.imgAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Decline Action clicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtSubTitle;
        public ImageButton imgAction1;
        public ImageView imgAction2;

        public NotificationViewHolder(View v) {
            super(v);
            txtTitle = v.findViewById(R.id.txtTitleNotifications);
            txtSubTitle = v.findViewById(R.id.txtSubTitleNotification);
            imgAction1 = v.findViewById(R.id.approve_action);
            imgAction2 = v.findViewById(R.id.reject_action);
        }
    }

}