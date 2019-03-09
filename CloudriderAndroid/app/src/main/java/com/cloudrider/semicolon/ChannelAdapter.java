package com.cloudrider.semicolon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PeersAdapter.PeerViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PeerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public PeerViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

}
