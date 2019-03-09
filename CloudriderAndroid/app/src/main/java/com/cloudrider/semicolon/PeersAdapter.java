package com.cloudrider.semicolon;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudrider.semicolon.dto.PeerDTO;

import java.util.ArrayList;

public class PeersAdapter extends RecyclerView.Adapter<PeersAdapter.PeerViewHolder>{

    ArrayList peers;

    Activity activity;

    public PeersAdapter(Activity activity, ArrayList peers) {
        this.activity = activity;
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
            PeerDTO peer = (PeerDTO) peers.get(i);
            myViewHolder.txtTitle.setText(peer.getPeerTitle());
            myViewHolder.txtSubTitle.setText(peer.getPeerSubTitle());
            myViewHolder.txtLedgerCount.setText(activity.getString(R.string.channel_count, peer.getChannelCount()));
            myViewHolder.txtCCCount.setText(activity.getString(R.string.cc_count, peer.getChainCodeCount()));
            myViewHolder.imgAction1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "Action clicked", Toast.LENGTH_LONG).show();
                }
            });
        myViewHolder.imgAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Action clicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return peers.size();
    }

    public static class PeerViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtSubTitle;
        public TextView txtLedgerCount;
        public TextView txtCCCount;
        public ImageView imgAction1;
        public ImageView imgAction2;

        public PeerViewHolder(View v) {
            super(v);
           txtTitle = v.findViewById(R.id.txtTitlePeers);
           txtSubTitle = v.findViewById(R.id.txtSubTitlePeers);
            txtLedgerCount = v.findViewById(R.id.txtLedgerCount);
            txtCCCount = v.findViewById(R.id.txtCCCount);
           imgAction1 = v.findViewById(R.id.peer_action_1);
           imgAction2 = v.findViewById(R.id.peer_action_2);
        }
    }

}
