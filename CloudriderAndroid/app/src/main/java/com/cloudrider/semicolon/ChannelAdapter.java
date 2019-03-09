package com.cloudrider.semicolon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>{

    ArrayList channels;

    public ChannelAdapter(ArrayList channels) {
        this.channels = channels;
    }

    @NonNull
    @Override
    public ChannelAdapter.ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.channel_item_layout, viewGroup, false);
        return new ChannelAdapter.ChannelViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelAdapter.ChannelViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtSubTitle;
        public ImageView imgAction1;
        public ImageView imgAction2;

        public ChannelViewHolder(View v) {
            super(v);
            txtTitle = v.findViewById(R.id.txtTitlePeers);
            txtSubTitle = v.findViewById(R.id.txtSubTitlePeers);
            imgAction1 = v.findViewById(R.id.peer_action_1);
            imgAction2 = v.findViewById(R.id.peer_action_2);
        }
    }

}
