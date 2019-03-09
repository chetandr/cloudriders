package com.cloudrider.semicolon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudrider.semicolon.dto.ChannelDTO;
import com.cloudrider.semicolon.parse.ChDatum;

import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>{

    List channels;

    public ChannelAdapter(List channels) {
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

        ChDatum dto = (ChDatum) channels.get(i);
        myViewHolder.txtTitle.setText(dto.getChannelName());
        //myViewHolder.txtTitle.setText(dto.getChannelSubTitle());
        myViewHolder.txtSubTitle.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return channels.size();
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
