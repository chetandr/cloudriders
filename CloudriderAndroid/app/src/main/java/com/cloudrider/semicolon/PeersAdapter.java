package com.cloudrider.semicolon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PeersAdapter extends RecyclerView.Adapter<PeersAdapter.PeerViewHolder>{

    ArrayList peers;

    public PeersAdapter(ArrayList peers) {
        this.peers = peers;
    }

    @NonNull
    @Override
    public PeersAdapter.PeerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.peer_item_layout, viewGroup, false);
        return new PeerViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeersAdapter.PeerViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return peers.size();
    }

    public static class PeerViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtSubTitle;
        public ImageView imgAction1;
        public ImageView imgAction2;

        public PeerViewHolder(View v) {
            super(v);
           txtTitle = v.findViewById(R.id.txtTitlePeers);
           txtSubTitle = v.findViewById(R.id.txtSubTitlePeers);
           imgAction1 = v.findViewById(R.id.peer_action_1);
           imgAction2 = v.findViewById(R.id.peer_action_2);
        }
    }

}
