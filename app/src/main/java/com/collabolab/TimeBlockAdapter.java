package com.collabolab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TimeBlockAdapter extends RecyclerView.Adapter<TimeBlockAdapter.ViewHolder> {
    List<Integer> listTime;
    Context context;

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView tvTime;
        Button btnBlock;

        public ViewHolder(View view) {
            super(view);
            tvTime = view.findViewById(R.id.tv_time);
            btnBlock = view.findViewById(R.id.btn_time);
        }
    }

    public TimeBlockAdapter(List<Integer> time, Context context) {
        this.context = context;
        this.listTime = time;
    }

    @Override
    public TimeBlockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tvTime.setText(String.valueOf(listTime.get(position)));
        viewHolder.btnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Button)view).setSelected(!(((Button)view).isSelected()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTime.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
