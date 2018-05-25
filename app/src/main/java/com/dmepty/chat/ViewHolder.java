package com.dmepty.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView textViewMessage;

    public ViewHolder(View itemView) {
        super(itemView);

        textViewMessage = itemView.findViewById(R.id.message_item);
    }
}
