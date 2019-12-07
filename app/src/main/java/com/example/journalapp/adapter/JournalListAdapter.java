package com.example.journalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journalapp.model.Journal;
import com.example.journalapp.R;

import java.util.List;

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.JournalViewHolder> {

    private final LayoutInflater mInflater;
    private List<Journal> mJournals; // Cached copy of words
    private static ClickListener clickListener;

    public JournalListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new JournalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        if (mJournals != null) {
            Journal current = mJournals.get(position);
            holder.titleItemView.setText(current.getTitle());
            holder.contentItemView.setText(current.getContent());
            holder.dateItemView.setText(current.getJournalDate().toString());
        }
    }

    public void setJournals(List<Journal> journals) {
        mJournals = journals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mJournals != null)
            return mJournals.size();
        else return 0;
    }

    public Journal getJournalAtPosition(int position) {
        return mJournals.get(position);
    }

    public class JournalViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleItemView;
        private final TextView contentItemView;
        private final TextView dateItemView;


        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);

            titleItemView = itemView.findViewById(R.id.itemTitleTetView);
            contentItemView = itemView.findViewById(R.id.itemContentTetView);
            dateItemView = itemView.findViewById(R.id.itemDateTetView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        JournalListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
