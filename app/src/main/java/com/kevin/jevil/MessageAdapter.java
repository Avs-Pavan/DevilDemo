package com.kevin.jevil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kevin.devil.models.DevilMessage;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    List<DevilMessage> list = new ArrayList<>();
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;

    public MessageAdapter(Context context, List<DevilMessage> list) { // you can pass other parameters in constructor
        this.context = context;
        this.list.addAll(list);
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV, dateTV;

        MessageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }

        void bind(int position) {
            DevilMessage msgDetail = list.get(position);
            messageTV.setText(msgDetail.getMessage());
            dateTV.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(msgDetail.getCreatedAt()));
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV, dateTV;

        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }

        void bind(int position) {
            DevilMessage msgDetail = list.get(position);
            messageTV.setText(msgDetail.getMessage());
            dateTV.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(msgDetail.getCreatedAt()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.in_message, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.out_message, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list.get(position).isFromUser()) {
            ((MessageOutViewHolder) holder).bind(position);
        } else {
            ((MessageInViewHolder) holder).bind(position);
        }
    }

    public void updateList(List<DevilMessage> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void add(DevilMessage message) {
        list.add(message);
        notifyItemChanged(list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isFromUser())
            return 1;
        return 0;
    }
}